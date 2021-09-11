package com.multiplayer.RPS.service;

import com.couchbase.client.java.kv.GetResult;
import com.multiplayer.RPS.connector.CouchBaseConnector;
import com.multiplayer.RPS.entities.Action;
import com.multiplayer.RPS.entities.Game;
import com.multiplayer.RPS.entities.Player;
import com.multiplayer.RPS.entities.PlayerGameCreateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Slf4j
public class GameServiceImpl implements GameService{

    @Autowired
    CouchBaseConnector couchBaseConnector;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void createGame(final PlayerGameCreateRequest playerGameCreateRequest,
                           final SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        playerGameCreateRequest.setPlayerId(simpMessageHeaderAccessor.getSessionId());
        final Mono<Game> gameMono = couchBaseConnector.createMatch(playerGameCreateRequest);
        gameMono
                .subscribe(game -> {
                    final SimpMessageHeaderAccessor headerAccessor = getHeaders(simpMessageHeaderAccessor);
                    log.info(simpMessageHeaderAccessor.toString());
                    simpMessagingTemplate.convertAndSendToUser(simpMessageHeaderAccessor.getSessionId(),
                            "/topic/messages", game, headerAccessor.getMessageHeaders());
                });
    }

    @Override
    public Mono<Game> joinGame(final PlayerGameCreateRequest playerGameCreateRequest,
                               final SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        playerGameCreateRequest.setPlayerId(simpMessageHeaderAccessor.getSessionId());
        final Mono<GetResult> getResultMono = couchBaseConnector.getGame(playerGameCreateRequest);

        final SimpMessageHeaderAccessor headerAccessor = getHeaders(simpMessageHeaderAccessor);
        log.info(simpMessageHeaderAccessor.toString());
        getResultMono
                .subscribe(getResult -> {
                    try {
                        long cas = getResult.cas();
                        final Game game = getResult.contentAs(Game.class);

                        // Todo check if player 1 is still active
                        if (Objects.isNull(game) || Objects.isNull(game.getPlayers()) ||
                                (game.getPlayers().isEmpty())) {
                            throw new RuntimeException(" No players or game is null");
                        }
                        if (game.getPlayers().size() == 2) {
                            throw new RuntimeException(" 2 Players Already added.");
                        }
                        game.getPlayers().add(Player.builder()
                                .id(playerGameCreateRequest.getPlayerId())
                                        .action(Action.PLAYER_2)
                                .build());
                    couchBaseConnector.joinMatch(game, cas)
                            .subscribe(gameAdded -> {
                                simpMessagingTemplate.convertAndSendToUser(simpMessageHeaderAccessor.getSessionId(),
                                        "/topic/messages", gameAdded, headerAccessor.getMessageHeaders());
                            });

                    }
                    catch (final Exception e) {
                        log.error("{ }", e);
                    }
                }, throwable -> {
                    log.error(throwable.toString());
                });

        return null;
    }

    private SimpMessageHeaderAccessor getHeaders(final SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor
                .create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(simpMessageHeaderAccessor.getSessionId());
        headerAccessor.setLeaveMutable(true);
        return headerAccessor;
    }
}
