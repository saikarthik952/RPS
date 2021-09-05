package com.multiplayer.RPS.connector;

import com.couchbase.client.java.Cluster;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class CouchBaseConnector {

    private Cluster cluster;

    public CouchBaseConnector(Cluster cluster) {
        this.cluster = cluster;
        log.info(cluster.bucket("matchBooking").ping().exportToJson());
    }


    public String testConnection() {


        return cluster.bucket("matchBooking")
                .scope("TestScope").collection("testCollection")
                .get("myDoc").toString();
    }



}
