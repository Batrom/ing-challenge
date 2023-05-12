package com.batrom.ing;

import com.batrom.ing.atmservice.Input;
import com.batrom.ing.atmservice.InputDeserializer;
import com.batrom.ing.atmservice.Order;
import com.batrom.ing.atmservice.OrderSerializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;

public class IngApplication {

    public static void main(String[] args) {

        final var module = new SimpleModule();
        module.addDeserializer(Input.class, new InputDeserializer());
        module.addSerializer(Order.class, new OrderSerializer());
        final var app = Javalin.create(config -> {
                    config.jsonMapper(new JavalinJackson().updateMapper(mapper -> {
                        mapper.registerModule(module);
                    }));
                    config.http.maxRequestSize = 52_428_800;
                })
                .get("/", ctx -> ctx.result("Hello World"))
                .post("/atms/calculateOrder", ctx -> ctx.json(ctx.bodyAsClass(Input.class).toResponse()))
                .start(8080);
    }
}
