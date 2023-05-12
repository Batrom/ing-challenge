package com.batrom.ing.atmservice;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class OrderSerializer extends JsonSerializer<Order> {

    @Override
    public void serialize(final Order order, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        final var atms = order.getATMs();
        for (int i = 0; i < atms.length; i++) {
            final var atm = atms[i];
            if (atm == null) continue;

            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("region", atm.region());
            jsonGenerator.writeNumberField("atmId", atm.atmId());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
    }
}