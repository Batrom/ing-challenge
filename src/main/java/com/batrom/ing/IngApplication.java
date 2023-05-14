package com.batrom.ing;

import com.batrom.ing.atmservice.*;
import com.batrom.ing.onlinegame.*;
import com.batrom.ing.transactions.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.json.JavalinJackson;

public class IngApplication {

    private static final int MAX_REQUEST_SIZE = 52_428_800;
    private static final int PORT = 8080;

    public static void main(String[] args) {
        Javalin.create(IngApplication::config)
                .post(ATMServiceController.ENDPOINT, ATMServiceController.HANDLER)
                .post(OnlineGameController.ENDPOINT, OnlineGameController.HANDLER)
                .post(TransactionsController.ENDPOINT, TransactionsController.HANDLER)
                .start(PORT);
    }

    private static void config(final JavalinConfig config) {
        config.jsonMapper(
                new JavalinJackson().updateMapper(mapper -> {
                    mapper.registerModule(moduleWithSerializers());
                }));
        config.http.maxRequestSize = MAX_REQUEST_SIZE;
    }

    private static SimpleModule moduleWithSerializers() {
        final var module = new SimpleModule();
        module.addDeserializer(ATMServiceInput.class, new ATMServiceInputDeserializer());
        module.addDeserializer(TransactionsInput.class, new TransactionsInputDeserializer());
        module.addDeserializer(OnlineGameInput.class, new OnlineGameInputDeserializer());
        module.addSerializer(ATM[].class, new ATMsSerializer());
        module.addSerializer(Account[].class, new AccountsSerializer());
        module.addSerializer(Group[].class, new GroupsSerializer());
        return module;
    }
}
