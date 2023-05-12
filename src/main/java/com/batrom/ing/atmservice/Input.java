package com.batrom.ing.atmservice;

import java.io.Serializable;

public record Input(Regions regions) implements Serializable {

    public Order toResponse() {
        return regions.toOrder();
    }
}
