package za.co.wethinkcode.acceptance;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RobotLookTests {
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
    void LookIfWorldIsEmptyTest(){
        assertTrue(serverClient.isConnected());

        String request = "{" +
                "  \"robot\": \"JOE\"," +
                "  \"command\": \"look\"," +
                "  \"arguments\": []" +
                "}";

        JsonNode response = serverClient.sendRequest(request);
        assertNotNull(response.get("result"));
        assertEquals("ERROR", response.get("result").asText());

        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("message"));
        assertTrue(response.get("data").get("message").asText().contains("Robot does not exist"));
    }

     @Test
     void seeAnObstacleTest(){
         assertTrue(serverClient.isConnected());

 //        Given a world of size 2x2
 //        and the world has an obstacle at coordinate [0,1]
 //        and I have successfully launched a robot into the world
         String launchrequest = "{" +
                 "  \"robot\": \"HAL\"," +
                 "  \"command\": \"launch\"," +
                 "  \"arguments\": [\"sniper]\",\"5\",\"5\"]" +
                 "}";
         String request = "{" +
                 "  \"robot\": \"HAL\"," +
                 "  \"command\": \"look\"," +
                 "  \"arguments\": []" +
                 "}";
 //        When I ask the robot to look
         JsonNode lResponse = serverClient.sendRequest(launchrequest);
         JsonNode response = serverClient.sendRequest(request);

 //        Then I should get a response back with an object of type OBSTACLE at a distance of 1 step.
         assertNotNull(response.get("result"));
         assertEquals("OK", response.get("result").asText());
         assertNotNull(response.get("data").get("objects"));
         assertTrue(response.get("data").get("objects").isArray());

         JsonNode arr = response.get("data").get("objects");

         for(JsonNode elem: arr){
             if (elem.get("type").asText().equals("OBSTACLE")) {
                 assertEquals("1", elem.get("distance").asText());
                 break;
             }
         }
     }
}
