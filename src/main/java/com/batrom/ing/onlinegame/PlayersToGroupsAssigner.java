package com.batrom.ing.onlinegame;

import java.util.Arrays;
import java.util.Comparator;

class PlayersToGroupsAssigner {

    private static final Comparator<Clan> CLAN_COMPARATOR = comparatorByPointsDescendingAndNumberOfPlayersAscending();

    static Group[] assign(final OnlineGameInput input) {
        final var clans = input.clans();
        final var maxGroupSize = input.groupCount();

        if (clans.length == 0) return new Group[0];

        sortClans(clans);
        final var groups = initializeGroups(clans[0], maxGroupSize);

        clansLoop:
        for (int clanIndex = 1; clanIndex < clans.length; clanIndex++) {
            final var clan = clans[clanIndex];
            final var numberOfPlayers = clan.numberOfPlayers();

            for (int groupIndex = 0; groupIndex < groups.size(); groupIndex++) {
                final var group = groups.get(groupIndex);

                if (group.willFit(numberOfPlayers)) {
                    group.addClan(clan);

                    continue clansLoop;
                }
            }

            // if no group was found with enough space then create new one
            groups.add(Group.of(clan, maxGroupSize));
        }

        return groups.getData();
    }

    private static void sortClans(final Clan[] clans) {
        Arrays.sort(clans, CLAN_COMPARATOR);
    }

    private static Groups initializeGroups(final Clan firstClan, final int maxGroupSize) {
        return Groups.of(Group.of(firstClan, maxGroupSize));
    }

    private static Comparator<Clan> comparatorByPointsDescendingAndNumberOfPlayersAscending() {
        return Comparator.comparingInt(Clan::points).reversed().thenComparingInt(Clan::numberOfPlayers);
    }
}
