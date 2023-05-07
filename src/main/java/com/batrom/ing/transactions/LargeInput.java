package com.batrom.ing.transactions;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

record LargeInput(List<AccountBalanceChangesBucket> buckets) implements Input {

    static final int NUMBER_OF_BUCKETS = 11;

    static Input fromJson(final JsonNode node) {
        return new LargeInput(LargeInputJsonParser.parse(node));
    }

    public Account[] toResponse() {
        return LargeInputToResponseConverter.convert(buckets);
    }
}
