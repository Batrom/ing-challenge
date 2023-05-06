//package com.batrom.ing.transactions2;
//
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//
//@Service
//class TransactionsService1 implements TransactionsService {
//
//    @Override
//    public Account[] process(final AccountBalanceChange[] changes) {
//        processTransactions(changes);
//        final var accounts = toAccounts(accountWithSummaryMap);
//        Arrays.sort(accounts, Comparator.comparing(Account::getAccount));
//        return accounts;
//    }
//
//    private static Account[] processChanges(final AccountBalanceChange[] changes) {
//        final var accountWithSummaryMap = new HashMap<String, Account>();
//        for (int i = 0; i < changes.length; i++) {
//            final var change = changes[i];
//            final var amount = change.amount();
//
//            if (change.amount() > 0) {
//                updateCreditAccount(change, amount, accountWithSummaryMap);
//            } else if (change.amount() < 0) {
//                updateDebitAccount(change, -amount, accountWithSummaryMap);
//            }
//        }
//        final var accounts = accountWithSummaryMap.values().toArray(new Account[0]);
//        Arrays.sort(accounts, Comparator.comparing(Account::getAccount));
//        return accounts;
//    }
//
//    private Account[] toAccounts(final Map<String, Account> accountWithSummaryMap) {
//        final var entries = new ArrayList<>(accountWithSummaryMap.entrySet());
//        final var accounts = new Account[entries.size()];
//
//        for (int i = 0; i < entries.size(); i++) {
//            final var entry = entries.get(i);
//            final var summary = entry.getValue();
//            accounts[i] = new Account(entry.getKey(), summary.debitCount, summary.creditCount, summary.balance);
//        }
//        return accounts;
//    }
//
//    private void processTransactions(final AccountBalanceChange[] changes, final Map<String, Account> accountWithSummaryMap) {
//        for (int i = 0; i < changes.length; i++) {
//            final var change = changes[i];
//            final var amount = change.amount();
//
//            updateCreditAccount(change, amount, accountWithSummaryMap);
//            updateDebitAccount(change, amount, accountWithSummaryMap);
//        }
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
//}
