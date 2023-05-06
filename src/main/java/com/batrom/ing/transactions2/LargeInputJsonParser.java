package com.batrom.ing.transactions2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.stream.StreamSupport;

import static com.batrom.ing.transactions2.LargeInput.NUMBER_OF_BUCKETS;
import static com.batrom.ing.transactions2.ParsingUtil.*;

class LargeInputJsonParser {

    static AccountBalanceChangesBucket[] read(final ArrayNode node) {
        return StreamSupport.stream(node.spliterator(), true)
                .reduce(initializeBuckets(),
                        (LargeInputJsonParser::addToBuckets),
                        (buckets, buckets2) -> buckets);
    }

    private static AccountBalanceChangesBucket[] addToBuckets(final AccountBalanceChangesBucket[] buckets, final JsonNode transaction) {
        final var amount = extractAmount(transaction);
        createDebitAccountChange(buckets, transaction, amount);
        createCreditAccountChange(buckets, transaction, amount);
        return buckets;
    }

    private static void createDebitAccountChange(final AccountBalanceChangesBucket[] buckets, final JsonNode transaction, final float amount) {
        final var account = extractDebitAccount(transaction);
        final var change = new AccountBalanceChange(account, -amount);
        addToBucket(buckets, account, change);
    }

    private static void createCreditAccountChange(final AccountBalanceChangesBucket[] buckets, final JsonNode transaction, final float amount) {
        final var account = extractCreditAccount(transaction);
        final var change = new AccountBalanceChange(account, amount);
        addToBucket(buckets, account, change);
    }

    private static void addToBucket(final AccountBalanceChangesBucket[] buckets, final String account, final AccountBalanceChange change) {
        switch (account.charAt(0)) {
            case '0' -> buckets[0].add(change);
            case '1' -> buckets[1].add(change);
            case '2' -> buckets[2].add(change);
            case '3' -> buckets[3].add(change);
            case '4' -> buckets[4].add(change);
            case '5' -> buckets[5].add(change);
            case '6' -> buckets[6].add(change);
            case '7' -> buckets[7].add(change);
            case '8' -> buckets[8].add(change);
            case '9' -> buckets[9].add(change);
            default -> buckets[10].add(change);
        }
    }

    private static AccountBalanceChangesBucket[] initializeBuckets() {
        final var buckets = new AccountBalanceChangesBucket[NUMBER_OF_BUCKETS];
        for (int i = 0; i < NUMBER_OF_BUCKETS; i++) {
            buckets[i] = new AccountBalanceChangesBucket();
        }
        return buckets;
    }
}
