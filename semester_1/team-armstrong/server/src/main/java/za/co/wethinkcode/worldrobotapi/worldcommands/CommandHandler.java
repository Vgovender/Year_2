package za.co.wethinkcode.worldrobotapi.worldcommands;


import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import net.lemnik.eodsql.EoDException;
import net.lemnik.eodsql.InvalidQueryException;
import net.lemnik.eodsql.QueryTool;

import za.co.wethinkcode.YAMLHandler;
import za.co.wethinkcode.communication.Request;
import za.co.wethinkcode.communication.Response;
import za.co.wethinkcode.dao.ObstacleDO;
import za.co.wethinkcode.dao.WorldDO;
import za.co.wethinkcode.dao.WorldsDAI.WorldDAI;
import za.co.wethinkcode.server.RequestErrorException;
import za.co.wethinkcode.server.SinglePointObstacle;
import za.co.wethinkcode.worldrobotapi.dbconnection.DbConnection;
import za.co.wethinkcode.worldrobotapi.world.GameWorld;
import za.co.wethinkcode.worldrobotapi.world.Robot;
import za.co.wethinkcode.worldrobotapi.worldapi.RobotApi;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler {
    private final WorldDAI worldDAI = QueryTool.getQuery(DbConnection.startDbConnection(), WorldDAI.class);
    public CommandHandler(){}

    public void launchRobot(Context context, GameWorld gameWorld){
        Response response;
        try{
            Request req = context.bodyAsClass(Request.class);
            WorldApiCommands command = WorldApiCommands.create(req);
            response = command.execute();
            if (response.getResult().equals("OK")){
                context.json(gameWorld);
                context.status(HttpCode.CREATED);
            }else{
                context.json(response);
                context.status(HttpCode.NOT_FOUND);
            }
        } catch (RuntimeException | RequestErrorException e){
            context.status(HttpCode.NOT_FOUND);
        }


    }

    public void moveRobot(Context context){
        Response response;
        try {
            Request request = context.bodyAsClass(Request.class);
            WorldApiCommands commands = WorldApiCommands.create(request);
            response = commands.execute();
            context.json(response);
            context.status(HttpCode.OK);

        } catch (RequestErrorException e) {
            context.status(HttpCode.NOT_FOUND);
        }
    }

    public void addObstacles(Context context){
        try{
            Request request = context.bodyAsClass(Request.class);
            WorldApiCommands commands = WorldApiCommands.create(request);
            context.json(commands.execute());

        } catch (RequestErrorException e) {
            context.status(HttpCode.NOT_FOUND);
        }

    }

    public void loadWorld(Context context){
        String name = context.pathParamAsClass("world", String.class).get();
        WorldDO worldDO = getWorld(name);
        if (worldDO == null){
            context.status(HttpCode.NOT_FOUND);
            context.result("Name of World does not exist");
        }else{
            try{
                List<ObstacleDO> obstacleDO = getObstacles(worldDO.getWorldId());
                GameWorld gameWorld = new GameWorld(YAMLHandler.getYamlObject("Configurations.yml"));
                gameWorld.setVisibility(worldDO.getVisibility());
                gameWorld.setHeight(worldDO.getWorldSize());
                gameWorld.setWidth(worldDO.getWorldSize());
                gameWorld.setWorldname(worldDO.getWorldName());

                for (ObstacleDO obs:obstacleDO) {
                    System.out.println(obs.getX_val());
                    gameWorld.getObstacleList().add(new SinglePointObstacle(obs.getX_val(), obs.getY_val()));
                }
                RobotApi.gameWorld = gameWorld;
                context.json(gameWorld);
            }catch (InvalidQueryException e){
                context.result(e.toString());
            } catch (RuntimeException e){
                context.status(HttpCode.NOT_FOUND);
                context.result("Name of World does not exist");
            }
        }
    }

    public void saveWorld(Context context){
        String name = context.pathParamAsClass("world", String.class).get();
        try {

            worldDAI.save(name, RobotApi.gameWorld.getVisibility(), RobotApi.gameWorld.getHeight() );
            WorldDO world = worldDAI.getWorld(name);
            for (SinglePointObstacle point:RobotApi.gameWorld.getObstacleList()) {
                worldDAI.addObstacles(point.getBottomLeftX(), point.getBottomLeftY(), point.getSize(), world.getWorldId());
            }
            context.result("World Saved Successfully");
        }catch (Exception e){
            context.status(HttpCode.NOT_FOUND);
        }
    }

    public void deleteRobot(Context context){
        String robotName = context.pathParamAsClass("name", String.class).get();
        boolean deleted = false;
        try {
            for (Robot bot:RobotApi.gameWorld.getRobotList()) {
                if(bot.getName().equals(robotName)){
                    RobotApi.gameWorld.getRobotList().remove(bot);
                    deleted = true;
                    break;
                }
            }
            if (deleted){
                context.result("Success");
            }
            else {
                context.result("robot does not exist");
                context.status(HttpCode.NOT_FOUND);
            }
        }catch (Exception e){
            context.status(HttpCode.NOT_FOUND);
        }
    }

    public void listRobots(Context context){
        if (RobotApi.gameWorld.getRobotList().isEmpty()){
            context.result("World does not have robots");
        }
        else{
            context.json(RobotApi.gameWorld.getRobotList());
        }
    }

    private List<ObstacleDO> getObstacles(int id) {
        try{
            return worldDAI.getObstacles(id);
        } catch(EoDException eoDException){
            return new ArrayList<>();
        }
    }

    private WorldDO getWorld(String name){
        try {
            return worldDAI.getWorld(name);
        }catch (NullPointerException e){
            return null;
        }
    }

    private List<ObstacleDO> getAllObstacles() {
        try{
            return worldDAI.getAllObstacles();
        } catch(EoDException eoDException){
            return new ArrayList<>();
        }
    }

    public void getWorlds(Context context){
        List<WorldDO> Worlds = worldDAI.getAllWorldDOs();
        List<GameWorld> gameWorlds = new ArrayList<>();
        List<ObstacleDO> allObstacles = getAllObstacles();
        for (WorldDO worldDO : Worlds) {
            GameWorld world = new GameWorld(YAMLHandler.getYamlObject("Configurations.yml"));
            world.setVisibility(worldDO.getVisibility());
            world.setHeight(worldDO.getWorldSize());
            world.setWidth(worldDO.getWorldSize());
            
            world.setWorldname(worldDO.getWorldName());
            for (ObstacleDO obstacleDO : allObstacles) {
                if (worldDO.getWorldId() == obstacleDO.getObstacle_world()){
                    world.getObstacleList().add(new SinglePointObstacle(obstacleDO.getX_val(), obstacleDO.getY_val()));
                }
            }
            gameWorlds.add(world);
        }
        context.json(gameWorlds);
    }
}
