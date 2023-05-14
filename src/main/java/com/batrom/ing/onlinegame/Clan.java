package com.batrom.ing.onlinegame;

import java.io.Serializable;

class Clan implements Serializable {
    private final int numberOfPlayers;
    private final int points;
    private boolean isAssigned = false;

    Clan(final int numberOfPlayers, final int points) {
        this.numberOfPlayers = numberOfPlayers;
        this.points = points;
    }

    void setIsAssignedToTrue() {
        this.isAssigned = true;
    }

    boolean isNotAssigned() {
        return !this.isAssigned;
    }

    int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    int getPoints() {
        return points;
    }
}
