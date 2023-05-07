package com.batrom.ing.atmservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class ATMServiceController {

    @PostMapping("/atms/calculateOrder")
    ResponseEntity<List<ATM>> report(@RequestBody final Input input) {
        return ResponseEntity.ok(input.toResponse());
    }
}
