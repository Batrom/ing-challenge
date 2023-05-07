package com.batrom.ing.onlinegame;

import java.util.*;

class PlayersToGroupsAssigner {

    static List<Group> assign(final Players players) {
        final var clans = players.clans();
        final var maxGroupSize = players.groupCount();
        if (clans.length == 0) return Collections.emptyList();
        sortClans(clans);

        final var groups = initializeGroups(clans);
        int firstNonFullGroupPosition = 0;
        boolean allGroupsAreFullSoFar = groups.get(0).isFull(maxGroupSize);

        for (int clanIndex = 1; clanIndex < clans.length; clanIndex++) {
            final var clan = clans[clanIndex];
            boolean clanAddedToGroup = false;

            for (int groupPosition = firstNonFullGroupPosition; groupPosition < groups.size(); groupPosition++) {
                final var group = groups.get(groupPosition);
                if (group.willFit(clan.numberOfPlayers(), maxGroupSize)) {
                    group.addClan(clan);

                    if (allGroupsAreFullSoFar) {
                        if (!group.isFull(maxGroupSize)) {
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
                final var group = Group.of(clan);
                groups.add(group);

                if (allGroupsAreFullSoFar) {
                    if (!group.isFull(maxGroupSize)) {
                        allGroupsAreFullSoFar = false;
                        firstNonFullGroupPosition = groups.size() - 1;
                    } else {
                        firstNonFullGroupPosition = groups.size();
                    }
                }
            }
        }

        return groups;
    }

    private static void sortClans(final Clan[] clans) {
        Arrays.sort(clans, compareByPointsDescendingAndNumberOfPlayersAscending());
    }

    private static List<Group> initializeGroups(final Clan[] clans) {
        final var groups = new ArrayList<Group>();
        groups.add(Group.of(clans[0]));
        return groups;
    }

    private static Comparator<Clan> compareByPointsDescendingAndNumberOfPlayersAscending() {
        return Comparator.comparingInt(Clan::points)
                .reversed()
                .thenComparingInt(Clan::numberOfPlayers);
    }
}