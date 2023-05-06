//package com.batrom.ing.transactions2;
//
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.Executors;
//
//@Service
//class TransactionsService5 implements TransactionsService {
//
//    @Override
//    public Account[] process(final AccountBalanceChange[] changes) {
//        final var change = toBuckets(changes);
//        final var accountsBuckets = processAsynchronously(change);
//        return createResult(accountsBuckets);
//    }
//
//    private static Account[][] processAsynchronously(final Buckets change) {
//        final var executorService = Executors.newFixedThreadPool(11);
//        final var accountsBuckets = new Account[11][];
//
//        try {
//            final var futures = executorService.invokeAll(List.of(
//                    () -> processChanges(change.accountsStartingWith0()),
//                    () -> processChanges(change.accountsStartingWith1()),
//                    () -> processChanges(change.accountsStartingWith2()),
//                    () -> processChanges(change.accountsStartingWith3()),
//                    () -> processChanges(change.accountsStartingWith4()),
//                    () -> processChanges(change.accountsStartingWith5()),
//                    () -> processChanges(change.accountsStartingWith6()),
//                    () -> processChanges(change.accountsStartingWith7()),
//                    () -> processChanges(change.accountsStartingWith8()),
//                    () -> processChanges(change.accountsStartingWith9()),
//                    () -> processChanges(change.otherAccounts())));
//            for (int i = 0; i < futures.size(); i++) {
//                accountsBuckets[i] = (Account[]) futures.get(i).get();
//            }
//        } catch (final InterruptedException | ExecutionException e) {
//            throw new RuntimeException(e);
//        } finally {
//            executorService.shutdown();
//        }
//        return accountsBuckets;
//    }
//
//    private static Account[] createResult(final Account[][] accountsBuckets) {
//        final var size = calculateResultSize(accountsBuckets);
//        return mergeBuckets(accountsBuckets, size);
//    }
//
//    private static Account[] mergeBuckets(final Account[][] accountsBuckets, final int size) {
//        final var result = Arrays.copyOf(accountsBuckets[0], size);
//        getArraycopy(accountsBuckets[1], result, accountsBuckets[0].length);
//        getArraycopy(accountsBuckets[2], result, accountsBuckets[0].length + accountsBuckets[1].length);
//        getArraycopy(accountsBuckets[3], result, accountsBuckets[0].length + accountsBuckets[1].length + accountsBuckets[2].length);
//        getArraycopy(accountsBuckets[4], result, accountsBuckets[0].length + accountsBuckets[1].length + accountsBuckets[2].length + accountsBuckets[3].length);
//        getArraycopy(accountsBuckets[5], result, accountsBuckets[0].length + accountsBuckets[1].length + accountsBuckets[2].length + accountsBuckets[3].length + accountsBuckets[4].length);
//        getArraycopy(accountsBuckets[6], result, accountsBuckets[0].length + accountsBuckets[1].length + accountsBuckets[2].length + accountsBuckets[3].length + accountsBuckets[4].length + accountsBuckets[5].length);
//        getArraycopy(accountsBuckets[7], result, accountsBuckets[0].length + accountsBuckets[1].length + accountsBuckets[2].length + accountsBuckets[3].length + accountsBuckets[4].length + accountsBuckets[5].length + accountsBuckets[6].length);
//        getArraycopy(accountsBuckets[8], result, accountsBuckets[0].length + accountsBuckets[1].length + accountsBuckets[2].length + accountsBuckets[3].length + accountsBuckets[4].length + accountsBuckets[5].length + accountsBuckets[6].length + accountsBuckets[7].length);
//        getArraycopy(accountsBuckets[9], result, accountsBuckets[0].length + accountsBuckets[1].length + accountsBuckets[2].length + accountsBuckets[3].length + accountsBuckets[4].length + accountsBuckets[5].length + accountsBuckets[6].length + accountsBuckets[7].length + accountsBuckets[8].length);
//        getArraycopy(accountsBuckets[10], result, accountsBuckets[0].length + accountsBuckets[1].length + accountsBuckets[2].length + accountsBuckets[3].length + accountsBuckets[4].length + accountsBuckets[5].length + accountsBuckets[6].length + accountsBuckets[7].length + accountsBuckets[8].length + accountsBuckets[9].length);
//        return result;
//    }
//
//    private static int calculateResultSize(final Account[][] accountsBuckets) {
//        return accountsBuckets[0].length +
//                accountsBuckets[1].length +
//                accountsBuckets[2].length +
//                accountsBuckets[3].length +
//                accountsBuckets[4].length +
//                accountsBuckets[5].length +
//                accountsBuckets[6].length +
//                accountsBuckets[7].length +
//                accountsBuckets[8].length +
//                accountsBuckets[9].length +
//                accountsBuckets[10].length;
//    }
//
//    private static Buckets toBuckets(final AccountBalanceChange[] changes) {
//        final var changesForAccountsStartingWith0 = new ArrayList<AccountBalanceChange>();
//        final var changesForAccountsStartingWith1 = new ArrayList<AccountBalanceChange>();
//        final var changesForAccountsStartingWith2 = new ArrayList<AccountBalanceChange>();
//        final var changesForAccountsStartingWith3 = new ArrayList<AccountBalanceChange>();
//        final var changesForAccountsStartingWith4 = new ArrayList<AccountBalanceChange>();
//        final var changesForAccountsStartingWith5 = new ArrayList<AccountBalanceChange>();
//        final var changesForAccountsStartingWith6 = new ArrayList<AccountBalanceChange>();
//        final var changesForAccountsStartingWith7 = new ArrayList<AccountBalanceChange>();
//        final var changesForAccountsStartingWith8 = new ArrayList<AccountBalanceChange>();
//        final var changesForAccountsStartingWith9 = new ArrayList<AccountBalanceChange>();
//        final var changesForOtherAccounts = new ArrayList<AccountBalanceChange>();
//
//        for (int i = 0; i < changes.length; i++) {
//            final var change = changes[i];
//
//            switch (change.account().charAt(0)) {
//                case '0' -> changesForAccountsStartingWith0.add(change);
//                case '1' -> changesForAccountsStartingWith1.add(change);
//                case '2' -> changesForAccountsStartingWith2.add(change);
//                case '3' -> changesForAccountsStartingWith3.add(change);
//                case '4' -> changesForAccountsStartingWith4.add(change);
//                case '5' -> changesForAccountsStartingWith5.add(change);
//                case '6' -> changesForAccountsStartingWith6.add(change);
//                case '7' -> changesForAccountsStartingWith7.add(change);
//                case '8' -> changesForAccountsStartingWith8.add(change);
//                case '9' -> changesForAccountsStartingWith9.add(change);
//                default -> changesForOtherAccounts.add(change);
//            }
//        }
//
//        return new Buckets(
//                changesForAccountsStartingWith0,
//                changesForAccountsStartingWith1,
//                changesForAccountsStartingWith2,
//                changesForAccountsStartingWith3,
//                changesForAccountsStartingWith4,
//                changesForAccountsStartingWith5,
//                changesForAccountsStartingWith6,
//                changesForAccountsStartingWith7,
//                changesForAccountsStartingWith8,
//                changesForAccountsStartingWith9,
//                changesForOtherAccounts);
//    }
//
//    private static void getArraycopy(final Account[] arrayToBeCopied, final Account[] destinationArray, final int startingPointInDestinationArray) {
//        System.arraycopy(arrayToBeCopied, 0, destinationArray, startingPointInDestinationArray, arrayToBeCopied.length);
//    }
//
//    private static Account[] processChanges(final List<AccountBalanceChange> changes) {
//        final var accountWithSummaryMap = new HashMap<String, Account>();
//        if (!changes.isEmpty()) {
//            for (int i = 0; i < changes.size(); i++) {
//                final var change = changes.get(i);
//                final var amount = change.amount();
//
//                if (change.amount() > 0) {
//                    updateCreditAccount(change, amount, accountWithSummaryMap);
//                } else if (change.amount() < 0) {
//                    updateDebitAccount(change, -amount, accountWithSummaryMap);
//                }
//            }
//            final var accounts = accountWithSummaryMap.values().toArray(new Account[0]);
//            Arrays.sort(accounts, Comparator.comparing(Account::getAccount));
//            return accounts;
//        }
//        return new Account[0];
//    }
//
//    private static void updateCreditAccount(final AccountBalanceChange change, final Float amount, final Map<String, Account> accountWithSummaryMap) {
//        final var accountNumber = change.account();
//        final var account = accountWithSummaryMap.get(accountNumber);
//        if (account != null) {
//            account.updateCredit(amount);
//        } else {
//            accountWithSummaryMap.put(accountNumber, Account.initializeWithCredit(accountNumber, amount));
//        }
//    }
//
//    private static void updateDebitAccount(final AccountBalanceChange change, final Float amount, final Map<String, Account> accountWithSummaryMap) {
//        final var account = change.account();
//        final var accountSummary = accountWithSummaryMap.get(account);
//        if (accountSummary != null) {
//            accountSummary.updateDebit(amount);
//        } else {
//            accountWithSummaryMap.put(account, Account.initializeWithDebit(account, amount));
//        }
//    }
//
//    private record Buckets(List<AccountBalanceChange> accountsStartingWith0,
//                           List<AccountBalanceChange> accountsStartingWith1,
//                           List<AccountBalanceChange> accountsStartingWith2,
//                           List<AccountBalanceChange> accountsStartingWith3,
//                           List<AccountBalanceChange> accountsStartingWith4,
//                           List<AccountBalanceChange> accountsStartingWith5,
//                           List<AccountBalanceChange> accountsStartingWith6,
//                           List<AccountBalanceChange> accountsStartingWith7,
//                           List<AccountBalanceChange> accountsStartingWith8,
//                           List<AccountBalanceChange> accountsStartingWith9,
//                           List<AccountBalanceChange> otherAccounts) {
//    }
//}
