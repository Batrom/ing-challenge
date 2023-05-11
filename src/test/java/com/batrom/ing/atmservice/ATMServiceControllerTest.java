package com.batrom.ing.atmservice;

import com.batrom.ing.BaseControllerTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ATMServiceController.class)
class ATMServiceControllerTest extends BaseControllerTest {

    @Override
    protected String folderName() {
        return "atmservice";
    }

    @Override
    protected String endpoint() {
        return "/atms/calculateOrder";
    }
}