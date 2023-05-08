package com.batrom.ing.atmservice;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;

@JsonDeserialize(using = InputDeserializer.class)
record Input(Regions regions) implements Serializable {

    Order toResponse() {
        return regions.toOrder();
    }
}
