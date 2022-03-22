package za.co.wethinkcode.worldrobotapi.worldapi;

import io.javalin.Javalin;
import za.co.wethinkcode.worldrobotapi.world.GameWorld;
import za.co.wethinkcode.worldrobotapi.worldcommands.CommandHandler;

public class ServerApi {

    @lombok.Getter
    private final Javalin javalin;

    public ServerApi(CommandHandler commandHandler, GameWorld gameWorld){

        this.javalin = Javalin.create(config -> config.defaultContentType = "application/json");
        javalin.get("/admin/robots", commandHandler::listRobots);
        javalin.delete("/admin/robot/{name}", commandHandler::deleteRobot);
        javalin.post("/admin/obstacles", commandHandler::addObstacles);
        javalin.delete("/admin/obstacles", commandHandler::addObstacles);
        javalin.post("/user/move/forward", commandHandler::moveRobot);
        javalin.get("/admin/load/{world}", commandHandler::loadWorld);
        javalin.post("/admin/save/{world}", commandHandler::saveWorld);
        javalin.post("/user/launch", ctx ->  commandHandler.launchRobot(ctx, gameWorld));
        javalin.post("/user/move/turn", commandHandler::moveRobot);
        javalin.get("/world",commandHandler::getWorlds);
    }

    public Javalin start(){
        return this.javalin.start(7000);
    }

    public Javalin stop(){
        return this.javalin.stop();
    }

}
