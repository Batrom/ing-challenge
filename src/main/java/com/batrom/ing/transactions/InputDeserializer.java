package com.batrom.ing.transactions;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class InputDeserializer extends JsonDeserializer<Input> {

    @Override
    public Input deserialize(final JsonParser jsonParser, final DeserializationContext context) throws IOException {
        final var node = readTreeToArrayNode(jsonParser);
        return new Input(InputJsonParser.parse(node));
    }

    private static JsonNode readTreeToArrayNode(final JsonParser jsonParser) throws IOException {
        return jsonParser.getCodec().readTree(jsonParser);
    }
}
