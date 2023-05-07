package com.batrom.ing.transactions;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

class TransactionsHelper {

    static final Account[] EMPTY_ACCOUNTS = new Account[0];

    static String extractCreditAccount(final JsonNode transaction) {
        return transaction.get("creditAccount").textValue();
    }

    static String extractDebitAccount(final JsonNode transaction) {
        return transaction.get("debitAccount").textValue();
    }

    static double extractAmount(final JsonNode transaction) {
        return transaction.get("amount").doubleValue();
    }

    static Account[] toSortedArray(final Map<String, Account> accountsMap) {
        final var accounts = accountsMap.values().toArray(EMPTY_ACCOUNTS);
        Arrays.sort(accounts, Comparator.comparing(Account::getAccount));
        return accounts;
    }
}
