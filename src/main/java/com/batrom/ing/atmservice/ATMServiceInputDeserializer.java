package com.batrom.ing.atmservice;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class ATMServiceInputDeserializer extends JsonDeserializer<ATMServiceInput> {

    @Override
    public ATMServiceInput deserialize(final JsonParser jsonParser, final DeserializationContext context) throws IOException {
        final var tasks = readTreeToArrayNode(jsonParser);
        return InputJsonParser.parse(tasks);
    }

    private static JsonNode readTreeToArrayNode(final JsonParser jsonParser) throws IOException {
        return jsonParser.getCodec().readTree(jsonParser);
    }
}
