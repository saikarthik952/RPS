package com.multiplayer.RPS.entities;

import com.couchbase.client.java.kv.MutationResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.function.Function;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Game  {

    String gameId;

    String gameWinner;

    String matchCount;

    String currentMatch;

    List<Match> matches;

    List<Player> players;

    int player1Wins;
    int player2Wins;

    Action action;


}
