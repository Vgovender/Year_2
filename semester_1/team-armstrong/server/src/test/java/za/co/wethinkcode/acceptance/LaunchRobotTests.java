package za.co.wethinkcode.acceptance;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * As a player
 * I want to launch my robot in the online robot world
 * So that I can break the record for the most robot kills
 */
class LaunchRobotTests {
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
   void validLaunchShouldSucceed(){
       // Given that I am connected to a running Robot Worlds server
       // And the world is of size 1x1 (The world is configured or hardcoded to this size)

       assertTrue(serverClient.isConnected());

       // When I send a valid launch request to the server
       String request = "{" +
               "  \"robot\": \"jake\"," +
               "  \"command\": \"launch\"," +
               "  \"arguments\": [\"sniper]\",\"5\",\"5\"]" +
               "}";

       JsonNode response = serverClient.sendRequest(request);

       // Then I should get a valid response from the server
       assertNotNull(response.get("result"));
       assertEquals("OK", response.get("result").asText());

       // And the position should be (x:0, y:0)
       assertNotNull(response.get("data"));
       assertNotNull(response.get("data").get("position"));
       assertNotNull(response.get("state"));
   }

    @Test
    void invalidLaunchShouldFail(){
        // Given that I am connected to a running Robot Worlds server
        assertTrue(serverClient.isConnected());

        // When I send an invalid launch request with the command "luanch" instead of "launch"
        String request = "{" +
                "\"robot\": \"HAL\"," +
                "\"command\": \"luanch\"," +
                "\"arguments\": [\"sniper\",\"5\",\"5\"]" +
                "}";
        JsonNode response = serverClient.sendRequest(request);

        // Then I should get an error response
        assertNotNull(response.get("result"));
        assertEquals("ERROR", response.get("result").asText());

        // And the message "Unsupported command"
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("message"));
        assertTrue(response.get("data").get("message").asText().contains("Unsupported command"));
    }

    @Test
    void tooManyOfYou(){
        // Given that I am connected to a running Robot Worlds server
        assertTrue(serverClient.isConnected());

        // When I send a valid launch request to a full server
        String firstRequest = "{" +
                "\"robot\": \"HAL\"," +
                "\"command\": \"launch\"," +
                "\"arguments\": [\"sniper\",\"5\",\"5\"]" +
                "}";
        JsonNode response = serverClient.sendRequest(firstRequest);

        String secondRequest = "{" +
                "\"robot\": \"HAL\"," +
                "\"command\": \"launch\"," +
                "\"arguments\": [\"sniper\",\"5\",\"5\"]" +
                "}";
        response = serverClient.sendRequest(secondRequest);

        // Then I should get an error response
        assertEquals("ERROR", response.get("result").asText());

        // And the message "Too many of you in this world"
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("message"));
        assertTrue(response.get("data").get("message").asText().contains("Too many of you in this world"));
    }

    //2x2 world
