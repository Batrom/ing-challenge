package com.batrom.ing.transactions;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

class DataGenerator {

    private static final DecimalFormat FORMATTER = new DecimalFormat("#.##");

    @Test
    void test() {
        for (int i = 0; i < 10; i++) {
            final var transactionsCount = RandomUtils.nextInt(100000, 100000);
            final var accountNumbersCount = RandomUtils.nextInt(10000, transactionsCount);

            final var data = generateTransactions(transactionsCount, accountNumbersCount)
                    .stream()
                    .map(transaction -> transaction.debitAccount + ";" + transaction.creditAccount + ";" + FORMATTER.format(transaction.amount))
                    .toList();
            try {
                final var fileName = RandomStringUtils.randomAlphabetic(10);
                Files.write(Paths.get("input/transactions/" + fileName), data);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }

    private static List<String> generateAccounts(final int accountNumbersCount) {
        final var accounts = new ArrayList<String>(accountNumbersCount);
        for (int i = 0; i < accountNumbersCount; i++) {
            accounts.add(randomAccount());
        }
        return accounts;
    }

    private static List<Transaction> generateTransactions(final int transactionsCount, final int accountNumbersCount) {
        final var accounts = generateAccounts(accountNumbersCount);
        final var transactions = new ArrayList<Transaction>(transactionsCount);
        for (int i = 0; i < transactionsCount; i++) {
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

    private static double randomAmount() {
        return ThreadLocalRandom.current().nextDouble() * randomInt(100_00);
    }

    private static Transaction randomTransaction(final List<String> accounts) {
        final var from = accounts.get(randomInt(accounts.size()));
        final var to = accounts.get(randomInt(accounts.size()));
        if (from.equals(to)) {
            return randomTransaction(accounts);
        }
        return new Transaction(from, to, randomAmount());
    }

    private record Transaction(String debitAccount, String creditAccount, double amount) {

    }

}