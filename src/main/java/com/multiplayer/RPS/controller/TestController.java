
package com.multiplayer.RPS.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.multiplayer.RPS.connector.CouchBaseConnector;
import com.multiplayer.RPS.entities.PlayerGameCreateRequest;
import com.multiplayer.RPS.entities.TestMessage;
import com.multiplayer.RPS.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Controller
@Slf4j
public class TestController {

    private SimpMessagingTemplate simpMessagingTemplate;

    private CouchBaseConnector couchBaseConnector;

    @Autowired
    GameService gameService;

    public TestController(SimpMessagingTemplate simpMessagingTemplate, CouchBaseConnector cluster) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.couchBaseConnector = cluster;
    }



    @MessageMapping("/chat")
    public void testMessages(final String message1) throws JsonProcessingException {
        log.info(message1);
        couchBaseConnector.createMatch(PlayerGameCreateRequest.builder()
        .matchCount("10").build()).block();
        Flux.interval(Duration.ofSeconds(2L))
                .map((n) -> new TestMessage("Hello " + couchBaseConnector.testConnection()))
                .subscribe(message -> simpMessagingTemplate.convertAndSend("/topic/messages", message));
    }

    @MessageMapping("/test")
    public void createMatch(final PlayerGameCreateRequest playerGameCreateRequest) {
            log.info(playerGameCreateRequest.toString());
            gameService.createGame(playerGameCreateRequest);
    }
}
