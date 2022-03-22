package za.co.wethinkcode.worldrobotapi.worldapi;


import za.co.wethinkcode.YAMLHandler;
import za.co.wethinkcode.worldrobotapi.world.GameWorld;
import za.co.wethinkcode.worldrobotapi.worldcommands.CommandHandler;

public class RobotApi {
    public static GameWorld gameWorld = new GameWorld(YAMLHandler.getYamlObject("Configurations.yml"));

    public static void main(String[] args) {
        CommandHandler commandHandler = new CommandHandler();
        ServerApi serverApi = new ServerApi(commandHandler, gameWorld);

        serverApi.start();
        


    }
}
//        
//        serverApi.getJavalin().get("/world/{name}", context -> commandHandler.restoreWorld(context, gameWorld) );
//        serverApi.getJavalin().post("/robot", commandHandler::launchRobot);
