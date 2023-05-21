package com.batrom.ing.atmservice;

public class ATMsSerializer {

    public static String serialize(final ATM[] atms) {
        if (atms.length == 0) return "[]";

        final var builder = new StringBuilder("[");
        for (final ATM atm : atms) {
            atm.appendToJson(builder);
            builder.append(",");
        }
        builder.setLength(builder.length() - 1);
        return builder.append("]").toString();
    }
}