package com.batrom.ing.transactions;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;

@JsonDeserialize(using = InputDeserializer.class)
record Input(Account[] accounts) implements Serializable {

    public Account[] toResponse() {
        return accounts;
    }
}
