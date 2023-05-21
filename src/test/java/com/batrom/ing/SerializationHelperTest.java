package com.batrom.ing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SerializationHelperTest {

    @Test
    void divideByHundredAndRoundToTwoDecimalPlaces_forValueOne() {
        final var expected = "0.01";
        final var actual = SerializationHelper.divideByHundredAndRoundToTwoDecimalPlaces(1);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void divideByHundredAndRoundToTwoDecimalPlaces_forValueTen() {
        final var expected = "0.10";
        final var actual = SerializationHelper.divideByHundredAndRoundToTwoDecimalPlaces(10);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void divideByHundredAndRoundToTwoDecimalPlaces_forValueZero() {
        final var expected = "0.00";
        final var actual = SerializationHelper.divideByHundredAndRoundToTwoDecimalPlaces(0);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void divideByHundredAndRoundToTwoDecimalPlaces_forValueOneHundred() {
        final var expected = "1.00";
        final var actual = SerializationHelper.divideByHundredAndRoundToTwoDecimalPlaces(100);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void divideByHundredAndRoundToTwoDecimalPlaces_forValueMinusOne() {
        final var expected = "-0.01";
        final var actual = SerializationHelper.divideByHundredAndRoundToTwoDecimalPlaces(-1);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void divideByHundredAndRoundToTwoDecimalPlaces_forValueMinusTen() {
        final var expected = "-0.10";
        final var actual = SerializationHelper.divideByHundredAndRoundToTwoDecimalPlaces(-10);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void divideByHundredAndRoundToTwoDecimalPlaces_forValueMinusOneHundred() {
        final var expected = "-1.00";
        final var actual = SerializationHelper.divideByHundredAndRoundToTwoDecimalPlaces(-100);

        Assertions.assertEquals(expected, actual);
    }
}