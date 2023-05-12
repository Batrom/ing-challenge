package com.batrom.ing.atmservice;

import io.javalin.http.Handler;

public interface ATMServiceController {
    String ENDPOINT = "/atms/calculateOrder";
    Handler HANDLER = ctx -> ctx.json(ctx.bodyAsClass(Input.class).toResponse());
}
