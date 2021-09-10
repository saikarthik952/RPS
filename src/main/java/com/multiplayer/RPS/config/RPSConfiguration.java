package com.multiplayer.RPS.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.multiplayer.RPS.connector.CouchBaseConnector;
import com.multiplayer.RPS.controller.TestController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Configuration
public class RPSConfiguration {

    @ConditionalOnMissingBean(TestController.class)
    @Bean
    @DependsOn("couchbase")
   public TestController testController(SimpMessagingTemplate simpMessagingTemplate, CouchBaseConnector couchBaseConnector) {
        return new TestController(simpMessagingTemplate, couchBaseConnector);
    }

    @Bean
    public ObjectMapper createObjectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }
}
