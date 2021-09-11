package com.multiplayer.RPS.controller;

import com.multiplayer.RPS.entities.PlayerGameCreateRequest;
import com.multiplayer.RPS.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class MatchBookingControlller {

    @Autowired
    GameService gameService;

    @MessageMapping("/createGame")
    public void createGame(final SimpMessageHeaderAccessor headerAccessor, final PlayerGameCreateRequest playerGameCreateRequest) {
        log.info(playerGameCreateRequest.toString());
        gameService.createGame(playerGameCreateRequest, headerAccessor);
    }
    @MessageMapping("/joinGame")
    public void joinGame(final SimpMessageHeaderAccessor headerAccessor, final PlayerGameCreateRequest playerGameCreateRequest) {
        log.info(playerGameCreateRequest.toString());
        gameService.joinGame(playerGameCreateRequest, headerAccessor);
    }
}
