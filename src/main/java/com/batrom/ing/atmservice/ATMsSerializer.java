package com.batrom.ing.atmservice;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ATMsSerializer extends JsonSerializer<ATM[]> {

    @Override
    public void serialize(final ATM[] atms, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        for (final ATM atm : atms) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("region", atm.region());
            jsonGenerator.writeNumberField("atmId", atm.atmId());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
    }
}