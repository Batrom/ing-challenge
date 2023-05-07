package com.batrom.ing.onlinegame;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class OnlineGameController {

    @PostMapping("/onlinegame/calculate")
    ResponseEntity<List<Group>> report(@RequestBody final Players players) {
        return ResponseEntity.ok(PlayersToGroupsAssigner.assign(players));
    }
}
