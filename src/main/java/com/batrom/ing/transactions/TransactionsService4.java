package com.batrom.ing.transactions;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
class TransactionsService4 implements TransactionsService {

//    public Account[] process(final AccountsBalanceChange change) {

    @Override
    public Account[] process(final List<Transaction> transactions) {
        final var change = toAccountsBalanceChange(transactions);

        final var accountsStartingWith0 = processChanges(change.accountsStartingWith0());
        final var accountsStartingWith1 = processChanges(change.accountsStartingWith1());
        final var accountsStartingWith2 = processChanges(change.accountsStartingWith2());
        final var accountsStartingWith3 = processChanges(change.accountsStartingWith3());
        final var accountsStartingWith4 = processChanges(change.accountsStartingWith4());
        final var accountsStartingWith5 = processChanges(change.accountsStartingWith5());
        final var accountsStartingWith6 = processChanges(change.accountsStartingWith6());
        final var accountsStartingWith7 = processChanges(change.accountsStartingWith7());
        final var accountsStartingWith8 = processChanges(change.accountsStartingWith8());
        final var accountsStartingWith9 = processChanges(change.accountsStartingWith9());
        final var otherAccounts = processChanges(change.otherAccounts());

        final var size = accountsStartingWith0.length +
                accountsStartingWith1.length +
                accountsStartingWith2.length +
                accountsStartingWith3.length +
                accountsStartingWith4.length +
                accountsStartingWith5.length +
                accountsStartingWith6.length +
                accountsStartingWith7.length +
                accountsStartingWith8.length +
                accountsStartingWith9.length +
                otherAccounts.length;

        final var result = Arrays.copyOf(accountsStartingWith0, size);
        getArraycopy(accountsStartingWith1, result, accountsStartingWith0.length);
        getArraycopy(accountsStartingWith2, result, accountsStartingWith0.length + accountsStartingWith1.length);
        getArraycopy(accountsStartingWith3, result, accountsStartingWith0.length + accountsStartingWith1.length + accountsStartingWith2.length);
        getArraycopy(accountsStartingWith4, result, accountsStartingWith0.length + accountsStartingWith1.length + accountsStartingWith2.length + accountsStartingWith3.length);
        getArraycopy(accountsStartingWith5, result, accountsStartingWith0.length + accountsStartingWith1.length + accountsStartingWith2.length + accountsStartingWith3.length + accountsStartingWith4.length);
        getArraycopy(accountsStartingWith6, result, accountsStartingWith0.length + accountsStartingWith1.length + accountsStartingWith2.length + accountsStartingWith3.length + accountsStartingWith4.length + accountsStartingWith5.length);
        getArraycopy(accountsStartingWith7, result, accountsStartingWith0.length + accountsStartingWith1.length + accountsStartingWith2.length + accountsStartingWith3.length + accountsStartingWith4.length + accountsStartingWith5.length + accountsStartingWith6.length);
        getArraycopy(accountsStartingWith8, result, accountsStartingWith0.length + accountsStartingWith1.length + accountsStartingWith2.length + accountsStartingWith3.length + accountsStartingWith4.length + accountsStartingWith5.length + accountsStartingWith6.length + accountsStartingWith7.length);
        getArraycopy(accountsStartingWith9, result, accountsStartingWith0.length + accountsStartingWith1.length + accountsStartingWith2.length + accountsStartingWith3.length + accountsStartingWith4.length + accountsStartingWith5.length + accountsStartingWith6.length + accountsStartingWith7.length + accountsStartingWith8.length);
        getArraycopy(otherAccounts, result, accountsStartingWith0.length + accountsStartingWith1.length + accountsStartingWith2.length + accountsStartingWith3.length + accountsStartingWith4.length + accountsStartingWith5.length + accountsStartingWith6.length + accountsStartingWith7.length + accountsStartingWith8.length + accountsStartingWith9.length);
        return result;
    }

