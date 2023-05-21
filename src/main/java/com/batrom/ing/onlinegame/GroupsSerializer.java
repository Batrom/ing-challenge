package com.batrom.ing.onlinegame;

public class GroupsSerializer {

    public static String serialize(final Group[] groups) {
        if (groups.length == 0) return "[]";

        final var builder = new StringBuilder("[");
        for (final Group group : groups) {
            group.appendToJson(builder);
            builder.append(",");
        }
        builder.setLength(builder.length() - 1);
        return builder.append("]").toString();
    }
}