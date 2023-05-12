package com.batrom.ing.onlinegame;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class GroupsSerializer extends JsonSerializer<Group[]> {

    @Override
    public void serialize(final Group[] groups, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        for (final Group group : groups) {
            jsonGenerator.writeStartArray();
            for (final Clan clan : group.getClans()) {
                if (clan == null) continue;
                jsonGenerator.writeStartObject();
                jsonGenerator.writeNumberField("numberOfPlayers", clan.numberOfPlayers());
                jsonGenerator.writeNumberField("points", clan.points());
                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();
        }
        jsonGenerator.writeEndArray();
    }
}