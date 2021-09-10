package com.multiplayer.RPS.connector;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.ExistsOptions;
import com.couchbase.client.java.kv.MutationResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.multiplayer.RPS.entities.*;
import com.multiplayer.RPS.util.RandomIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

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
    public Mono<Game> createMatch(final PlayerGameCreateRequest playerMatchCreateRequest) {
        final String id = RandomIdGenerator.GetBase62(6);
        final Game game = Game.builder()
                .gameId(id)
                .players(List.of(Player.builder().id(playerMatchCreateRequest.getPlayerId()).build()))
                .matchCount(playerMatchCreateRequest.getMatchCount())
                .action(Action.GAME_CREATED)
                .build();

        return cluster.reactive()
                .bucket("matchBooking")
                .scope("TestScope")
                .collection("testCollection")
                .insert(id, game)
                .map(mutationResult -> game)
                .onErrorResume((throwable -> {
                    log.error(throwable.toString());
                    return Mono.just(Game.builder()
                            .action(Action.GAME_NOT_CREATED)
                            .build());
                }));
    }


}
