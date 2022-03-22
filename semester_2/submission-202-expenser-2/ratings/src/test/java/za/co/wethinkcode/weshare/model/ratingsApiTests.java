package za.co.wethinkcode.weshare.model;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.weshare.RatingsWebServer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ratingsApiTests {
    private static RatingsWebServer server;

    @BeforeAll
    public static void startServer(){
        server = new RatingsWebServer();
        server.start(5001);
    }
    @AfterAll
    public static void stopServer() {
        server.close();
    }


    @Test
    @DisplayName("GET /ratings/settled")
    void testExecuteCommand() throws UnirestException {

        String persons = "[{\"id\": \"12\", \"name\": \"herman\",\"email\": \"herman@wethinkcode.co.za\"},{\"id\": \"13\", " +
                "\"name\": \"herman\",\"email\": \"Mike@wethinkcode.co.za\"}," +
                "{\"id\": \"12\", \"name\": \"herman\",\"email\": \"sett@wethinkcode.co.za\"}]";
        HttpResponse<JsonNode> launchResponse =  Unirest.get("http://localhost:5001/ratings/settled")
                        .header("persons",persons).asJson();

        assertEquals(201, launchResponse.getStatus());
        assertTrue(launchResponse.getBody().toString().contains("[{\"when\": \"2022-02-24\", \"who\": \"herman@wethinkcode.co.za\",\"amount\": \"400.0\"},{\"when\": \"2022-02-24\", " +
                "\"who\": \"Mike@wethinkcode.co.za\",\"amount\": \"400.0\"}," + "]"));
    }

}
