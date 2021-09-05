package com.multiplayer.RPS.entities;

public enum Action {
    ROCK(0),
    PAPER(1),
    SCISSORS(2),
    PLAYER_LEFT(9),
    CONNECTION_LOST(8),
    NO_ACTION(5);
    int actionCode;

    Action(int actionCode) {
        this.actionCode = actionCode;
    }

    public int getActionCode() {
        return actionCode;
    }
}
