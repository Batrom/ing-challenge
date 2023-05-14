package com.batrom.ing.onlinegame;

import java.util.*;

class PlayersToGroupsAssigner {

    private static final Comparator<Clan> CLAN_COMPARATOR = comparatorByPointsDescendingAndNumberOfPlayersAscending();
    private static final Clan EMPTY_CLAN = new Clan(0, 0);
    private static final Deque<Clan> EMPTY_LIST = new LinkedList<>();

    static Group[] assign(final OnlineGameInput input) {
        final var clans = input.clans();
        final var maxGroupSize = input.groupCount();
        if (clans.length == 0) return new Group[0];

        sortClans(clans);

        final var map = new HashMap<Integer, Deque<Clan>>();
        for (final Clan clan : clans) {
            final var numberOfPlayers = clan.getNumberOfPlayers();
            final var clansWithGivenNumberOfPlayers = map.get(numberOfPlayers);
            if (clansWithGivenNumberOfPlayers != null) {
                clansWithGivenNumberOfPlayers.add(clan);
            } else {
                final var value = new LinkedList<Clan>();
                value.add(clan);
                map.put(numberOfPlayers, value);
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
            Clan clanWithMaxPoints = EMPTY_CLAN;
            Deque<Clan> clanWithMaxPointsContainer = EMPTY_LIST;
            for (int sizeThatWillFitIntoGroup = group.getSizeLeft(); sizeThatWillFitIntoGroup > 0; sizeThatWillFitIntoGroup--) {
                final var clansThatWillFit = map.get(sizeThatWillFitIntoGroup);
                if (clansThatWillFit != null && !clansThatWillFit.isEmpty()) {
                    final var clan = clansThatWillFit.peek();
                    if (clan.getPoints() >= clanWithMaxPoints.getPoints()) {
                        clanWithMaxPoints = clan;
                        clanWithMaxPointsContainer = clansThatWillFit;
                    }
                }
            }
            if (clanWithMaxPoints != EMPTY_CLAN) {
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
