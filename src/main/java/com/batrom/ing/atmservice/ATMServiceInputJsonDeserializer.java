package com.batrom.ing.atmservice;

import static com.batrom.ing.SerializationHelper.*;
import static org.eclipse.jetty.util.StringUtil.isEmpty;

public class ATMServiceInputJsonDeserializer {

    private static final ATMServiceInput EMPTY_INPUT = new ATMServiceInput(new Regions());

    public static ATMServiceInput deserialize(final String json) {
        if (isEmpty(json)) return EMPTY_INPUT;

        final var regions = new Regions();
        final var chars = json.toCharArray();

        // find start and end
        final var startingIndex = startingIndexForArrayRoot(chars);
        final var endingIndex = endingIndexForArrayRoot(chars);
        for (int jsonPointer = startingIndex + 1; jsonPointer < endingIndex; jsonPointer++) {
            var character = chars[jsonPointer];

            // skip until next object opening found - so skip everything that is not '{'
            if (character != START_OBJECT) {
                continue;
            }

            int atmId = 0;
            int region = 0;
            RequestType requestType = null;

            // iterate until closing object found - so until '}' reached
            while (character != END_OBJECT) {

                if (character == LOWER_A) {
                    // skip 'atmId":' sentence
                    jsonPointer += 7;

                    // skip until value started - so skip until digit reached
                    jsonPointer = skipUntilStartOfNumberReached(chars, jsonPointer);

                    final int startNumberIndex = jsonPointer;

                    // skip until end of value - so skip until non-digit reached
                    jsonPointer = skipUntilEndOfNumberReached(chars, jsonPointer);

                    final int endNumberIndex = jsonPointer;
                    final var numberLength = endNumberIndex - startNumberIndex + 1;

                    // create number that is multiplied by 100 - to get rid of decimal places
                    for (int digitPositon = 0, numberIndex = endNumberIndex; digitPositon < numberLength; digitPositon++, numberIndex--) {
                        atmId += POWERS_OF_TEN[digitPositon] * Character.getNumericValue(chars[numberIndex]);
                    }
                } else if (character == LOWER_R) {
                    final var thirdLetter = chars[jsonPointer + 2];
                    if (thirdLetter == LOWER_Q) {
                        // skip 'requestType":' sentence
                        jsonPointer += 13;

                        // skip until value started - so skip everything that is not equal to '"'
                        jsonPointer = skipUntilStartOfTextValueReached(chars, jsonPointer);

                        final var firstLetterOfRequestType = chars[jsonPointer];
                        if (firstLetterOfRequestType == UPPER_P) {
                            // skip 'PRIORITY"' sentence
                            jsonPointer += 9;
                            requestType = RequestType.PRIORITY;
                        } else if (firstLetterOfRequestType == UPPER_F) {
                            // skip 'FAILURE_RESTART"' sentence
                            jsonPointer += 16;
                            requestType = RequestType.FAILURE_RESTART;
                        } else if (firstLetterOfRequestType == UPPER_S) {
                            final var secondLetterOfRequestType = chars[++jsonPointer];
                            if (secondLetterOfRequestType == UPPER_T) {
                                // skip 'TANDARD"' sentence
                                jsonPointer += 8;
                                requestType = RequestType.STANDARD;
                            } else if (secondLetterOfRequestType == UPPER_I) {
                                // skip 'IGNAL_LOW"' sentence
                                jsonPointer += 10;
                                requestType = RequestType.SIGNAL_LOW;
                            }
                        }

                    } else if (thirdLetter == LOWER_G) {
                        // skip 'region":' sentence
                        jsonPointer += 8;

                        // skip until value started - so skip until digit reached
                        jsonPointer = skipUntilStartOfNumberReached(chars, jsonPointer);

                        final int startNumberIndex = jsonPointer;

                        // skip until end of value - so skip until non-digit reached
                        jsonPointer = skipUntilEndOfNumberReached(chars, jsonPointer);

                        final int endNumberIndex = jsonPointer;
                        final var numberLength = endNumberIndex - startNumberIndex + 1;

                        // create number that is multiplied by 100 - to get rid of decimal places
                        for (int digitPositon = 0, numberIndex = endNumberIndex; digitPositon < numberLength; digitPositon++, numberIndex--) {
                            region += POWERS_OF_TEN[digitPositon] * Character.getNumericValue(chars[numberIndex]);
                        }
                    }
                }

                character = chars[++jsonPointer];
            }

            regions.add(region, requestType, atmId);
        }
        return new ATMServiceInput(regions);
    }
}
