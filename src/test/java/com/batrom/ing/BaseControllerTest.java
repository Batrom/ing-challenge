package com.batrom.ing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class BaseControllerTest {

    @Autowired
    private MockMvc mvc;

    protected abstract String folderName();
    protected abstract String endpoint();

    @Test
    void testEndpoint() throws Exception {
        final var requestFiles = FileResourcesUtil.allFilesFromResource(folderName() + "/request");
        final var responseFiles = FileResourcesUtil.allFilesFromResource(folderName() + "/response");

        for (int i = 0; i < requestFiles.size(); i++) {
            final var requestFile = requestFiles.get(i);
            final var responseFile = responseFiles.get(i);
            final var request = FileResourcesUtil.readFile(requestFile);
            final var response = FileResourcesUtil.readFile(responseFile);

            mvc.perform(post(endpoint())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request))
                    .andExpect(status().isOk())
                    .andExpect(content().string(response));
        }
    }

}