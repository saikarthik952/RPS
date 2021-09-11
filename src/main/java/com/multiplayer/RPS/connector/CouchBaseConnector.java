package com.multiplayer.RPS.connector;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.ReactiveCollection;
import com.couchbase.client.java.codec.RawJsonTranscoder;
import com.couchbase.client.java.codec.Transcoder;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.multiplayer.RPS.entities.*;
import com.multiplayer.RPS.util.RandomIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Slf4j
public class CouchBaseConnector {

    private Cluster cluster;

    @Autowired
    ObjectMapper objectMapper;

    public CouchBaseConnector(Cluster cluster) {
        this.cluster = cluster;
        log.info(cluster.bucket("matchBooking").ping().exportToJson());
    }


    public String testConnection() {
        return cluster.bucket("matchBooking")
                .scope("TestScope").collection("testCollection")
                .get("myDoc").toString();
    }

    private ReactiveCollection matchCollection(final String id) {
        return cluster.reactive()
                .bucket("matchBooking")
                .scope(index(id) ? "matchScope_60" : "matchScope_30")
                .collection("matchCreations");
    }

    private boolean index(final String id) {
        final int startsWith = RandomIdGenerator.letters.indexOf(id.substring(0,1));
        final boolean index = startsWith > 30;
        return index;
    }

    public Mono<Game> createMatch(final PlayerGameCreateRequest playerMatchCreateRequest) {
        final String id = RandomIdGenerator.GetBase62(6);
        final Game game = Game.builder()
                .gameId(id)
                .players(List.of(Player.builder().id(playerMatchCreateRequest.getPlayerId())
                                .action(Action.PLAYER_1)
                        .build()))
                .matchCount(playerMatchCreateRequest.getMatchCount())
                .action(Action.GAME_CREATED)
                .build();

        return matchCollection(id)
                .insert(id, game)
                .map(mutationResult -> game)
                .onErrorResume((throwable -> {
                    log.error(throwable.toString());
                    return Mono.just(Game.builder()
                            .action(Action.GAME_NOT_CREATED)
                            .build());
                }));
    }

    public Mono<GetResult> getGame(final PlayerGameCreateRequest playerMatchCreateRequest) {
        final Mono<GetResult> gameMono = matchCollection(playerMatchCreateRequest.getGameId())
                .getAndLock(playerMatchCreateRequest.getGameId(), Duration.ofSeconds(15));
        return gameMono;
    }
    public Mono<Game> joinMatch(final Game game, final long cas) {
        final Mono<Game> gameMono = matchCollection(game.getGameId())
                .replace(game.getGameId(), game, ReplaceOptions.replaceOptions().cas(cas))
                .map(mutationResult -> game)
                .onErrorResume((throwable -> {
            log.error(throwable.toString());
            return Mono.just(game.toBuilder()
                    .action(Action.PLAYER_NOT_ADDED)
                    .build());
        }));
        return gameMono;
    }
}
