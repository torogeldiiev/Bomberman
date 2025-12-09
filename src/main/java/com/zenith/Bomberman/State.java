package com.zenith.Bomberman;

public class State {
    protected GameEngine ge;

    public State(GameEngine ge) {
        this.ge = ge;
    }

    public GameEngine getGame() {
        return ge;
    }
}
