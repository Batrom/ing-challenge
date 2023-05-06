package com.batrom.ing.atmservice;

enum RequestType {
    STANDARD(0),
    PRIORITY(1),
    SIGNAL_LOW(2),
    FAILURE_RESTART(3);
    
    private final int order;

    RequestType(final int order) {
        this.order = order;
    }

    int getOrder() {
        return order;
    }
}