//    @Test
//    void canLaunchAnotherRobot(){
//        assertTrue(serverClient.isConnected());
//
//        // robot "HAL" has already been launched into the world
//       String firstRobot = "{" +
//               "  \"robot\": \"Hal\"," +
//               "  \"command\": \"launch\"," +
//               "  \"arguments\": [\"sniper\",\"5\",\"5\"]" +
//               "}";
//
//       JsonNode response1 = serverClient.sendRequest(firstRobot);
//
//       // Launch should be successful
//       assertNotNull(response1.get("result"));
//
//       assertEquals("OK", response1.get("result").asText());
//
//       assertNotNull(response1.get("data"));
//       assertNotNull(response1.get("data").get("position"));
//       assertNotNull(response1.get("state"));
//
//        // Given a world of size 2x2
//        assertTrue(serverClient.isConnected());
//
//       // When I launch robot "R2D2" into the world
//       String secondRobot = "{" +
//               "  \"robot\": \"R2D2\"," +
//               "  \"command\": \"launch\"," +
//               "  \"arguments\": [\"sniper\",\"5\",\"5\"]" +
//               "}";
//
//       JsonNode response2 = serverClient.sendRequest(secondRobot);
//
//       // Launch should be successful
//       assertNotNull(response2.get("result"));
//
//       assertEquals("OK", response2.get("result").asText());
//
//       // and a randomly allocated position of R2D2 should be returned
//       assertNotNull(response2.get("data"));
//       assertNotNull(response2.get("data").get("position"));
//       assertNotNull(response2.get("state"));
//   }

   @Test
   void worldWorldWithoutObstaclesIsFull(){
       // Given that I am connected to a running Robot Worlds server
       assertTrue(serverClient.isConnected());

       String R1 = "{" +
               "  \"robot\": \"Robot1\"," +
               "  \"command\": \"launch\"," +
               "  \"arguments\": [\"sniper\",\"5\",\"5\"]" +
               "}";

       serverClient.sendRequest(R1);
    //  launch 2nd robot into world
       String R2 = "{" +
               "  \"robot\": \"Robot2\"," +
               "  \"command\": \"launch\"," +
               "  \"arguments\": [\"sniper\",\"5\",\"5\"]" +
               "}";

            serverClient.sendRequest(R2);
    //  launch 3rd robot into world
    String R3 = "{" +
               "  \"robot\": \"Robot3\"," +
               "  \"command\": \"launch\"," +
               "  \"arguments\": [\"sniper\",\"5\",\"5\"]" +
               "}";

            serverClient.sendRequest(R3);
    //  launch 4th robot into world
    String R4 = "{" +
               "  \"robot\": \"Robot4\"," +
               "  \"command\": \"launch\"," +
               "  \"arguments\": [\"sniper\",\"5\",\"5\"]" +
               "}";

            serverClient.sendRequest(R4);
    //  launch 5th robot into world
    String R5 = "{" +
               "  \"robot\": \"Robot5\"," +
               "  \"command\": \"launch\"," +
               "  \"arguments\": [\"sniper\",\"5\",\"5\"]" +
               "}";

            serverClient.sendRequest(R5);
    //  launch 6th robot into world
    String R6 = "{" +
               "  \"robot\": \"Robot6\"," +
               "  \"command\": \"launch\"," +
               "  \"arguments\": [\"sniper\",\"5\",\"5\"]" +
               "}";

            serverClient.sendRequest(R6);
    //  launch 7th robot into world
    String R7 = "{" +
               "  \"robot\": \"Robot7\"," +
               "  \"command\": \"launch\"," +
               "  \"arguments\": [\"sniper\",\"5\",\"5\"]" +
               "}";

            serverClient.sendRequest(R7);
    //  launch 8th robot into world
    String R8 = "{" +
               "  \"robot\": \"Robot8\"," +
               "  \"command\": \"launch\"," +
               "  \"arguments\": [\"sniper\",\"5\",\"5\"]" +
               "}";

            serverClient.sendRequest(R8);
    //  launch 9th robot into world
    String R9 = "{" +
               "  \"robot\": \"Robot9\"," +
               "  \"command\": \"launch\"," +
               "  \"arguments\": [\"sniper\",\"5\",\"5\"]" +
               "}";

       serverClient.sendRequest(R9);
    //  launch 10th robot into world (should fail)
       String R10 = "{" +
               "  \"robot\": \"Robot10\"," +
               "  \"command\": \"launch\"," +
               "  \"arguments\": [\"sniper\",\"5\",\"5\"]" +
               "}";

       JsonNode response = serverClient.sendRequest(R10);

       // Then I should get an error response
       assertEquals("ERROR", response.get("result").asText());

       // And the message "No more space in this world"
       assertNotNull(response.get("data"));
       assertNotNull(response.get("data").get("message"));
        //    System.out.println(response.get("data").get("message"));
       assertTrue(response.get("data").get("message").asText().contains("No more space in this world"));
   }
}
