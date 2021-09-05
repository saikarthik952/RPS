package com.multiplayer.RPS.entities;

import org.springframework.data.couchbase.core.mapping.Document;

import java.util.List;

@Document
public class Game {

    String gameWinner;

    String matchCount;

    List<Match> matches;

    List<Player> players;
}
