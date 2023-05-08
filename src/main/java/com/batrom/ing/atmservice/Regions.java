package com.batrom.ing.atmservice;

import java.util.Arrays;

class Regions {

    private RequestTypesWithAtmIds[] data = new RequestTypesWithAtmIds[10];

    void add(final int region, final RequestType requestType, final int atmId) {
        growIfNecessary(region);
        final var regionData = this.data[region];
        if (regionData != null) {
            regionData.add(requestType, atmId);
        } else {
            final var newRegionData = new RequestTypesWithAtmIds();
            newRegionData.add(requestType, atmId);
            this.data[region] = newRegionData;
        }
    }

    Order toOrder() {
        final var order = new Order();
        for (int region = 1; region < this.data.length; region++) {
            final var regionData = this.data[region];
            if (regionData == null) continue;

            addToOrder(order, region, regionData.data[3],
                    addToOrder(order, region, regionData.data[2],
                            addToOrder(order, region, regionData.data[1],
                                    addToOrder(order, region, regionData.data[0], 0))));
        }
        return order;
    }

    private static int addToOrder(final Order order, final int region, final AtmIds atmIds, int previousAtmId) {
        if (atmIds == null) return previousAtmId;

        final var atmIdsData = atmIds.data;

        for (int i = 0; i < atmIdsData.length; i++) {
            final var atmId = atmIdsData[i];
            if (atmId == 0) return previousAtmId;

            if (previousAtmId != atmId) {
                order.add(region, previousAtmId = atmId);
            }
        }
        return previousAtmId;
    }

    private void growIfNecessary(final int region) {
        if (data.length <= region) {
            this.data = Arrays.copyOf(this.data, region >> 1);
        }
    }

    private static class RequestTypesWithAtmIds {
        private final AtmIds[] data = new AtmIds[4];

        private void add(final RequestType requestType, final int atmId) {
            atmIdsForRequestType(requestType).add(atmId);
        }

        private AtmIds atmIdsForRequestType(final RequestType requestType) {
            final var order = requestType.getOrder();
            final var atmIds = this.data[order];
            return atmIds == null ? this.data[order] = new AtmIds() : atmIds;
        }
    }

    private static class AtmIds {
        private int realSize = 0;
        private int[] data = new int[10];

        private void add(final int atmId) {
            growIfNecessary();
            this.data[this.realSize] = atmId;
            this.realSize++;
        }

        private void growIfNecessary() {
            if (this.data.length == this.realSize) {
                this.data = Arrays.copyOf(this.data, this.realSize >> 1);
            }
        }
    }
}