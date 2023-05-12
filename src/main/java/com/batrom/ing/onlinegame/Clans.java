package com.batrom.ing.onlinegame;

import java.io.Serializable;
import java.util.Arrays;

class Clans implements Serializable {
    private static final int MAX_NUMBER_OF_CLANS = 20_000;
    private static final Clan[] EMPTY = new Clan[0];

    private int realSize = 0;
    private Clan[] data = new Clan[10];

    static Clans of(final Clan clan) {
        final var clans = new Clans();
        clans.add(clan);
        return clans;
    }

    void add(final Clan clan) {
        growIfNecessary();
        this.data[this.realSize] = clan;
        this.realSize++;
    }

    Clan[] getData() {
        if (this.realSize < this.data.length) {
            return this.realSize == 0 ? EMPTY : Arrays.copyOf(this.data, this.realSize);
        }
        return this.data;
    }

    private void growIfNecessary() {
        if (this.data.length == this.realSize) {
            this.data = Arrays.copyOf(this.data, Math.min(MAX_NUMBER_OF_CLANS, this.realSize + (this.realSize >> 1)));
        }
    }
}
