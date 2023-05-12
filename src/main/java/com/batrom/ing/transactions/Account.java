package com.batrom.ing.transactions;

import java.io.Serializable;
import java.util.Objects;

public class Account implements Serializable {
    private final String account;
    private int debitCount;
    private int creditCount;
    private double balance;

    Account(final String account, final int debitCount, final int creditCount, final double balance) {
        this.account = account;
        this.debitCount = debitCount;
        this.creditCount = creditCount;
        this.balance = balance;
    }

    static Account initializeWithDebit(final String accountNumber, final double balance) {
        return new Account(accountNumber, 1, 0, balance);
    }

    static Account initializeWithCredit(final String accountNumber, final double balance) {
        return new Account(accountNumber, 0, 1, balance);
    }

    void updateDebit(final double amount) {
        debitCount++;
        balance += amount;
    }

    void updateCredit(final double amount) {
        creditCount++;
        balance += amount;
    }

    String getAccount() {
        return account;
    }

    int getDebitCount() {
        return debitCount;
    }

    int getCreditCount() {
        return creditCount;
    }

    double getBalance() {
        return balance;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Account other = (Account) o;
        return Objects.equals(account, other.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account);
    }
}