package com.batrom.ing.atmservice;

import com.fasterxml.jackson.databind.JsonNode;

class InputParser {

    public static Input parse(final JsonNode tasks) {
        final var regions = new Regions();

        for (final JsonNode task : tasks) {
            final var region = task.get("region").intValue();
            final var requestType = RequestType.valueOf(task.get("requestType").textValue());
            final var atmId = task.get("atmId").intValue();
            regions.add(region, requestType, atmId);
        }

        return new Input(regions);
    }
}
