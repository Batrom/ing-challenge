package com.batrom.ing.atmservice;

import java.io.Serializable;

public record ATM(int region, int atmId) implements Serializable {
}
