package com.batrom.ing;

import com.batrom.ing.atmservice.*;
import com.batrom.ing.onlinegame.Group;
import com.batrom.ing.onlinegame.GroupsSerializer;
import com.batrom.ing.onlinegame.OnlineGameController;
import com.batrom.ing.transactions.Account;
import com.batrom.ing.transactions.AccountsSerializer;
import com.batrom.ing.transactions.TransactionsController;
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
        config.jsonMapper(new JavalinJackson().updateMapper(mapper -> {
            mapper.registerModule(moduleWithSerializers());
        }));
        config.http.maxRequestSize = MAX_REQUEST_SIZE;
    }

    private static SimpleModule moduleWithSerializers() {
        final var module = new SimpleModule();
        module.addDeserializer(com.batrom.ing.atmservice.Input.class, new com.batrom.ing.atmservice.InputDeserializer());
        module.addDeserializer(com.batrom.ing.transactions.Input.class, new com.batrom.ing.transactions.InputDeserializer());
        module.addDeserializer(Input.class, new InputDeserializer());
        module.addSerializer(Order.class, new OrderSerializer());
        module.addSerializer(Account[].class, new AccountsSerializer());
        module.addSerializer(Group[].class, new GroupsSerializer());
        return module;
    }
}
