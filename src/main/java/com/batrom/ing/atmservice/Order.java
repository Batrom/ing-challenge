package com.batrom.ing.atmservice;

import java.util.Arrays;

class Order {
    private static final ATM[] EMPTY = new ATM[0];

    private int realSize = 0;
    private ATM[] data = new ATM[10];

    void add(final int region, final int atmId) {
        growIfNecessary();
        this.data[this.realSize] = new ATM(region, atmId);
        this.realSize++;
    }

    ATM[] getATMs() {
        if (this.realSize < this.data.length) {
            return this.realSize == 0 ? EMPTY : Arrays.copyOf(this.data, this.realSize);
        }
        return this.data;
    }

    private void growIfNecessary() {
        if (this.data.length == this.realSize) {
            this.data = Arrays.copyOf(this.data, this.realSize + (this.realSize >> 1));
        }
    }
}
