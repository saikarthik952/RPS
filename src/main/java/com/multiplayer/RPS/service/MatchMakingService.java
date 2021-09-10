package com.multiplayer.RPS.service;

import com.multiplayer.RPS.entities.Match;
import com.multiplayer.RPS.entities.PlayerGameCreateRequest;
import reactor.core.publisher.Mono;

public interface MatchMakingService {

    public Mono<Match> createMatch(PlayerGameCreateRequest playerMatchCreateRequest);

    public Mono<Match> joinMatch(PlayerGameCreateRequest playerMatchCreateRequest);
}
