package com.batrom.ing.transactions;

public class AccountsSerializer {

    public static String serialize(final Account[] accounts) {
        if (accounts.length == 0) return "[]";

        final var builder = new StringBuilder("[");
        for (final Account account : accounts) {
            builder.append(account.toJson()).append(",");
        }
        builder.setLength(builder.length() - 1);
        return builder.append("]").toString();
    }
}