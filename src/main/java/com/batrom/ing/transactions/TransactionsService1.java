package com.batrom.ing.transactions;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
class TransactionsService1 implements TransactionsService {

    @Override
    public Account[] process(final List<Transaction> transactions) {
        final var accountWithSummaryMap = new HashMap<String, AccountSummary>();

        processTransactions(transactions, accountWithSummaryMap);
        final var accounts = toAccounts(accountWithSummaryMap);
        Arrays.sort(accounts, Comparator.comparing(Account::getAccount));
        return accounts;
    }

    private Account[] toAccounts(final Map<String, AccountSummary> accountWithSummaryMap) {
        final var entries = new ArrayList<>(accountWithSummaryMap.entrySet());
        final var accounts = new Account[entries.size()];

        for (int i = 0; i < entries.size(); i++) {
            final var entry = entries.get(i);
            final var summary = entry.getValue();
            accounts[i] = new Account(entry.getKey(), summary.debitCount, summary.creditCount, summary.balance);
        }
        return accounts;
    }

    private void processTransactions(final List<Transaction> transactions, final Map<String, AccountSummary> accountWithSummaryMap) {
        for (int i = 0; i < transactions.size(); i++) {
            final var transaction = transactions.get(i);
            final var amount = transaction.amount();

            updateCreditAccount(transaction, amount, accountWithSummaryMap);
            updateDebitAccount(transaction, amount, accountWithSummaryMap);
        }
    }

    private void updateCreditAccount(final Transaction transaction, final Float amount, final Map<String, AccountSummary> accountWithSummaryMap) {
        final var creditAccountNumber = transaction.creditAccount();
        final var creditAccount = accountWithSummaryMap.get(creditAccountNumber);
        if (creditAccount != null) {
            creditAccount.updateCredit(amount);
        } else {
            accountWithSummaryMap.put(creditAccountNumber, AccountSummary.initializeWithCredit(amount));
        }
    }

    private void updateDebitAccount(final Transaction transaction, final Float amount, final Map<String, AccountSummary> accountWithSummaryMap) {
        final var debitAccountNumber = transaction.debitAccount();
        final var debitAccount = accountWithSummaryMap.get(debitAccountNumber);

        if (debitAccount != null) {
            debitAccount.updateDebit(amount);
        } else {
            accountWithSummaryMap.put(debitAccountNumber, AccountSummary.initializeWithDebit(amount));
        }
    }

    private static class AccountSummary {
        private int debitCount;
        private int creditCount;
        private float balance;

        private static AccountSummary initializeWithDebit(final float amount) {
            final var summary = new AccountSummary();
            summary.updateDebit(amount);
            return summary;
        }

        private static AccountSummary initializeWithCredit(final float amount) {
            final var summary = new AccountSummary();
            summary.updateCredit(amount);
            return summary;
        }

        private void updateDebit(final float amount) {
            debitCount++;
            balance -= amount;
        }

        private void updateCredit(final float amount) {
            creditCount++;
            balance += amount;
        }
    }
}
