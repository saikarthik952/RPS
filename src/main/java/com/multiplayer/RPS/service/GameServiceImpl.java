package com.multiplayer.RPS.service;

import com.multiplayer.RPS.connector.CouchBaseConnector;
import com.multiplayer.RPS.entities.Game;
import com.multiplayer.RPS.entities.PlayerGameCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
@Service
public class GameServiceImpl implements GameService{

    @Autowired
    CouchBaseConnector couchBaseConnector;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void createGame(PlayerGameCreateRequest playerGameCreateRequest) {
        final Mono<Game> gameMono = couchBaseConnector.createMatch(playerGameCreateRequest);
        gameMono
                .subscribe(game -> {
                    simpMessagingTemplate.convertAndSend("/topic/messages", game);
                });
    }

    @Override
    public Mono<Game> joinGame(PlayerGameCreateRequest playerMatchCreateRequest) {
        return null;
    }
}
