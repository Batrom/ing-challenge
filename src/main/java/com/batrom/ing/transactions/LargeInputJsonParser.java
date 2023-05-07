package com.batrom.ing.transactions;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import static com.batrom.ing.transactions.LargeInput.NUMBER_OF_BUCKETS;
import static com.batrom.ing.transactions.TransactionsHelper.*;

class LargeInputJsonParser {

    static List<AccountBalanceChangesBucket> parse(final JsonNode node) {
        return StreamSupport.stream(node.spliterator(), true)
                .collect(LargeInputJsonParser::initializeBuckets,
                        LargeInputJsonParser::addToBuckets,
                        LargeInputJsonParser::mergeBuckets);
    }

    private static List<AccountBalanceChangesBucket> mergeBuckets(final List<AccountBalanceChangesBucket> buckets, final List<AccountBalanceChangesBucket> buckets2) {
        for (int i = 0; i < NUMBER_OF_BUCKETS; i++) {
            buckets.get(i).merge(buckets2.get(i));
        }
        return buckets;
    }

    private static List<AccountBalanceChangesBucket> addToBuckets(final List<AccountBalanceChangesBucket> buckets, final JsonNode transaction) {
        final var amount = extractAmount(transaction);
        createDebitAccountChange(buckets, transaction, amount);
        createCreditAccountChange(buckets, transaction, amount);
        return buckets;
    }

    private static void createDebitAccountChange(final List<AccountBalanceChangesBucket> buckets, final JsonNode transaction, final double amount) {
        final var account = extractDebitAccount(transaction);
        final var change = new AccountBalanceChange(account, -amount);
        addToBucket(buckets, account, change);
    }

    private static void createCreditAccountChange(final List<AccountBalanceChangesBucket> buckets, final JsonNode transaction, final double amount) {
        final var account = extractCreditAccount(transaction);
        final var change = new AccountBalanceChange(account, amount);
        addToBucket(buckets, account, change);
    }

    private static void addToBucket(final List<AccountBalanceChangesBucket> buckets, final String account, final AccountBalanceChange change) {
        switch (account.charAt(0)) {
            case '0' -> buckets.get(0).add(change);
            case '1' -> buckets.get(1).add(change);
            case '2' -> buckets.get(2).add(change);
            case '3' -> buckets.get(3).add(change);
            case '4' -> buckets.get(4).add(change);
            case '5' -> buckets.get(5).add(change);
            case '6' -> buckets.get(6).add(change);
            case '7' -> buckets.get(7).add(change);
            case '8' -> buckets.get(8).add(change);
            case '9' -> buckets.get(9).add(change);
            default -> buckets.get(10).add(change);
        }
    }

    private static List<AccountBalanceChangesBucket> initializeBuckets() {
        final var buckets = new ArrayList<AccountBalanceChangesBucket>(NUMBER_OF_BUCKETS);
        for (int i = 0; i < NUMBER_OF_BUCKETS; i++) {
            buckets.add(new AccountBalanceChangesBucket());
        }
        return buckets;
    }
}
