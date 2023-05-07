package com.batrom.ing.transactions;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashMap;
import java.util.Map;

import static com.batrom.ing.transactions.TransactionsHelper.*;

class SmallInputJsonParser {

    static Account[] parse(final JsonNode node) {
        final var accountsMap = new HashMap<String, Account>();

        final var iterator = node.iterator();
        while (iterator.hasNext()) {
            final var transaction = iterator.next();

            final var debitAccountNumber = extractDebitAccount(transaction);
            final var creditAccountNumber = extractCreditAccount(transaction);
            final var amount = extractAmount(transaction);

            updateCreditAccount(accountsMap, creditAccountNumber, amount);
            updateDebitAccount(accountsMap, debitAccountNumber, -amount);
        }

        return toSortedArray(accountsMap);
    }

    private static void updateCreditAccount(final Map<String, Account> accountsMap, final String accountNumber, final double amount) {
        final var account = accountsMap.get(accountNumber);
        if (account != null) {
            account.updateCredit(amount);
        } else {
            accountsMap.put(accountNumber, Account.initializeWithCredit(accountNumber, amount));
        }
    }

    private static void updateDebitAccount(final Map<String, Account> accountsMap, final String accountNumber, final double amount) {
        final var account = accountsMap.get(accountNumber);
        if (account != null) {
            account.updateDebit(amount);
        } else {
            accountsMap.put(accountNumber, Account.initializeWithDebit(accountNumber, amount));
        }
    }
}
