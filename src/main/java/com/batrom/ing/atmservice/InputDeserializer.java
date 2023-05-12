package com.batrom.ing.atmservice;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class InputDeserializer extends JsonDeserializer<Input> {

    @Override
    public Input deserialize(final JsonParser jsonParser, final DeserializationContext context) throws IOException {
        final var tasks = readTreeToArrayNode(jsonParser);
        return InputParser.parse(tasks);
    }

    private static JsonNode readTreeToArrayNode(final JsonParser jsonParser) throws IOException {
        return jsonParser.getCodec().readTree(jsonParser);
    }
}
