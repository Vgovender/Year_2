package za.co.wethinkcode.acceptance;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import za.co.wethinkcode.server.RequestErrorException;
// import za.co.wethinkcode.server.commands.ForwardCommand;
// import za.co.wethinkcode.communication.Request;
import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.JsonNode;

// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Forwardtest {
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
    public void testForwardFail() throws RequestErrorException{
        //connectToServer();
        assertTrue(serverClient.isConnected());

        String requestlaunch = "{" +
               "  \"robot\": \"HAL\"," +
               "  \"command\": \"launch\"," +
               "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
               "}";

       JsonNode response = serverClient.sendRequest(requestlaunch);

       // When I send a valid launch request to the server
       String request_forward = "{" +
               "  \"robot\": \"HAL\"," +
               "  \"command\": \"forward\"," +
               "  \"arguments\": [\"0.5\"]" +
               "}";

       response = serverClient.sendRequest(request_forward);

       // Then I should get a valid response from the server
       assertNotNull(response.get("result"));
       System.out.println(response.get("result"));
       assertEquals("ERROR", response.get("result").asText());
       //disconnectFromServer();
    }

    @Test 
    public void testForwardPass() throws RequestErrorException{
        //connectToServer();
        assertTrue(serverClient.isConnected());

        String requestlaunch = "{" +
               "  \"robot\": \"HAL\"," +
               "  \"command\": \"launch\"," +
               "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
               "}";

       JsonNode response = serverClient.sendRequest(requestlaunch);

       // When I send a valid launch request to the server
       String request_forward = "{" +
               "  \"robot\": \"HAL\"," +
               "  \"command\": \"forward\"," +
               "  \"arguments\": [\"5\"]" +
               "}";

       response = serverClient.sendRequest(request_forward);

       // Then I should get a valid response from the server
       assertNotNull(response.get("result"));
       assertNotNull(response.get("data").get("message"));
       assertEquals("OK", response.get("result").asText());
       assertEquals("At the NORTH edge", response.get("data").get("message").asText());
       //disconnectFromServer();
    }

    // TODO:Write test, to test the output of the message being displayed to the user
    // @Test 
    // public void testForwardOutput() throws RequestErrorException{
    //     connectToServer();
    //     assertTrue(serverClient.isConnected());

    //     String requestlaunch = "{" +
    //            "  \"robot\": \"HAL\"," +
    //            "  \"command\": \"launch\"," +
    //            "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
    //            "}";

    //    JsonNode response = serverClient.sendRequest(requestlaunch);

    //    // When I send a valid launch request to the server
    //    String request_forward = "{" +
    //            "  \"robot\": \"HAL\"," +
    //            "  \"command\": \"forward\"," +
    //            "  \"arguments\": [\"5\"]" +
    //            "}";

    //    response = serverClient.sendRequest(request_forward);

    //    // Then I should get a valid response from the server
    //    assertNotNull(response.get("result"));
    // //    System.out.println(response.get("result"));
    //    assertEquals("OK", response.get("result").asText());
    //    disconnectFromServer();
    // }
}
