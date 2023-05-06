package com.batrom.ing.transactions;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;

class InputDeserializer extends JsonDeserializer<Input> {

    @Override
    public Input deserialize(final JsonParser jsonParser, final DeserializationContext context) throws IOException {
        final var node = readTreeToArrayNode(jsonParser);

        if (node.size() < 10_000) {
            return SmallInput.fromJson(node);
        } else {
            return LargeInput.fromJson(node);
        }
    }

    private static ArrayNode readTreeToArrayNode(final JsonParser jsonParser) throws IOException {
        return jsonParser.getCodec().readTree(jsonParser);
    }
}
