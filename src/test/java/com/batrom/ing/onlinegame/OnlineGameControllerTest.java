package com.batrom.ing.onlinegame;

import com.batrom.ing.BaseControllerTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OnlineGameController.class)
class OnlineGameControllerTest extends BaseControllerTest {

    @Override
    protected String folderName() {
        return "onlinegame";
    }

    @Override
    protected String endpoint() {
        return "/onlinegame/calculate";
    }
}