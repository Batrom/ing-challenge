package com.batrom.ing.transactions;

import com.batrom.ing.BaseControllerTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TransactionsController.class)
class TransactionsControllerTest extends BaseControllerTest {

    @Override
    protected String folderName() {
        return "transactions";
    }

    @Override
    protected String endpoint() {
        return "/transactions/report";
    }
}