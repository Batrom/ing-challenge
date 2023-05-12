package com.batrom.ing.transactions;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class AccountsSerializer extends JsonSerializer<Account[]> {

    @Override
    public void serialize(final Account[] accounts, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        for (final Account account : accounts) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("account", account.getAccount());
            jsonGenerator.writeNumberField("debitCount", account.getDebitCount());
            jsonGenerator.writeNumberField("creditCount", account.getCreditCount());
            jsonGenerator.writeNumberField("balance", account.getBalance());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
    }
}