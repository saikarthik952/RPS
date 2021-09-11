package com.multiplayer.RPS.service;

import com.multiplayer.RPS.entities.Game;
import com.multiplayer.RPS.entities.PlayerGameCreateRequest;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import reactor.core.publisher.Mono;

public interface GameService {

    public void createGame(PlayerGameCreateRequest playerGameCreateRequest, SimpMessageHeaderAccessor simpMessageHeaderAccessor);
    public Mono<Game> joinGame(PlayerGameCreateRequest playerMatchCreateRequest, SimpMessageHeaderAccessor simpMessageHeaderAccessor);
}
