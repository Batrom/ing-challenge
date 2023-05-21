package com.batrom.ing.onlinegame;

import static com.batrom.ing.SerializationHelper.*;
import static org.eclipse.jetty.util.StringUtil.isEmpty;

public class OnlineGameInputJsonDeserializer {

    private static final OnlineGameInput EMPTY_INPUT = new OnlineGameInput(0, new Clan[0]);

    public static OnlineGameInput deserialize(final String json) {
        if (isEmpty(json)) return EMPTY_INPUT;

        int groupCount = 0;

        final var clans = new Clans();

        final var chars = json.toCharArray();
        final var startingIndex = startingIndexForObjectRoot(chars);
        final var endingIndex = endingIndexForObjectRoot(chars);

        for (int jsonPointer = startingIndex + 1; jsonPointer < endingIndex; jsonPointer++) {
            final var character = chars[jsonPointer];

            if (character == LOWER_G) {
                // skip 'groupCount":' sentence
                jsonPointer += 12;

                // skip until value started - so skip until digit reached
                jsonPointer = skipUntilStartOfNumberReached(chars, jsonPointer);

                final int startNumberIndex = jsonPointer;

                // skip until end of value - so skip until non-digit reached
                jsonPointer = skipUntilEndOfNumberReached(chars, jsonPointer);

                final int endNumberIndex = jsonPointer;
                final var numberLength = endNumberIndex - startNumberIndex + 1;

                for (int digitPositon = 0, numberIndex = endNumberIndex; digitPositon < numberLength; digitPositon++, numberIndex--) {
                    groupCount += POWERS_OF_TEN_AS_INTS[digitPositon] * Character.getNumericValue(chars[numberIndex]);
                }
            } else if (character == LOWER_C) {
                // skip 'clans":' sentence
                jsonPointer += 7;

                var characterInsideClans = chars[jsonPointer];

                // iterate until closing array found - so until ']' reached
                while (characterInsideClans != END_ARRAY) {

                    // skip until start of an object reached
                    if (characterInsideClans != START_OBJECT) {
                        characterInsideClans = chars[++jsonPointer];
                        continue;
                    }

                    int numberOfPlayers = 0;
                    int points = 0;

                    // iterate until closing object found - so until '}' reached
                    while (characterInsideClans != END_OBJECT) {

                        if (characterInsideClans == LOWER_N) {
                            // skip 'numberOfPlayers":' sentence
                            jsonPointer += 17;

                            // skip until value started - so skip until digit reached
                            jsonPointer = skipUntilStartOfNumberReached(chars, jsonPointer);

                            final int startNumberIndex = jsonPointer;

                            // skip until end of value - so skip until non-digit reached
                            jsonPointer = skipUntilEndOfNumberReached(chars, jsonPointer);

                            final int endNumberIndex = jsonPointer;
                            final var numberLength = endNumberIndex - startNumberIndex + 1;

                            for (int digitPositon = 0, numberIndex = endNumberIndex; digitPositon < numberLength; digitPositon++, numberIndex--) {
                                numberOfPlayers += POWERS_OF_TEN_AS_INTS[digitPositon] * Character.getNumericValue(chars[numberIndex]);
                            }
                        } else if (characterInsideClans == LOWER_P) {
                            // skip 'points":' sentence
                            jsonPointer += 8;

                            // skip until value started - so skip until digit reached
                            jsonPointer = skipUntilStartOfNumberReached(chars, jsonPointer);

                            final int startNumberIndex = jsonPointer;

                            // skip until end of value - so skip until non-digit reached
                            jsonPointer = skipUntilEndOfNumberReached(chars, jsonPointer);

                            final int endNumberIndex = jsonPointer;
                            final var numberLength = endNumberIndex - startNumberIndex + 1;

                            for (int digitPositon = 0, numberIndex = endNumberIndex; digitPositon < numberLength; digitPositon++, numberIndex--) {
                                points += POWERS_OF_TEN_AS_INTS[digitPositon] * Character.getNumericValue(chars[numberIndex]);
                            }
                        }
                        characterInsideClans = chars[++jsonPointer];
                    }
                    clans.add(new Clan(numberOfPlayers, points));
                    characterInsideClans = chars[++jsonPointer];
                }
            }
        }
        return new OnlineGameInput(groupCount, clans.getData());
    }
}
