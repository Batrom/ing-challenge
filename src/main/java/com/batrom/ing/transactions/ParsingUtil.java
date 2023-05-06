package com.batrom.ing.transactions;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

class ParsingUtil {

    static String extractCreditAccount(final JsonNode transaction) {
        return transaction.get("creditAccount").asText();
    }

    static String extractDebitAccount(final JsonNode transaction) {
        return transaction.get("debitAccount").asText();
    }

    static double extractAmount(final JsonNode transaction) {
        return transaction.get("amount").doubleValue();
    }

    static Account[] toSortedArray(final Map<String, Account> accountsMap) {
        final var accounts = accountsMap.values().toArray(new Account[0]); //todo init with 0?
        Arrays.sort(accounts, Comparator.comparing(Account::getAccount));
        return accounts;
    }
}
