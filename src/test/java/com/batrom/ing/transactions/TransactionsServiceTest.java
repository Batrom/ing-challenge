//package com.batrom.ing.transactions;
//
//import org.junit.jupiter.api.Test;
//import org.openjdk.jmh.annotations.*;
//import org.openjdk.jmh.runner.Runner;
//import org.openjdk.jmh.runner.options.Options;
//import org.openjdk.jmh.runner.options.OptionsBuilder;
//import org.openjdk.jmh.runner.options.TimeValue;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ThreadLocalRandom;
//import java.util.concurrent.TimeUnit;
//
//public class TransactionsServiceTest {
//
//    @Test
//    public void runBenchmarks() throws Exception {
//        Options options = new OptionsBuilder()
//                .include(this.getClass().getName() + ".*")
//                .mode(Mode.AverageTime)
//                .warmupTime(TimeValue.seconds(1))
//                .warmupIterations(1)
//                .threads(1)
//                .measurementIterations(2)
//                .forks(1)
//                .shouldFailOnError(true)
//                .shouldDoGC(true)
//                .build();
//
//        new Runner(options).run();
//    }
//
//    @Benchmark
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    public void service1(final BenchmarkState state) {
//        state.service1.process(state.transactions);
//    }
//
//    @Benchmark
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    public void service5(final BenchmarkState state) {
//        state.service5.process(state.transactions);
//    }
//
//    private static List<String> generateAccounts() {
//        final var size = 1_000_000;
//        final var accounts = new ArrayList<String>(size);
//        for (int i = 0; i < size; i++) {
//            accounts.add(randomAccount());
//        }
//        return accounts;
//    }
//
//    private static List<Transaction> generateTransactions() {
//        final var accounts = generateAccounts();
//        final var size = 10_000_000;
//        final var transactions = new ArrayList<Transaction>(size);
//        for (int i = 0; i < size; i++) {
//            transactions.add(randomTransaction(accounts));
//        }
//        return transactions;
//    }
//
//    private static String randomAccount() {
//        final var length = 26;
//        final var numbers = "0123456789";
//
//        final var result = new StringBuilder(length);
//
//        for (int i = 0; i < length; i++) {
//            result.append(numbers.charAt(randomInt(10)));
//        }
//
//        return result.toString();
//    }
//
//    private static int randomInt(final int upperLimit) {
//        return ThreadLocalRandom.current().nextInt(0, upperLimit);
//    }
//
//    private static float randomAmount() {
//        return ThreadLocalRandom.current().nextFloat();
//    }
//
//    private static Transaction randomTransaction(final List<String> accounts) {
//        final var from = accounts.get(randomInt(accounts.size()));
//        final var to = accounts.get(randomInt(accounts.size()));
//        if (from.equals(to)) {
//            return randomTransaction(accounts);
//        }
//        return new Transaction(from, to, randomAmount());
//    }
//
//    @State(Scope.Thread)
//    public static class BenchmarkState {
//        final TransactionsService service1 = new TransactionsService1();
//        final TransactionsService service4 = new TransactionsService4();
//        final TransactionsService service5 = new TransactionsService5();
//        final List<Transaction> transactions = generateTransactions();
//    }
//
//}