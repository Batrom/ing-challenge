package com.batrom.ing;

import com.batrom.ing.atmservice.ATMServiceController;
import com.batrom.ing.onlinegame.OnlineGameController;
import com.batrom.ing.transactions.TransactionsController;
import io.javalin.testtools.HttpClient;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class IngApplicationTest {

    @Test
    public void testATMServiceEndpointWithSampleData1() {
        final String request = "[{\"region\":4,\"requestType\":\"STANDARD\",\"atmId\":1},{\"region\":1,\"requestType\":\"STANDARD\",\"atmId\":1},{\"region\":2,\"requestType\":\"STANDARD\",\"atmId\":1},{\"region\":3,\"requestType\":\"PRIORITY\",\"atmId\":2},{\"region\":3,\"requestType\":\"STANDARD\",\"atmId\":1},{\"region\":2,\"requestType\":\"SIGNAL_LOW\",\"atmId\":1},{\"region\":5,\"requestType\":\"STANDARD\",\"atmId\":2},{\"region\":5,\"requestType\":\"FAILURE_RESTART\",\"atmId\":1}]";
        final String expectedResponse = "[{\"region\":1,\"atmId\":1},{\"region\":2,\"atmId\":1},{\"region\":3,\"atmId\":2},{\"region\":3,\"atmId\":1},{\"region\":4,\"atmId\":1},{\"region\":5,\"atmId\":1},{\"region\":5,\"atmId\":2}]";

        testEndpoint(ATMServiceController.ENDPOINT, request, expectedResponse);
    }

    @Test
    public void testATMServiceEndpointWithSampleData2() {
        final String request = "[{\"region\":1,\"requestType\":\"STANDARD\",\"atmId\":2},{\"region\":1,\"requestType\":\"STANDARD\",\"atmId\":1},{\"region\":2,\"requestType\":\"PRIORITY\",\"atmId\":3},{\"region\":3,\"requestType\":\"STANDARD\",\"atmId\":4},{\"region\":4,\"requestType\":\"STANDARD\",\"atmId\":5},{\"region\":5,\"requestType\":\"PRIORITY\",\"atmId\":2},{\"region\":5,\"requestType\":\"STANDARD\",\"atmId\":1},{\"region\":3,\"requestType\":\"SIGNAL_LOW\",\"atmId\":2},{\"region\":2,\"requestType\":\"SIGNAL_LOW\",\"atmId\":1},{\"region\":3,\"requestType\":\"FAILURE_RESTART\",\"atmId\":1}]";
        final String expectedResponse = "[{\"region\":1,\"atmId\":2},{\"region\":1,\"atmId\":1},{\"region\":2,\"atmId\":3},{\"region\":2,\"atmId\":1},{\"region\":3,\"atmId\":1},{\"region\":3,\"atmId\":2},{\"region\":3,\"atmId\":4},{\"region\":4,\"atmId\":5},{\"region\":5,\"atmId\":2},{\"region\":5,\"atmId\":1}]";

        testEndpoint(ATMServiceController.ENDPOINT, request, expectedResponse);
    }

    @Test
    public void testATMServiceEndpointWithEmptyData() {
        final String request = "[]";
        final String expectedResponse = "[]";

        testEndpoint(ATMServiceController.ENDPOINT, request, expectedResponse);
    }

    @Test
    public void testOnlineGameEndpointWithSampleData() {
        final String request = "{\"groupCount\":6,\"clans\":[{\"numberOfPlayers\":4,\"points\":50},{\"numberOfPlayers\":2,\"points\":70},{\"numberOfPlayers\":6,\"points\":60},{\"numberOfPlayers\":1,\"points\":15},{\"numberOfPlayers\":5,\"points\":40},{\"numberOfPlayers\":3,\"points\":45},{\"numberOfPlayers\":1,\"points\":12},{\"numberOfPlayers\":4,\"points\":40}]}";
        final String expectedResponse = "[[{\"numberOfPlayers\":2,\"points\":70},{\"numberOfPlayers\":4,\"points\":50}],[{\"numberOfPlayers\":6,\"points\":60}],[{\"numberOfPlayers\":3,\"points\":45},{\"numberOfPlayers\":1,\"points\":15},{\"numberOfPlayers\":1,\"points\":12}],[{\"numberOfPlayers\":4,\"points\":40}],[{\"numberOfPlayers\":5,\"points\":40}]]";

        testEndpoint(OnlineGameController.ENDPOINT, request, expectedResponse);
    }

    @Test
    public void testOnlineGameEndpointWithEmptyData() {
        final String request = "{\"groupCount\":1,\"clans\":[]}";
        final String expectedResponse = "[]";

        testEndpoint(OnlineGameController.ENDPOINT, request, expectedResponse);
    }

    @Test
    public void testTransactionsEndpointWithSampleData() {
        final String request = "[{\"debitAccount\":\"32309111922661937852684864\",\"creditAccount\":\"06105023389842834748547303\",\"amount\":10.9},{\"debitAccount\":\"31074318698137062235845814\",\"creditAccount\":\"66105036543749403346524547\",\"amount\":200.9},{\"debitAccount\":\"66105036543749403346524547\",\"creditAccount\":\"32309111922661937852684864\",\"amount\":50.1}]";
        final String expectedResponse = "[{\"account\":\"06105023389842834748547303\",\"debitCount\":0,\"creditCount\":1,\"balance\":10.9},{\"account\":\"31074318698137062235845814\",\"debitCount\":1,\"creditCount\":0,\"balance\":-200.9},{\"account\":\"32309111922661937852684864\",\"debitCount\":1,\"creditCount\":1,\"balance\":39.2},{\"account\":\"66105036543749403346524547\",\"debitCount\":1,\"creditCount\":1,\"balance\":150.8}]";

        testEndpoint(TransactionsController.ENDPOINT, request, expectedResponse);
    }

    @Test
    public void testTransactionsEndpointWithEmptyData() {
        final String request = "[]";
        final String expectedResponse = "[]";

        testEndpoint(TransactionsController.ENDPOINT, request, expectedResponse);
    }

    private void testEndpoint(final String endpoint, final String request, final String expectedResponse) {
        final var javalinServer = IngApplication.createJavalinServer();
        JavalinTest.test(javalinServer, (server, client) -> Assertions.assertEquals(expectedResponse, executeCall(client, endpoint, request)));
    }

    private String executeCall(final HttpClient client, final String endpoint, final String request) throws IOException {
        return client.post(endpoint, request).body().string();
    }

}