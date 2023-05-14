package com.batrom.ing.onlinegame;

import java.io.Serializable;
import java.util.Arrays;

class Groups implements Serializable {
    private static final int MAX_NUMBER_OF_GROUPS = 20_000;
    private static final Group[] EMPTY = new Group[0];

    private int realSize = 0;
    private Group[] data = new Group[10];

    static Groups of(final Group group) {
        final var clans = new Groups();
        clans.add(group);
        return clans;
    }

    void add(final Group group) {
        growIfNecessary();
        this.data[this.realSize] = group;
        this.realSize++;
    }

    Group[] getData() {
        if (this.realSize < this.data.length) {
            return this.realSize == 0 ? EMPTY : Arrays.copyOf(this.data, this.realSize);
        }
        return this.data;
    }

    private void growIfNecessary() {
        if (this.data.length == this.realSize) {
            this.data = Arrays.copyOf(this.data, Math.min(MAX_NUMBER_OF_GROUPS, this.realSize + (this.realSize >> 1)));
        }
    }
}
