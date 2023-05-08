package com.batrom.ing.atmservice;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Arrays;

@JsonSerialize(using = OrderSerializer.class)
class Order {
    private int realSize = 0;

    private ATM[] data = new ATM[10];

    void add(final int region, final int atmId) {
        growIfNecessary();
        this.data[this.realSize] = new ATM(region, atmId);
        this.realSize++;
    }

    ATM[] getATMs() {
        return data;
    }

    private void growIfNecessary() {
        if (this.data.length == this.realSize) {
            this.data = Arrays.copyOf(this.data, this.realSize >> 1);
        }
    }
}
