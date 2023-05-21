package com.batrom.ing.transactions;

import java.io.Serializable;
import java.util.Objects;

import static com.batrom.ing.SerializationHelper.divideByHundredAndRoundToTwoDecimalPlaces;

public class Account implements Serializable {
    private final String account;
    private int debitCount;
    private int creditCount;
    private long balance;

    Account(final String account, final int debitCount, final int creditCount, final long balance) {
        this.account = account;
        this.debitCount = debitCount;
        this.creditCount = creditCount;
        this.balance = balance;
    }

    static Account initializeWithDebit(final String accountNumber, final long balance) {
        return new Account(accountNumber, 1, 0, balance);
    }

    static Account initializeWithCredit(final String accountNumber, final long balance) {
        return new Account(accountNumber, 0, 1, balance);
    }

    void updateDebit(final long amount) {
        debitCount++;
        balance += amount;
    }

    void updateCredit(final long amount) {
        creditCount++;
        balance += amount;
    }

    String getAccount() {
        return account;
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

    public String toJson() {
        return "{" +
               "\"account\":\"" + account + "\"," +
               "\"debitCount\":" + debitCount + "," +
               "\"creditCount\":" + creditCount + "," +
               "\"balance\":" + divideByHundredAndRoundToTwoDecimalPlaces(balance) +
               "}";
    }
}