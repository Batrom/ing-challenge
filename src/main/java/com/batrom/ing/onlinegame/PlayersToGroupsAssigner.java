package com.batrom.ing.onlinegame;

import java.util.Arrays;
import java.util.Comparator;

class PlayersToGroupsAssigner {

    private static final Comparator<Clan> CLAN_COMPARATOR = comparatorByPointsDescendingAndNumberOfPlayersAscending();

    static Group[] assign(final OnlineGameInput input) {
        final var clanSizeCounters = input.clanSizeCounters();
        final var clans = input.clans();
        final var maxGroupSize = input.groupCount();

        if (clans.length == 0) return new Group[0];

        int minClanSize = minClanSize(clanSizeCounters, 1);

        sortClans(clans);
        final var groups = initializeGroups(clans[0], maxGroupSize);
        int indexOfFirstNonFullGroup = 0;

        clansLoop:
        for (int clanIndex = 1; clanIndex < clans.length; clanIndex++) {
            final var clan = clans[clanIndex];

            boolean allGroupsAreFullSoFar = true;
            for (int groupIndex = indexOfFirstNonFullGroup; groupIndex < groups.size(); groupIndex++) {
                final var group = groups.get(groupIndex);

                allGroupsAreFullSoFar = allGroupsAreFullSoFar && group.isFull(minClanSize);
                if (allGroupsAreFullSoFar) {
                    indexOfFirstNonFullGroup = groupIndex + 1;
                }

                final var numberOfPlayers = clan.numberOfPlayers();
                if (group.willFit(numberOfPlayers)) {
                    group.addClan(clan);

                    clanSizeCounters[numberOfPlayers] -= 1;
                    minClanSize = minClanSize(clanSizeCounters, minClanSize);

                    allGroupsAreFullSoFar = allGroupsAreFullSoFar && group.isFull(minClanSize);
                    if (allGroupsAreFullSoFar) {
                        indexOfFirstNonFullGroup = groupIndex + 1;
                    }

                    continue clansLoop;
                }
            }

            // if no group found with enough space then create new one
            final var group = Group.of(clan, maxGroupSize);
            groups.add(group);

            clanSizeCounters[clan.numberOfPlayers()] -= 1;
            minClanSize = minClanSize(clanSizeCounters, minClanSize);

            allGroupsAreFullSoFar = allGroupsAreFullSoFar && group.isFull(minClanSize);
            if (allGroupsAreFullSoFar) {
                indexOfFirstNonFullGroup = groups.size();
            }
        }

        return groups.getData();
    }

    private static int minClanSize(final int[] clanSizeCounters, final int currentMinClanSize) {
        for (int clanSize = currentMinClanSize; clanSize < clanSizeCounters.length; clanSize++) {
            if (clanSizeCounters[clanSize] > 0) {
                return clanSize;
            }
        }
        return currentMinClanSize;
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
