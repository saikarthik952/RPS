
package com.multiplayer.RPS.controller;

import com.multiplayer.RPS.connector.CouchBaseConnector;
import com.multiplayer.RPS.entities.TestMessage;
import lombok.extern.slf4j.Slf4j;
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

    public TestController(SimpMessagingTemplate simpMessagingTemplate, CouchBaseConnector cluster) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.couchBaseConnector = cluster;
    }



    @MessageMapping("/chat")
    public void testMessages(final String message1) {
        log.info(message1);
        Flux.interval(Duration.ofSeconds(2L))
                .map((n) -> new TestMessage("Hello " + couchBaseConnector.testConnection()))
                .subscribe(message -> simpMessagingTemplate.convertAndSend("/topic/messages", message));
    }
}
