package za.co.wethinkcode.acceptance;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RobotStateTests {
    private final static int DEFAULT_PORT = 5000;
    private final static String DEFAULT_IP = "localhost";
    private RobotWorldClient serverClient;

    @BeforeEach
    void connectToServer(){
        serverClient = new RobotWorldJsonClient();
        serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
    }

    @AfterEach
    void disconnectFromServer(){
        serverClient.disconnect();
        serverClient = null;
    }

    @Test
    void robotStateDoesNotExistsInTheWorld(){
        assertTrue(serverClient.isConnected());

        String request = "{" +
                "  \"robot\": \"JOE\"," +
                "  \"command\": \"state\"," +
                "  \"arguments\": []" +
                "}";

        JsonNode response = serverClient.sendRequest(request);

        System.out.println(response.toString());
        assertNotNull(response.get("result"));
        assertEquals("ERROR", response.get("result").asText());

        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("message"));
        assertTrue(response.get("data").get("message").asText().contains("Robot does not exist"));

        assertNull(response.get("state"));
    }



}
