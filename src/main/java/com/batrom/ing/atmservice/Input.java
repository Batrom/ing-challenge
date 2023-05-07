package com.batrom.ing.atmservice;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonDeserialize(using = InputDeserializer.class)
record Input(ATMIdsGroupedByRegions atmIdsGroupedByRegionsInCorrectOrder) implements Serializable {

    List<ATM> toResponse() {
        return InputToResponseConverter.convert(atmIdsGroupedByRegionsInCorrectOrder);
    }

    static class ATMIdForRequestTypeList extends ArrayList<Integer> {
    }

    static class ATMIdsGroupedByRegions extends ArrayList<ATMIdForRequestTypeList[]> {
    }
}
