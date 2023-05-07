package com.batrom.ing.atmservice;

import com.fasterxml.jackson.databind.JsonNode;

class InputParser {

    public static Input parse(final JsonNode tasks) {
        final var atmIdsGroupedByRegions = new Input.ATMIdsGroupedByRegions();

        final var iterator = tasks.iterator();
        while (iterator.hasNext()) {
            final var task = iterator.next();

            final var region = task.get("region").intValue();
            final var requestType = RequestType.valueOf(task.get("requestType").textValue());
            final var atmId = task.get("atmId").intValue();

            final var atmsForRegion = atmsForRegion(atmIdsGroupedByRegions, region);
            addAtmId(atmsForRegion, requestType, atmId);
        }

        return new Input(atmIdsGroupedByRegions);
    }

    private static void addAtmId(final Input.ATMIdForRequestTypeList[] atmsForRegion, final RequestType requestType, final int atmId) {
        final var order = requestType.getOrder();
        final var atmIdForRequestType = atmsForRegion[order];
        if (atmIdForRequestType != null) {
            atmIdForRequestType.add(atmId);
        } else {
            final var atmIds = new Input.ATMIdForRequestTypeList();
            atmIds.add(atmId);
            atmsForRegion[order] = atmIds;
        }
    }

    private static Input.ATMIdForRequestTypeList[] atmsForRegion(final Input.ATMIdsGroupedByRegions atmIdsGroupedByRegions, final int region) {
        atmIdsGroupedByRegions.ensureCapacity(region);
        final var atmsForRegion = atmIdsGroupedByRegions.get(region);

        if (atmsForRegion != null) {
            return atmsForRegion;
        } else {
            final var atmIds = new Input.ATMIdForRequestTypeList[4];
            atmIdsGroupedByRegions.set(region, atmIds);
            return atmIds;
        }
    }
}
