package com.batrom.ing.atmservice;

import java.io.Serializable;

public record ATMServiceInput(Regions regions) implements Serializable {

    ATM[] toResponse() {
        return regions.toOrder().getATMs();
    }
}
