package com.batrom.ing.transactions2;

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

    static float extractAmount(final JsonNode transaction) {
        return transaction.get("amount").floatValue();
    }

    static Account[] toSortedArray(final Map<String, Account> accountsMap) {
        final var accounts = accountsMap.values().toArray(new Account[0]); //todo init with 0?
        Arrays.sort(accounts, Comparator.comparing(Account::getAccount));
        return accounts;
    }
}
