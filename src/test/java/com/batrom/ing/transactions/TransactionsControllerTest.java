package com.batrom.ing.transactions;

import com.batrom.ing.FileResourcesUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TransactionsController.class)
class TransactionsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void test() throws Exception {
        final var requestFiles = FileResourcesUtil.allFilesFromResource("transactions/request");
        final var responseFiles = FileResourcesUtil.allFilesFromResource("transactions/response");

        for (int i = 0; i < requestFiles.size(); i++) {
            final var requestFile = requestFiles.get(i);
            final var responseFile = responseFiles.get(i);
            final var request = FileResourcesUtil.readFile(requestFile)
                    .stream()
                    .map(TransactionsControllerTest::toRequest)
                    .toList();

            final var response = FileResourcesUtil.readFile(responseFile)
                    .stream()
                    .map(TransactionsControllerTest::toResponse)
                    .toList();

            mvc.perform(post("/transactions/report")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(content().string(objectMapper.writeValueAsString(response)));
        }
    }

    private static Request toRequest(final String line) {
        final var data = line.split(";");
        return new Request(data[0], data[1], Double.parseDouble(data[2]));
    }

    private static Response toResponse(final String line) {
        final var data = line.split(";");
        return new Response(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), Double.parseDouble(data[3]));
    }

    private record Request(String debitAccount, String creditAccount, double amount) {
    }

    private record Response(String account, int debitCount, int creditCount, double balance) {
    }

}