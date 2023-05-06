package com.batrom.ing.transactions;

import com.fasterxml.jackson.databind.node.ArrayNode;

record SmallInput(Account[] accounts) implements Input {

    static Input fromJson(final ArrayNode node) {
        return new SmallInput(SmallInputJsonParser.read(node));
    }

    public Account[] toResponse() {
        return accounts;
    }
}
