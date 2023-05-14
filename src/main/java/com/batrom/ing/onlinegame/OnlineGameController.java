package com.batrom.ing.onlinegame;

import io.javalin.http.Handler;

public interface OnlineGameController {
    String ENDPOINT = "/onlinegame/calculate";
    Handler HANDLER = ctx -> ctx.json(PlayersToGroupsAssigner.assign(ctx.bodyAsClass(OnlineGameInput.class)));
}
