package com.batrom.ing.transactions;

import com.fasterxml.jackson.databind.JsonNode;

record SmallInput(Account[] accounts) implements Input {

    static Input fromJson(final JsonNode node) {
        return new SmallInput(SmallInputJsonParser.parse(node));
    }

    public Account[] toResponse() {
        return accounts;
    }
}
