package com.batrom.ing.onlinegame;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class OnlineGameInputDeserializer extends JsonDeserializer<OnlineGameInput> {

    @Override
    public OnlineGameInput deserialize(final JsonParser jsonParser, final DeserializationContext context) throws IOException {
        final var node = readTreeToArrayNode(jsonParser);
        return InputJsonParser.parse(node);
    }

    private static JsonNode readTreeToArrayNode(final JsonParser jsonParser) throws IOException {
        return jsonParser.getCodec().readTree(jsonParser);
    }
}