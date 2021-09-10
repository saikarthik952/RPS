package com.multiplayer.RPS.entities;

public enum Action {
    ROCK(0),
    PAPER(1),
    SCISSORS(2),
    PLAYER_LEFT(9),
    CONNECTION_LOST(8),
    NO_ACTION(5),
    CREATED_MATCH_ID(6),
    WAITING_FOR_OTHER_PLAYER(7),
    GAME_CREATED(10),
    MATCH_ABORTED(11),
    GAME_NOT_CREATED(99),
    PLAYER_1(12),
    PLAYER_2(13);
    int actionCode;

    Action(int actionCode) {
        this.actionCode = actionCode;
    }

    public int getActionCode() {
        return actionCode;
    }
}
