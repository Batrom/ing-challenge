package com.batrom.ing.transactions;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

@Service
class TransactionsService5 implements TransactionsService {

    @Override
    public Account[] process(final List<Transaction> transactions) {
        final var change = toAccountsBalanceChange(transactions);

        final var executorService = Executors.newFixedThreadPool(10);
        final var accountsBuckets = new Account[11][];

        try {
            final var futures = executorService.invokeAll(List.of(
                    () -> processChanges(change.accountsStartingWith0()),
                    () -> processChanges(change.accountsStartingWith1()),
                    () -> processChanges(change.accountsStartingWith2()),
                    () -> processChanges(change.accountsStartingWith3()),
                    () -> processChanges(change.accountsStartingWith4()),
                    () -> processChanges(change.accountsStartingWith5()),
                    () -> processChanges(change.accountsStartingWith6()),
                    () -> processChanges(change.accountsStartingWith7()),
                    () -> processChanges(change.accountsStartingWith8()),
                    () -> processChanges(change.accountsStartingWith9()),
                    () -> processChanges(change.otherAccounts())));
            for (int i = 0; i < futures.size(); i++) {
                accountsBuckets[i] = (Account[]) futures.get(i).get();
            }
        } catch (final InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }

        return createResult(accountsBuckets);
    }

    private static Account[] createResult(final Account[][] accountsBuckets) {
        final var size = calculateResultSize(accountsBuckets);
        return mergeBuckets(accountsBuckets, size);
    }

    private static Account[] mergeBuckets(final Account[][] accountsBuckets, final int size) {
        final var result = Arrays.copyOf(accountsBuckets[0], size);
        getArraycopy(accountsBuckets[1], result, accountsBuckets[0].length);
        getArraycopy(accountsBuckets[2], result, accountsBuckets[0].length + accountsBuckets[1].length);
        getArraycopy(accountsBuckets[3], result, accountsBuckets[0].length + accountsBuckets[1].length + accountsBuckets[2].length);
        getArraycopy(accountsBuckets[4], result, accountsBuckets[0].length + accountsBuckets[1].length + accountsBuckets[2].length + accountsBuckets[3].length);
        getArraycopy(accountsBuckets[5], result, accountsBuckets[0].length + accountsBuckets[1].length + accountsBuckets[2].length + accountsBuckets[3].length + accountsBuckets[4].length);
        getArraycopy(accountsBuckets[6], result, accountsBuckets[0].length + accountsBuckets[1].length + accountsBuckets[2].length + accountsBuckets[3].length + accountsBuckets[4].length + accountsBuckets[5].length);
        getArraycopy(accountsBuckets[7], result, accountsBuckets[0].length + accountsBuckets[1].length + accountsBuckets[2].length + accountsBuckets[3].length + accountsBuckets[4].length + accountsBuckets[5].length + accountsBuckets[6].length);
        getArraycopy(accountsBuckets[8], result, accountsBuckets[0].length + accountsBuckets[1].length + accountsBuckets[2].length + accountsBuckets[3].length + accountsBuckets[4].length + accountsBuckets[5].length + accountsBuckets[6].length + accountsBuckets[7].length);
        getArraycopy(accountsBuckets[9], result, accountsBuckets[0].length + accountsBuckets[1].length + accountsBuckets[2].length + accountsBuckets[3].length + accountsBuckets[4].length + accountsBuckets[5].length + accountsBuckets[6].length + accountsBuckets[7].length + accountsBuckets[8].length);
        getArraycopy(accountsBuckets[10], result, accountsBuckets[0].length + accountsBuckets[1].length + accountsBuckets[2].length + accountsBuckets[3].length + accountsBuckets[4].length + accountsBuckets[5].length + accountsBuckets[6].length + accountsBuckets[7].length + accountsBuckets[8].length + accountsBuckets[9].length);
        return result;
    }

    private static int calculateResultSize(final Account[][] accountsBuckets) {
        return accountsBuckets[0].length +
                accountsBuckets[1].length +
                accountsBuckets[2].length +
                accountsBuckets[3].length +
                accountsBuckets[4].length +
                accountsBuckets[5].length +
                accountsBuckets[6].length +
                accountsBuckets[7].length +
                accountsBuckets[8].length +
                accountsBuckets[9].length +
                accountsBuckets[10].length;
    }

    private static AccountsBalanceChange toAccountsBalanceChange(final List<Transaction> transactions) {
        final List<AccountBalanceChange> changes[] = new ArrayList<AccountBalanceChange>[10];
//        final var map = new HashMap<Character, List<AccountBalanceChange>>(10);
//        map.put('0',)

        final List<AccountBalanceChange>[] changes = new ArrayList<>[1]{new ArrayList<AccountBalanceChange>};
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
