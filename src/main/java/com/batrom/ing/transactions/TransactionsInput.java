package com.batrom.ing.transactions;

import java.io.Serializable;

public record TransactionsInput(Account[] accounts) implements Serializable {

    Account[] toResponse() {
        return accounts;
    }
}