    private static AccountsBalanceChange toAccountsBalanceChange(final List<Transaction> transactions) {
        final var changesForAccountsStartingWith0 = new ArrayList<AccountBalanceChange>();
        final var changesForAccountsStartingWith1 = new ArrayList<AccountBalanceChange>();
        final var changesForAccountsStartingWith2 = new ArrayList<AccountBalanceChange>();
        final var changesForAccountsStartingWith3 = new ArrayList<AccountBalanceChange>();
        final var changesForAccountsStartingWith4 = new ArrayList<AccountBalanceChange>();
        final var changesForAccountsStartingWith5 = new ArrayList<AccountBalanceChange>();
        final var changesForAccountsStartingWith6 = new ArrayList<AccountBalanceChange>();
        final var changesForAccountsStartingWith7 = new ArrayList<AccountBalanceChange>();
        final var changesForAccountsStartingWith8 = new ArrayList<AccountBalanceChange>();
        final var changesForAccountsStartingWith9 = new ArrayList<AccountBalanceChange>();
        final var changesForOtherAccounts = new ArrayList<AccountBalanceChange>();

        for (int i = 0; i < transactions.size(); i++) {
            final var transaction = transactions.get(i);

            final var creditAccountBalanceChange = new AccountBalanceChange(transaction.creditAccount(), transaction.amount());
            final var debitAccountBalanceChange = new AccountBalanceChange(transaction.debitAccount(), -transaction.amount());
            switch (transaction.creditAccount().charAt(0)) {
                case '0' -> changesForAccountsStartingWith0.add(creditAccountBalanceChange);
                case '1' -> changesForAccountsStartingWith1.add(creditAccountBalanceChange);
                case '2' -> changesForAccountsStartingWith2.add(creditAccountBalanceChange);
                case '3' -> changesForAccountsStartingWith3.add(creditAccountBalanceChange);
                case '4' -> changesForAccountsStartingWith4.add(creditAccountBalanceChange);
                case '5' -> changesForAccountsStartingWith5.add(creditAccountBalanceChange);
                case '6' -> changesForAccountsStartingWith6.add(creditAccountBalanceChange);
                case '7' -> changesForAccountsStartingWith7.add(creditAccountBalanceChange);
                case '8' -> changesForAccountsStartingWith8.add(creditAccountBalanceChange);
                case '9' -> changesForAccountsStartingWith9.add(creditAccountBalanceChange);
                default -> changesForOtherAccounts.add(creditAccountBalanceChange);
            }

            switch (transaction.debitAccount().charAt(0)) {
                case '0' -> changesForAccountsStartingWith0.add(debitAccountBalanceChange);
                case '1' -> changesForAccountsStartingWith1.add(debitAccountBalanceChange);
                case '2' -> changesForAccountsStartingWith2.add(debitAccountBalanceChange);
                case '3' -> changesForAccountsStartingWith3.add(debitAccountBalanceChange);
                case '4' -> changesForAccountsStartingWith4.add(debitAccountBalanceChange);
                case '5' -> changesForAccountsStartingWith5.add(debitAccountBalanceChange);
                case '6' -> changesForAccountsStartingWith6.add(debitAccountBalanceChange);
                case '7' -> changesForAccountsStartingWith7.add(debitAccountBalanceChange);
                case '8' -> changesForAccountsStartingWith8.add(debitAccountBalanceChange);
                case '9' -> changesForAccountsStartingWith9.add(debitAccountBalanceChange);
                default -> changesForOtherAccounts.add(debitAccountBalanceChange);
            }
        }

        return new AccountsBalanceChange(
                changesForAccountsStartingWith0,
                changesForAccountsStartingWith1,
                changesForAccountsStartingWith2,
                changesForAccountsStartingWith3,
                changesForAccountsStartingWith4,
                changesForAccountsStartingWith5,
                changesForAccountsStartingWith6,
                changesForAccountsStartingWith7,
                changesForAccountsStartingWith8,
                changesForAccountsStartingWith9,
                changesForOtherAccounts);
    }

    private static void getArraycopy(final Account[] arrayToBeCopied, final Account[] destinationArray, final int startingPointInDestinationArray) {
        System.arraycopy(arrayToBeCopied, 0, destinationArray, startingPointInDestinationArray, arrayToBeCopied.length);
    }

    private static Account[] processChanges(final List<AccountBalanceChange> changes) {
        final var accountWithSummaryMap = new HashMap<String, Account>();
        if (!changes.isEmpty()) {
            for (int i = 0; i < changes.size(); i++) {
                final var change = changes.get(i);
                final var amount = change.amount();

                if (change.amount() > 0) {
                    updateCreditAccount(change, amount, accountWithSummaryMap);
                } else if (change.amount() < 0) {
                    updateDebitAccount(change, -amount, accountWithSummaryMap);
                }
            }
            final var accounts = accountWithSummaryMap.values().toArray(new Account[0]);
            Arrays.sort(accounts, Comparator.comparing(Account::getAccount));
            return accounts;
        }
        return new Account[0];
    }

    private static void updateCreditAccount(final AccountBalanceChange change, final Float amount, final Map<String, Account> accountWithSummaryMap) {
        final var accountNumber = change.account();
        final var account = accountWithSummaryMap.get(accountNumber);
        if (account != null) {
            account.updateCredit(amount);
        } else {
            accountWithSummaryMap.put(accountNumber, Account.initializeWithCredit(accountNumber, amount));
        }
    }

    private static void updateDebitAccount(final AccountBalanceChange change, final Float amount, final Map<String, Account> accountWithSummaryMap) {
        final var account = change.account();
        final var accountSummary = accountWithSummaryMap.get(account);
        if (accountSummary != null) {
            accountSummary.updateDebit(amount);
        } else {
            accountWithSummaryMap.put(account, Account.initializeWithDebit(account, amount));
        }
    }
}
