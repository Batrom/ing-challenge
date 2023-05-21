package com.batrom.ing;

import com.batrom.ing.atmservice.ATMServiceController;
import com.batrom.ing.onlinegame.OnlineGameController;
import com.batrom.ing.transactions.TransactionsController;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;

public class IngApplication {

    private static final int MAX_REQUEST_SIZE = 104_857_600; // 100 MB
    private static final int PORT = 8080;

    public static void main(final String[] args) {
        createJavalinServer().start(PORT);
    }

    static Javalin createJavalinServer() {
        return Javalin.create(IngApplication::config)
                .post(ATMServiceController.ENDPOINT, ATMServiceController.HANDLER)
                .post(OnlineGameController.ENDPOINT, OnlineGameController.HANDLER)
                .post(TransactionsController.ENDPOINT, TransactionsController.HANDLER);
    }

    private static void config(final JavalinConfig config) {
        config.jsonMapper(new CustomJsonMapper());
        config.http.maxRequestSize = MAX_REQUEST_SIZE;
    }
}
