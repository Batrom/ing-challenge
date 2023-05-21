package com.batrom.ing.onlinegame;

import java.io.Serializable;

public class Group implements Serializable {
    transient private int sizeLeft;
    private final Clans clans;

    private Group(final Clans clans, final int sizeLeft) {
        this.sizeLeft = sizeLeft;
        this.clans = clans;
    }

    static Group of(final Clan clan, final int maxSize) {
        return new Group(Clans.of(clan), maxSize - clan.getNumberOfPlayers());
    }

    void addClan(final Clan clan) {
        this.clans.add(clan);
        this.sizeLeft -= clan.getNumberOfPlayers();
    }

    int getSizeLeft() {
        return sizeLeft;
    }

    void appendToJson(final StringBuilder builder) {
        builder.append("[");
        clans.appendToJson(builder);
        builder.append("]");
    }
}
