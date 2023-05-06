package com.batrom.ing.transactions2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import static com.batrom.ing.transactions2.ParsingUtil.toSortedArray;

class AccountBalanceChangesBucket extends ArrayList<AccountBalanceChange> {

    Callable<Account[]> toAccountsCallable() {
        return this::toAccounts;
    }

    private Account[] toAccounts() {
        if (!isEmpty()) {
            final var accountsMap = new HashMap<String, Account>();
            for (int i = 0; i < size(); i++) {
                final var change = get(i);
                final var amount = change.amount();

                if (change.amount() > 0) {
                    updateCreditAccount(change, amount, accountsMap);
                } else if (change.amount() < 0) {
                    updateDebitAccount(change, -amount, accountsMap);
                }
            }
            return toSortedArray(accountsMap);
        }
        return new Account[0];
    }

    private static void updateCreditAccount(final AccountBalanceChange change, final Float amount, final Map<String, Account> accountsMap) {
        final var accountNumber = change.account();
        final var account = accountsMap.get(accountNumber);
        if (account != null) {
            account.updateCredit(amount);
        } else {
            accountsMap.put(accountNumber, Account.initializeWithCredit(accountNumber, amount));
        }
    }

    private static void updateDebitAccount(final AccountBalanceChange change, final Float amount, final Map<String, Account> accountsMap) {
        final var account = change.account();
        final var accountSummary = accountsMap.get(account);
        if (accountSummary != null) {
            accountSummary.updateDebit(amount);
        } else {
            accountsMap.put(account, Account.initializeWithDebit(account, amount));
        }
    }

}