package com.batrom.ing.onlinegame;

import com.fasterxml.jackson.databind.JsonNode;

class InputJsonParser {

    static OnlineGameInput parse(final JsonNode node) {
        final var groupCount = node.get("groupCount").intValue();
        final var clansNode = node.get("clans");

        final var clans = new Clan[clansNode.size()];
        int index = 0;
        for (final JsonNode clan : clansNode) {
            final var numberOfPlayers = clan.get("numberOfPlayers").intValue();
            final var points = clan.get("points").intValue();
            clans[index] = new Clan(numberOfPlayers, points);

            index++;
        }

        return new OnlineGameInput(groupCount, clans);
    }
}
