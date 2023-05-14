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

        final var map = new GroupsMap(maxGroupSize);
        for (int i = clans.length - 1; i >= 0 ; i--) {
            final var clan = clans[i];
            final var numberOfPlayers = clan.getNumberOfPlayers();
            final var clansWithGivenNumberOfPlayers = map.get(numberOfPlayers);
            if (clansWithGivenNumberOfPlayers != null) {
                clansWithGivenNumberOfPlayers.push(clan);
            } else {
                map.put(clan);
            }
        }

        int sortedClansIndex = 0;
        final var firstClan = clans[sortedClansIndex];
        final var groups = initializeGroups(firstClan, maxGroupSize);
        firstClan.setIsAssignedToTrue();
        map.get(firstClan.getNumberOfPlayers()).pop();
        sortedClansIndex++;

        int groupsIndex = 0;

        groupsLoop:
        while (true) {
            final var group = groups.getData()[groupsIndex];
            Clan clanWithMaxPoints = null;
            GroupsMap.ClansStack clanWithMaxPointsContainer = null;
            for (int sizeThatWillFitIntoGroup = group.getSizeLeft(); sizeThatWillFitIntoGroup > 0; sizeThatWillFitIntoGroup--) {
                final var clansThatWillFit = map.get(sizeThatWillFitIntoGroup);
                if (clansThatWillFit != null && !clansThatWillFit.isEmpty()) {
                    final var clan = clansThatWillFit.peek();
                    if (clanWithMaxPoints == null || (clan.getPoints() >= clanWithMaxPoints.getPoints())) {
                        clanWithMaxPoints = clan;
                        clanWithMaxPointsContainer = clansThatWillFit;
                    }
                }
            }
            if (clanWithMaxPoints != null) {
                clanWithMaxPointsContainer.pop();
                clanWithMaxPoints.setIsAssignedToTrue();
                group.addClan(clanWithMaxPoints);
            } else {
                for (; sortedClansIndex < clans.length; sortedClansIndex++) {
                    final var clan = clans[sortedClansIndex];
                    if (clan.isNotAssigned()) {
                        groups.add(Group.of(clan, maxGroupSize));
                        clan.setIsAssignedToTrue();
                        map.get(clan.getNumberOfPlayers()).pop();
                        groupsIndex++;
                        continue groupsLoop;
                    }
                }
                break;
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
        return Comparator.comparingInt(Clan::getPoints).reversed().thenComparingInt(Clan::getNumberOfPlayers);
    }
}
