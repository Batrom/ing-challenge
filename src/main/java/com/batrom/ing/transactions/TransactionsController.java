package com.batrom.ing.transactions;

import io.javalin.http.Handler;

public interface TransactionsController {
    String ENDPOINT = "/transactions/report";
    Handler HANDLER = ctx -> ctx.json(ctx.bodyAsClass(Input.class).toResponse());
}
