package com.multiplayer.RPS.entities;

import lombok.experimental.SuperBuilder;
import org.springframework.data.couchbase.core.mapping.Document;

@Document
@SuperBuilder
public class Match {

    String Id;
    String result;

    Action action;
}
