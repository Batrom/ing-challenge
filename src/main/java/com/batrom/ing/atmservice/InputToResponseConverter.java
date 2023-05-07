package com.batrom.ing.atmservice;

import java.util.ArrayList;
import java.util.List;

class InputToResponseConverter {

    static List<ATM> convert(final Input.ATMIdsGroupedByRegions atmIdsGroupedByRegionsInCorrectOrder) {
        final var result = new ArrayList<ATM>();
        for (int region = 0; region < atmIdsGroupedByRegionsInCorrectOrder.size(); region++) {
            final var orderedTask = atmIdsGroupedByRegionsInCorrectOrder.get(region);
            if (orderedTask == null) continue;

            addToResultForRequestType(result, region, orderedTask[0]);
            addToResultForRequestType(result, region, orderedTask[1]);
            addToResultForRequestType(result, region, orderedTask[2]);
            addToResultForRequestType(result, region, orderedTask[3]);
        }
        return result;
    }

    private static void addToResultForRequestType(final ArrayList<ATM> result, final int region, final Input.ATMIdForRequestTypeList atmIds) {
        if (atmIds != null) {
            for (int i = 0; i < atmIds.size(); i++) {
                result.add(new ATM(region, atmIds.get(i)));
            }
        }
    }
}
