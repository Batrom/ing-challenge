package com.batrom.ing.transactions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static com.batrom.ing.transactions.TransactionsHelper.EMPTY_ACCOUNTS;

class LargeInputToResponseConverter {

    private static final int NUMBER_OF_BUCKETS = 11;

    static Account[] convert(final List<AccountBalanceChangesBucket> buckets) {
        final var executorService = Executors.newFixedThreadPool(NUMBER_OF_BUCKETS);
        final var accounts = new ArrayList<Account>();

        try {
            final var futures = executorService.invokeAll(createCallables(buckets));
            for (int i = 0; i < futures.size(); i++) {
                accounts.addAll(List.of(futures.get(i).get()));
            }
        } catch (final InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }

        return accounts.toArray(EMPTY_ACCOUNTS);
    }

    private static List<Callable<Account[]>> createCallables(final List<AccountBalanceChangesBucket> buckets) {
        final var callables = new ArrayList<Callable<Account[]>>(NUMBER_OF_BUCKETS);
        for (int i = 0; i < NUMBER_OF_BUCKETS; i++) {
            callables.add(buckets.get(i).toAccountsCallable());
        }
        return callables;
    }
}
