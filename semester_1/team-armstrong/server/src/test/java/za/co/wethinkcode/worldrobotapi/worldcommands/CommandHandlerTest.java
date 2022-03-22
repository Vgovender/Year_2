package za.co.wethinkcode.worldrobotapi.worldcommands;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.YAMLHandler;
import za.co.wethinkcode.worldrobotapi.world.GameWorld;
import za.co.wethinkcode.worldrobotapi.worldapi.ServerApi;


class CommandHandlerTest {
   private CommandHandler commandHandler;
   private GameWorld gameWorld;
   private ServerApi serverApi;


    @BeforeEach
    void connectServerApi(){
        commandHandler = new CommandHandler();
        gameWorld = new GameWorld(YAMLHandler.getYamlObject("Configurations.yml"));
        serverApi = new ServerApi(commandHandler, gameWorld);
        serverApi.start();
    }

    @AfterEach
    void disconnectServerApi(){
        serverApi.stop();
        commandHandler = null;
        gameWorld = null;
        serverApi = null;
    }


    @Test
    @DisplayName("POST /user/launch")
    void launchRobot() {

        String request = "{" +
                "  \"robot\": \"jake\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"sniper]\",\"5\",\"5\"]" +
                "}";
        HttpResponse<JsonNode> res = Unirest.post("http://localhost:7000/user/launch")
                .body(request)
                .asJson();
        assertEquals(res.getStatus(), 201);
        assertTrue(res.getBody().getObject().has("robotList"));
    
        serverApi.stop();

    }

    @Test
    void moveRobot() {
    }

    @Test
    void loadWorld() {
    }

    @Test
    void saveWorld() {
    }

    @Test
    void deleteRobot() {
    }

    @Test
    void listRobots() {
    }

    @Test
    void getWorld() {
    }
}