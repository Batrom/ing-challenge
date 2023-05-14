package com.batrom.ing.onlinegame;

import java.util.Arrays;

class GroupsMap {
    private final ClansStack[] clan;

    GroupsMap(final int maxGroupSize) {
        this.clan = new ClansStack[maxGroupSize + 1];
    }

    ClansStack get(final int numberOfPlayers) {
        return this.clan[numberOfPlayers];
    }

    void put(final Clan clan) {
        final var stack = this.clan[clan.getNumberOfPlayers()];
        if (stack != null) {
            stack.push(clan);
        } else {
            this.clan[clan.getNumberOfPlayers()] = new ClansStack(clan);
        }
    }

    static class ClansStack {
        private int top;
        private Clan[] data = new Clan[10];

        private ClansStack(final Clan clan) {
            this.data[0] = clan;
            this.top = 0;
        }

        Clan peek() {
            return this.data[this.top];
        }

        void push(final Clan clan) {
            growIfNecessary();
            this.top++;
            this.data[this.top] = clan;
        }

        void pop() {
            this.data[this.top] = null;
            this.top--;
        }

        boolean isEmpty() {
            return this.top < 0;
        }

        private void growIfNecessary() {
            if (this.data.length == this.top + 1) {
                this.data = Arrays.copyOf(this.data, this.top + (this.top >> 1));
            }
        }
    }
}
