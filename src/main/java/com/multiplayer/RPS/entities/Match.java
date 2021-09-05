package com.multiplayer.RPS.entities;

import org.springframework.data.couchbase.core.mapping.Document;

@Document
public class Match {
    @org.springframework.data.annotation.Id
    String Id;
    String result;
}
