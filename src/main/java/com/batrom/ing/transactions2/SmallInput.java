package com.batrom.ing.transactions2;

import com.fasterxml.jackson.databind.node.ArrayNode;

public record SmallInput(Account[] accounts) implements Input {

    static Input fromJson(final ArrayNode node) {
        return new SmallInput(SmallInputJsonParser.read(node));
    }

    public Account[] toResponse() {
        return accounts;
    }
}
