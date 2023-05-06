package com.batrom.ing.transactions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionsController {

    @PostMapping
    public ResponseEntity<Account[]> report(@RequestBody final AccountBalanceChange[] changes) {

    }
}
