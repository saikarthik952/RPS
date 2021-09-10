package com.multiplayer.RPS.config;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.ClusterOptions;
import com.multiplayer.RPS.connector.CouchBaseConnector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@Slf4j
public class CouchBaseConfiguration {

    @Bean(name = "cluster")
    public Cluster connectDB() {
        log.info("Connect DB ");
        return Cluster.connect("127.0.0.1", ClusterOptions
                .clusterOptions("saikarthik", "TestPassword@1"));
    }

    @ConditionalOnMissingBean(CouchBaseConnector.class)
    @Bean(name = "couchbase")
    @DependsOn("cluster")
    public CouchBaseConnector couchBaseConnector() {
        log.info("couchBaseConnector starting");
        return new CouchBaseConnector(connectDB());
    }
}