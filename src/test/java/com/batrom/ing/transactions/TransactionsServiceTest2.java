package com.batrom.ing.transactions;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

class TransactionsServiceTest2 {

    private static final int ACCOUNTS_NUMBER = 2;
    private static final int TRANSACTIONS_NUMBER = 4;

    @Test
    void test() {
        final TransactionsService service1 = new TransactionsService1();
        final TransactionsService service4 = new TransactionsService4();
        final TransactionsService service5 = new TransactionsService5();
        final List<Transaction> transactions = generateTransactions();

        final var result1 = service1.process(transactions);
        final var result4 = service4.process(transactions);
        final var result5 = service5.process(transactions);
        System.out.println("xxx");
    }

    private static List<String> generateAccounts() {
        final var size = ACCOUNTS_NUMBER;
        final var accounts = new ArrayList<String>(size);
        for (int i = 0; i < size; i++) {
            accounts.add(randomAccount());
        }
        return accounts;
    }

    private static List<Transaction> generateTransactions() {
        final var accounts = generateAccounts();
        final var size = TRANSACTIONS_NUMBER;
        final var transactions = new ArrayList<Transaction>(size);
        for (int i = 0; i < size; i++) {
            transactions.add(randomTransaction(accounts));
        }
        return transactions;
    }

    private static String randomAccount() {
        final var length = 26;
        final var numbers = "0123456789";

        final var result = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            result.append(numbers.charAt(randomInt(10)));
        }

        return result.toString();
    }

    private static int randomInt(final int upperLimit) {
        return ThreadLocalRandom.current().nextInt(0, upperLimit);
    }

    private static float randomAmount() {
        return ThreadLocalRandom.current().nextFloat();
    }

    private static Transaction randomTransaction(final List<String> accounts) {
        final var from = accounts.get(randomInt(accounts.size()));
        final var to = accounts.get(randomInt(accounts.size()));
        if (from.equals(to)) {
            return randomTransaction(accounts);
        }
        return new Transaction(from, to, randomAmount());
    }

}