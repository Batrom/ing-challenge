package com.batrom.ing.onlinegame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Group implements Serializable {
    transient private int size;
    private final List<Clan> clans;

    private Group(final int size, final List<Clan> clans) {
        this.size = size;
        this.clans = clans;
    }

    static Group of(final Clan clan) {
        final var clans = new ArrayList<Clan>();
        clans.add(clan);
        return new Group(clan.numberOfPlayers(), clans);
    }

    void addClan(final Clan clan) {
        clans.add(clan);
        size += clan.numberOfPlayers();
    }

    boolean willFit(final int newClanSize, final int maxSize) {
        return size + newClanSize <= maxSize;
    }

    boolean isFull(final int maxSize) {
        return size == maxSize;
    }

    List<Clan> getClans() {
        return clans;
    }
}
