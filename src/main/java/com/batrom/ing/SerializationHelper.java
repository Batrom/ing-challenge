package com.batrom.ing;

public class SerializationHelper {

    public static final char START_ARRAY = '[';
    public static final char END_ARRAY = ']';
    public static final char START_OBJECT = '{';
    public static final char END_OBJECT = '}';
    public static final char QUOTE = '"';
    public static final char DOT = '.';
    public static final char UPPER_P = 'P';
    public static final char UPPER_S = 'S';
    public static final char UPPER_F = 'F';
    public static final char UPPER_T = 'T';
    public static final char UPPER_I = 'I';
    public static final char LOWER_C = 'c';
    public static final char LOWER_N = 'n';
    public static final char LOWER_P = 'p';
    public static final char LOWER_R = 'r';
    public static final char LOWER_G = 'g';
    public static final char LOWER_Q = 'q';
    public static final char LOWER_A = 'a';
    public static final char LOWER_D = 'd';

    public static final long[] POWERS_OF_TEN_AS_LONGS = {
            1L,
            10L,
            100L,
            1_000L,
            10_000L,
            100_000L,
            1_000_000L,
            10_000_000L,
            100_000_000L,
            1_000_000_000L,
            10_000_000_000L,
            100_000_000_000L,
            1_000_000_000_000L,
            10_000_000_000_000L,
            100_000_000_000_000L,
            1_000_000_000_000_000L};

    public static final int[] POWERS_OF_TEN_AS_INTS = {
            1,
            10,
            100,
            1_000,
            10_000,
            100_000,
            1_000_000,
            10_000_000,
            100_000_000,
            1_000_000_000};

    public static int skipUntilEndOfNumberReached(final char[] chars, int jsonPointer) {
        while (isDigit(chars[jsonPointer])) {
            ++jsonPointer;
        }
        return --jsonPointer;
    }

    public static int skipUntilStartOfNumberReached(final char[] chars, int jsonPointer) {
        while (!isDigit(chars[jsonPointer])) {
            ++jsonPointer;
        }
        return jsonPointer;
    }

    public static boolean isNotStartOfTextValue(final char chars) {
        return chars != QUOTE;
    }

    public static int skipUntilStartOfTextValueReached(final char[] chars, int jsonPointer) {
        while (isNotStartOfTextValue(chars[jsonPointer])) {
            ++jsonPointer;
        }
        return ++jsonPointer;
    }

    public static boolean isDigit(final char character) {
        return switch (character) {
            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> true;
            default -> false;
        };
    }

    public static boolean isDigitGreaterOrEqualToFive(final char character) {
        return switch (character) {
            case '5', '6', '7', '8', '9' -> true;
            default -> false;
        };
    }

    public static int startingIndexForObjectRoot(final char[] chars) {
        int i = 0;
        while (chars[i] != START_OBJECT) {
            ++i;
        }
        return i;
    }

    public static int startingIndexForArrayRoot(final char[] chars) {
        int i = 0;
        while (chars[i] != START_ARRAY) {
            ++i;
        }
        return i;
    }

    public static int endingIndexForObjectRoot(final char[] chars) {
        int i = chars.length - 1;
        while (chars[i] != END_OBJECT) {
            --i;
        }
        return i;
    }

    public static int endingIndexForArrayRoot(final char[] chars) {
        int i = chars.length - 1;
        while (chars[i] != END_ARRAY) {
            --i;
        }
        return i;
    }

    public static String divideByHundredAndRoundToTwoDecimalPlaces(final long value) {
        if (value >= 0) {
            if (value < 10) {
                return "0.0" + value;
            } else if (value < 100) {
                return "0." + value;
            }
        } else {
            if (value > -10) {
                return "-0.0" + (-value);
            } else if (value > -100) {
                return "-0." + (-value);
            }
        }

        final var valueAsStringBuilder = new StringBuilder().append(value);
        return valueAsStringBuilder.insert(valueAsStringBuilder.length() - 2, ".").toString();
    }
}
