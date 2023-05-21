package com.batrom.ing.atmservice;

import java.io.Serializable;

public record ATM(int region, int atmId) implements Serializable {

    void appendToJson(final StringBuilder builder) {
        builder.append("{")
                .append("\"region\":").append(region).append(",")
                .append("\"atmId\":").append(atmId)
                .append("}");
    }
}
