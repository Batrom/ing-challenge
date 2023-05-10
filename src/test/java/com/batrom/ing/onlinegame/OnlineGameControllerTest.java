package com.batrom.ing.onlinegame;

import com.batrom.ing.FileResourcesUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OnlineGameController.class)
class OnlineGameControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void test() throws Exception {
        final var requestFiles = FileResourcesUtil.allFilesFromResource("onlinegame/request");
        final var responseFiles = FileResourcesUtil.allFilesFromResource("onlinegame/response");

        for (int i = 0; i < requestFiles.size(); i++) {
            final var requestFile = requestFiles.get(i);
            final var responseFile = responseFiles.get(i);
            final var fileLines = FileResourcesUtil.readFile(requestFile);
            final var clans = fileLines.stream()
                    .skip(1)
                    .map(OnlineGameControllerTest::toRequestClan)
                    .toList();
            final var request = new Request(Integer.parseInt(fileLines.get(0)), clans);

            final var response = FileResourcesUtil.readFile(responseFile)
                    .stream()
                    .map(OnlineGameControllerTest::toResponseGroup)
                    .toArray(ResponseGroup[][]::new);

            mvc.perform(post("/onlinegame/calculate")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(content().string(objectMapper.writeValueAsString(response)));
        }
    }

    private static Request.Clan toRequestClan(final String line) {
        final var data = line.split(";");
        return new Request.Clan(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
    }

    private static ResponseGroup[] toResponseGroup(final String line) {
        final var dataList = line.split("\\|");
        return Arrays.stream(dataList)
                .map(data -> data.split(";"))
                .map(data -> new ResponseGroup(Integer.parseInt(data[0]), Integer.parseInt(data[1])))
                .toArray(ResponseGroup[]::new);
    }

    private record Request(int groupCount, List<Clan> clans) {
        private record Clan(int numberOfPlayers, int points){}
    }

    private record ResponseGroup(int numberOfPlayers, int points) {
    }

}