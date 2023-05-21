package com.batrom.ing;

import com.batrom.ing.atmservice.ATMServiceController;
import com.batrom.ing.onlinegame.OnlineGameController;
import com.batrom.ing.transactions.TransactionsController;
import io.javalin.Javalin;
import io.javalin.testtools.HttpClient;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class IngApplicationTest {

    private final Javalin javalinServer = IngApplication.createJavalinServer();

    @Test
    void testATMServiceEndpointWithSampleData1() {
        final String request = """
                [
                  {
                    "region": 4,
                    "requestType": "STANDARD",
                    "atmId": 1
                  },
                  {
                    "region": 1,
                    "requestType": "STANDARD",
                    "atmId": 1
                  },
                  {
                    "region": 2,
                    "requestType": "STANDARD",
                    "atmId": 1
                  },
                  {
                    "region": 3,
                    "requestType": "PRIORITY",
                    "atmId": 2
                  },
                  {
                    "region": 3,
                    "requestType": "STANDARD",
                    "atmId": 1
                  },
                  {
                    "region": 2,
                    "requestType": "SIGNAL_LOW",
                    "atmId": 1
                  },
                  {
                    "region": 5,
                    "requestType": "STANDARD",
                    "atmId": 2
                  },
                  {
                    "region": 5,
                    "requestType": "FAILURE_RESTART",
                    "atmId": 1
                  }
                ]
                """;
        final String expectedResponse = trim("""
                [
                  {
                    "region": 1,
                    "atmId": 1
                  },
                  {
                    "region": 2,
                    "atmId": 1
                  },
                  {
                    "region": 3,
                    "atmId": 2
                  },
                  {
                    "region": 3,
                    "atmId": 1
                  },
                  {
                    "region": 4,
                    "atmId": 1
                  },
                  {
                    "region": 5,
                    "atmId": 1
                  },
                  {
                    "region": 5,
                    "atmId": 2
                  }
                ]
                """);

        testEndpoint(ATMServiceController.ENDPOINT, request, expectedResponse);
    }

    @Test
    void testATMServiceEndpointWithSampleData2() {
        final String request = """
                [
                  {
                    "region": 1,
                    "requestType": "STANDARD",
                    "atmId": 2
                  },
                  {
                    "region": 1,
                    "requestType": "STANDARD",
                    "atmId": 1
                  },
                  {
                    "region": 2,
                    "requestType": "PRIORITY",
                    "atmId": 3
                  },
                  {
                    "region": 3,
                    "requestType": "STANDARD",
                    "atmId": 4
                  },
                  {
                    "region": 4,
                    "requestType": "STANDARD",
                    "atmId": 5
                  },
                  {
                    "region": 5,
                    "requestType": "PRIORITY",
                    "atmId": 2
                  },
                  {
                    "region": 5,
                    "requestType": "STANDARD",
                    "atmId": 1
                  },
                  {
                    "region": 3,
                    "requestType": "SIGNAL_LOW",
                    "atmId": 2
                  },
                  {
                    "region": 2,
                    "requestType": "SIGNAL_LOW",
                    "atmId": 1
                  },
                  {
                    "region": 3,
                    "requestType": "FAILURE_RESTART",
                    "atmId": 1
                  }
                ]
                """;
        final String expectedResponse = trim("""
                [
                  {
                    "region": 1,
                    "atmId": 2
                  },
                  {
                    "region": 1,
                    "atmId": 1
                  },
                  {
                    "region": 2,
                    "atmId": 3
                  },
                  {
                    "region": 2,
                    "atmId": 1
                  },
                  {
                    "region": 3,
                    "atmId": 1
                  },
                  {
                    "region": 3,
                    "atmId": 2
                  },
                  {
                    "region": 3,
                    "atmId": 4
                  },
                  {
                    "region": 4,
                    "atmId": 5
                  },
                  {
                    "region": 5,
                    "atmId": 2
                  },
                  {
                    "region": 5,
                    "atmId": 1
                  }
                ]
                """);
        testEndpoint(ATMServiceController.ENDPOINT, request, expectedResponse);
    }

    @Test
    void testATMServiceEndpointWithSampleData3() {
        final String request = """
                [
                
                    {

                            "requestType"   :  "STANDARD",

                                     "atmId"  :     1,


                        "region"   :    2
                            
                                     
                      }  ,
                
                    {

                            "requestType"   :  "PRIORITY",

                                     "atmId"  :     2,


                        "region"   :    2
                            
                                     
                      }
                            ,
             {"region":1,"requestType":"STANDARD","atmId":2}]""";
        final String expectedResponse = trim("""
                [
                  {
                    "region": 1,
                    "atmId": 2
                  },
                  {
                    "region": 2,
                    "atmId": 2
                  },
                  {
                    "region": 2,
                    "atmId": 1
                  }
                ]
                """);
        testEndpoint(ATMServiceController.ENDPOINT, request, expectedResponse);
    }

    @Test
    void testATMServiceEndpointWithEmptyData() {
        final String request = "[]";
        final String expectedResponse = "[]";

        testEndpoint(ATMServiceController.ENDPOINT, request, expectedResponse);
    }

    @Test
    void testOnlineGameEndpointWithSampleData1() {
        final String request = """
                {
                  "groupCount": 6,
                  "clans": [
                    {
                      "numberOfPlayers": 4,
                      "points": 50
                    },
                    {
                      "numberOfPlayers": 2,
                      "points": 70
                    },
                    {
                      "numberOfPlayers": 6,
                      "points": 60
                    },
                    {
                      "numberOfPlayers": 1,
                      "points": 15
                    },
                    {
                      "numberOfPlayers": 5,
                      "points": 40
                    },
                    {
                      "numberOfPlayers": 3,
                      "points": 45
                    },
                    {
                      "numberOfPlayers": 1,
                      "points": 12
                    },
                    {
                      "numberOfPlayers": 4,
                      "points": 40
                    }
                  ]
                }
                        """;
        final String expectedResponse = trim("""
                [
                  [
                    {
                      "numberOfPlayers": 2,
                      "points": 70
                    },
                    {
                      "numberOfPlayers": 4,
                      "points": 50
                    }
                  ],
                  [
                    {
                      "numberOfPlayers": 6,
                      "points": 60
                    }
                  ],
                  [
                    {
                      "numberOfPlayers": 3,
                      "points": 45
                    },
                    {
                      "numberOfPlayers": 1,
                      "points": 15
                    },
                    {
                      "numberOfPlayers": 1,
                      "points": 12
                    }
                  ],
                  [
                    {
                      "numberOfPlayers": 4,
                      "points": 40
                    }
                  ],
                  [
                    {
                      "numberOfPlayers": 5,
                      "points": 40
                    }
                  ]
                ]
                """);
        testEndpoint(OnlineGameController.ENDPOINT, request, expectedResponse);
    }

    @Test
    void testOnlineGameEndpointWithSampleData2() {
        final String request = """
                
                {
                  "clans":     [
               {
                        "numberOfPlayers"   : 2  ,
              "points"
              : 10
                           }         ,
                    {"numberOfPlayers":1,"points": 20},{
                    
                      "points"  :  10
                      
                      ,
                      
                          "numberOfPlayers" :        1 
                    
                    
                    }
                    ,
                    {  "numberOfPlayers"   : 1,  "points"
                    :
                    15
                    
                        }
                    ]
                    ,
                         "groupCount"    :   2
                    
                    
                }
                        """;
        final String expectedResponse = trim("""
                [
                  [
                    {
                      "numberOfPlayers": 1,
                      "points": 20
                    },
                    {
                      "numberOfPlayers": 1,
                      "points": 15
                    }
                  ],
                  [
                    {
                      "numberOfPlayers": 1,
                      "points": 10
                    }
                  ],
                  [
                    {
                      "numberOfPlayers": 2,
                      "points": 10
                    }
                  ]
                ]
                """);
        testEndpoint(OnlineGameController.ENDPOINT, request, expectedResponse);
    }

    @Test
    void testOnlineGameEndpointWithEmptyData() {
        final String request = """
                {
                  "groupCount": 1,
                  "clans": []
                }
                """;
        final String expectedResponse = "[]";

        testEndpoint(OnlineGameController.ENDPOINT, request, expectedResponse);
    }

    @Test
    void testTransactionsEndpointWithSampleData1() {
        final String request = """
                [
                  {
                    "debitAccount": "32309111922661937852684864",
                    "creditAccount": "06105023389842834748547303",
                    "amount": 10.90
                  },
                  {
                    "debitAccount": "31074318698137062235845814",
                    "creditAccount": "66105036543749403346524547",
                    "amount": 200.90
                  },
                  {
                    "debitAccount": "66105036543749403346524547",
                    "creditAccount": "32309111922661937852684864",
                    "amount": 50.10
                  }
                ]
                """;
        final String expectedResponse = trim("""
                [
                  {
                    "account": "06105023389842834748547303",
                    "debitCount": 0,
                    "creditCount": 1,
                    "balance": 10.90
                  },
                  {
                    "account": "31074318698137062235845814",
                    "debitCount": 1,
                    "creditCount": 0,
                    "balance": -200.90
                  },
                  {
                    "account": "32309111922661937852684864",
                    "debitCount": 1,
                    "creditCount": 1,
                    "balance": 39.20
                  },
                  {
                    "account": "66105036543749403346524547",
                    "debitCount": 1,
                    "creditCount": 1,
                    "balance": 150.80
                  }
                ]
                """);

        testEndpoint(TransactionsController.ENDPOINT, request, expectedResponse);
    }

    @Test
    void testTransactionsEndpointWithSampleData2() {
        final String request = """
                
                        [{
                    "creditAccount": "06105023389842834748547303","amount"
                   : 10.9012
                   
                   
                   ,"debitAccount"   :        "32309111922661937852684864"},
                    
                    {
                    
                    
                    "debitAccount": "31074318698137062235845814",
                    
                               "creditAccount"     :"66105036543749403346524547","amount": 200
              },{"debitAccount": "66105036543749403346524547","amount": 50.09511,
                    
                    
                    "creditAccount"
                    : "32309111922661937852684864"  ,
             },{
                    "debitAccount": "32309111922661937852684864",
                        
                        "creditAccount": "54846133516271094482015843"   ,"amount": 39.204999
                  
                  }
                  
                  ]
                  
                """;
        final String expectedResponse = trim("""
                [
                  {
                    "account": "06105023389842834748547303",
                    "debitCount": 0,
                    "creditCount": 1,
                    "balance": 10.90
                  },
                  {
                    "account": "31074318698137062235845814",
                    "debitCount": 1,
                    "creditCount": 0,
                    "balance": -200.00
                  },
                  {
                    "account": "32309111922661937852684864",
                    "debitCount": 2,
                    "creditCount": 1,
                    "balance": 0.00
                  },
                  {
                    "account": "54846133516271094482015843",
                    "debitCount": 0,
                    "creditCount": 1,
                    "balance": 39.20
                  },
                  {
                    "account": "66105036543749403346524547",
                    "debitCount": 1,
                    "creditCount": 1,
                    "balance": 149.90
                  }
                ]
                """);

        testEndpoint(TransactionsController.ENDPOINT, request, expectedResponse);
    }

    private static String trim(final String json) {
        return json.replaceAll(" ", "").replaceAll("\n", "");
    }

    @Test
    void testTransactionsEndpointWithEmptyData() {
        final String request = "[]";
        final String expectedResponse = "[]";

        testEndpoint(TransactionsController.ENDPOINT, request, expectedResponse);
    }

    private void testEndpoint(final String endpoint, final String request, final String expectedResponse) {
        JavalinTest.test(javalinServer, (server, client) -> Assertions.assertEquals(expectedResponse, executeCall(client, endpoint, request)));
    }

    private String executeCall(final HttpClient client, final String endpoint, final String request) throws IOException {
        return client.post(endpoint, request).body().string();
    }

}