package com.batrom.ing.atmservice;

public enum RequestType {
    FAILURE_RESTART(0),
    PRIORITY(1),
    SIGNAL_LOW(2),
    STANDARD(3);

    private final int order;

    RequestType(final int order) {
        this.order = order;
    }

    int getOrder() {
        return order;
    }
}
