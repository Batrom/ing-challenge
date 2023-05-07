package com.batrom.ing.transactions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import static com.batrom.ing.transactions.TransactionsHelper.EMPTY_ACCOUNTS;
import static com.batrom.ing.transactions.TransactionsHelper.toSortedArray;

record AccountBalanceChangesBucket(List<AccountBalanceChange> changes) {


    AccountBalanceChangesBucket() {
        this(new ArrayList<>());
    }

    void add(final AccountBalanceChange change) {
        changes.add(change);
    }

    void merge(final AccountBalanceChangesBucket bucket) {
        changes.addAll(bucket.changes());
    }

    Callable<Account[]> toAccountsCallable() {
        return this::toAccounts;
    }

    private Account[] toAccounts() {
        if (!changes.isEmpty()) {
            final var accountsMap = new HashMap<String, Account>();
            for (int i = 0; i < changes.size(); i++) {
                final var change = changes.get(i);
                final var amount = change.amount();

                if (change.amount() > 0) {
                    updateCreditAccount(change, amount, accountsMap);
                } else if (change.amount() < 0) {
                    updateDebitAccount(change, amount, accountsMap);
                }
            }
            return toSortedArray(accountsMap);
        }
        return EMPTY_ACCOUNTS;
    }

    private static void updateCreditAccount(final AccountBalanceChange change, final double amount, final Map<String, Account> accountsMap) {
        final var accountNumber = change.account();
        final var account = accountsMap.get(accountNumber);
        if (account != null) {
            account.updateCredit(amount);
        } else {
            accountsMap.put(accountNumber, Account.initializeWithCredit(accountNumber, amount));
        }
    }

    private static void updateDebitAccount(final AccountBalanceChange change, final double amount, final Map<String, Account> accountsMap) {
        final var account = change.account();
        final var accountSummary = accountsMap.get(account);
        if (accountSummary != null) {
            accountSummary.updateDebit(amount);
        } else {
            accountsMap.put(account, Account.initializeWithDebit(account, amount));
        }
    }

}