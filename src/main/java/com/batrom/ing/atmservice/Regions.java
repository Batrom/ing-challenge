package com.batrom.ing.atmservice;

import java.util.Arrays;

class Regions {

    private static final int MAX_REGION = 10_000;

    private RequestTypesWithATMIds[] data = new RequestTypesWithATMIds[10];

    void add(final int region, final RequestType requestType, final int atmId) {
        growIfNecessary(region);
        final var regionData = this.data[region];
        if (regionData != null) {
            regionData.add(requestType, atmId);
        } else {
            final var newRegionData = new RequestTypesWithATMIds();
            newRegionData.add(requestType, atmId);
            this.data[region] = newRegionData;
        }
    }

    Order toOrder() {
        final var order = new Order();
        for (int region = 1; region < this.data.length; region++) {
            final var regionData = this.data[region];
            if (regionData == null) continue;

            final var atmIdPresenceArray = new boolean[regionData.maxATMId() + 1];

            addToOrder(order, region, regionData.data[0], atmIdPresenceArray);
            addToOrder(order, region, regionData.data[1], atmIdPresenceArray);
            addToOrder(order, region, regionData.data[2], atmIdPresenceArray);
            addToOrder(order, region, regionData.data[3], atmIdPresenceArray);

        }
        return order;
    }

    private static void addToOrder(final Order order, final int region, final ATMIds atmIds, final boolean[] atmIdPresenceArray) {
        if (atmIds == null) return;

        final var atmIdsData = atmIds.data;

        for (int i = 0; i < atmIdsData.length; i++) {
            final var atmId = atmIdsData[i];
            if (atmId == 0) return;

            if (!atmIdPresenceArray[atmId]) {
                atmIdPresenceArray[atmId] = true;
                order.add(region, atmId);
            }
        }
    }

    private void growIfNecessary(final int region) {
        if (data.length <= region) {
            this.data = Arrays.copyOf(this.data, Math.min(MAX_REGION, region >> 1));
        }
    }

    private static class RequestTypesWithATMIds {
        private final ATMIds[] data = new ATMIds[4];

        private void add(final RequestType requestType, final int atmId) {
            atmIdsForRequestType(requestType).add(atmId);
        }

        private int maxATMId() {
            return Math.max(Math.max(Math.max(maxATMIdForRequestType(0), maxATMIdForRequestType(1)), maxATMIdForRequestType(2)), maxATMIdForRequestType(3));
        }

        private int maxATMIdForRequestType(final int requestTypeOrder) {
            final var atmIds = data[requestTypeOrder];
            return atmIds != null ? atmIds.maxValue : 0;
        }

        private ATMIds atmIdsForRequestType(final RequestType requestType) {
            final var order = requestType.getOrder();
            final var atmIds = this.data[order];
            return atmIds == null ? this.data[order] = new ATMIds() : atmIds;
        }
    }

    private static class ATMIds {
        private static final int MAX_ATM_ID = 10_000;
        private int realSize = 0;
        private int maxValue = 0;
        private int[] data = new int[10];

        private void add(final int atmId) {
            this.maxValue =  Math.max(maxValue, atmId);
            growIfNecessary();
            this.data[this.realSize] = atmId;
            this.realSize++;
        }

        private void growIfNecessary() {
            if (this.data.length == this.realSize) {
                this.data = Arrays.copyOf(this.data, Math.min(MAX_ATM_ID, this.realSize >> 1));
            }
        }
    }
}