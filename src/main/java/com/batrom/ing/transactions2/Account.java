package com.batrom.ing.transactions2;

import java.io.Serializable;
import java.util.Objects;

public class Account implements Serializable {
    private final String account;
    private int debitCount;
    private int creditCount;
    private float balance;

    Account(final String account, final int debitCount, final int creditCount, final float balance) {
        this.account = account;
        this.debitCount = debitCount;
        this.creditCount = creditCount;
        this.balance = balance;
    }

    String getAccount() {
        return account;
    }

    static Account initializeWithDebit(final String accountNumber, final float balance) {
        return new Account(accountNumber, 1, 0, balance);
    }

    static Account initializeWithCredit(final String accountNumber, final float balance) {
        return new Account(accountNumber, 0, 1, balance);
    }

    void updateDebit(final float amount) {
        debitCount++;
        balance += amount;
    }

    void updateCredit(final float amount) {
        creditCount++;
        balance += amount;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Account account1 = (Account) o;
        return Objects.equals(account, account1.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account);
    }
}