package com.batrom.ing.transactions;

import java.io.Serializable;

public record Input(Account[] accounts) implements Serializable {

    public Account[] toResponse() {
        return accounts;
    }
}
