package com.batrom.ing.onlinegame;

import java.util.Arrays;
import java.util.Comparator;

class PlayersToGroupsAssigner {

    private static final Comparator<Clan> CLAN_COMPARATOR = comparatorByPointsDescendingAndNumberOfPlayersAscending();

    static Group[] assign(final Players players) {
        final var clans = players.clans();
        final var maxGroupSize = players.groupCount();
        if (clans.length == 0) return new Group[0];
        sortClans(clans);

        final var groups = initializeGroups(clans[0], maxGroupSize);
        int firstNonFullGroupPosition = 0;
        boolean allGroupsAreFullSoFar = groups.get(0).isFull();

        for (int clanIndex = 1; clanIndex < clans.length; clanIndex++) {
            final var clan = clans[clanIndex];
            boolean clanAddedToGroup = false;

            for (int groupPosition = firstNonFullGroupPosition; groupPosition < groups.size(); groupPosition++) {
                final var group = groups.get(groupPosition);
                if (group.willFit(clan.numberOfPlayers())) {
                    group.addClan(clan);

                    if (allGroupsAreFullSoFar) {
                        if (!group.isFull()) {
                            allGroupsAreFullSoFar = false;
                            firstNonFullGroupPosition = groupPosition;
                        } else {
                            firstNonFullGroupPosition = groupPosition + 1;
                        }
                    }
                    clanAddedToGroup = true;
                    break;
                }
            }

            if (!clanAddedToGroup) {
                final var group = Group.of(clan, maxGroupSize);
                groups.add(group);

                if (allGroupsAreFullSoFar) {
                    if (!group.isFull()) {
                        allGroupsAreFullSoFar = false;
                        firstNonFullGroupPosition = groups.size() - 1;
                    } else {
                        firstNonFullGroupPosition = groups.size();
                    }
                }
            }
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
