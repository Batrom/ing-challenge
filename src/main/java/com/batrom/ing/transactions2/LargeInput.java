package com.batrom.ing.transactions2;

import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public record LargeInput(AccountBalanceChangesBucket[] buckets) implements Input {

    static final int NUMBER_OF_BUCKETS = 11;

    static Input fromJson(final ArrayNode node) {
        return new LargeInput(LargeInputJsonParser.read(node));
    }

    public Account[] toResponse() {
        return toAccountsAsynchronously();
    }

    private Account[] toAccountsAsynchronously() {
        final var executorService = Executors.newFixedThreadPool(NUMBER_OF_BUCKETS);
        final var accounts = new ArrayList<Account>();

        try {
            final var futures = executorService.invokeAll(getCallables());
            for (int i = 0; i < futures.size(); i++) {
                accounts.addAll(List.of(futures.get(i).get()));
            }
        } catch (final InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }

        return accounts.toArray(new Account[0]);
    }

    private List<Callable<Account[]>> getCallables() {
        final var callables = new ArrayList<Callable<Account[]>>(NUMBER_OF_BUCKETS);
        for (int i = 0; i < NUMBER_OF_BUCKETS; i++) {
            callables.add(buckets[i].toAccountsCallable());
        }
        return callables;
    }
}
