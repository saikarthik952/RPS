package com.multiplayer.RPS.service;

import com.multiplayer.RPS.connector.CouchBaseConnector;
import com.multiplayer.RPS.entities.Match;
import com.multiplayer.RPS.entities.PlayerGameCreateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class MatchMakingServiceImpl implements MatchMakingService {

    @Autowired
    CouchBaseConnector couchBaseConnector;


    @Override
    public Mono<Match> createMatch(final PlayerGameCreateRequest playerMatchCreateRequest) {

        return null;
    }

    @Override
    public Mono<Match> joinMatch(PlayerGameCreateRequest playerMatchCreateRequest) {
        return null;
    }
}
