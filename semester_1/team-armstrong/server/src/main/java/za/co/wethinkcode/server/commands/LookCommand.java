package za.co.wethinkcode.server.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import za.co.wethinkcode.communication.Request;
import za.co.wethinkcode.communication.Response;
import za.co.wethinkcode.server.*;
import za.co.wethinkcode.server.world.Pits;

public class LookCommand extends Command{

    private static final Logger LOGGER = Logger.getLogger(LookCommand.class.getName());
    private final Request clientRequest;
    private List<LookObjects> seenObjects = new ArrayList<>();



    public LookCommand(Request request)
    {
       this.clientRequest = request;
    }

    @Override
    public Response execute() throws RequestErrorException {
        // Gets the database
        Db target = MultiServer.dataBase.get(0);
        Map<String, Object> data = new HashMap<>();
       
        // Get Robot
        Robots robot = target.findOne(clientRequest.getRobot());

        if(robot == null){
            data.put("message", "Robot does not exist");
            return new Response("ERROR", data, null);
        }

        int visibillity = target.getVisibility();

        northObstacles(target.getObstacleList(), robot.getRealPosition(), visibillity);
        eastObstacles(target.getObstacleList(), robot.getRealPosition(), visibillity);
        westObstacles(target.getObstacleList(), robot.getRealPosition(), visibillity);
        southObstacles(target.getObstacleList(), robot.getRealPosition(), visibillity);

       if(this.seenObjects.size() == 0){
           LOGGER.info("No nearby Obstacles!");
       }

       data.put("objects", this.seenObjects);


        return new Response("OK",data,buildState(robot));
       
    }
   //  /----------------------------------------------------------------
    public void northObstacles(List<SinglePointObstacle> obstacles,Position player, int visibility){
        for(SinglePointObstacle obs: obstacles){
            if(player.getX() == obs.getBottomLeftX() && obs.getBottomLeftY() >= player.getY()){
                if(obs.getBottomLeftY() <= player.getY() + visibility){
                    this.seenObjects.add(new LookObjects("NORTH", "OBSTACLE", Math.abs(obs.getBottomLeftY() - player.getY())));
                }
            }
        }

    }

    public void eastObstacles(List<SinglePointObstacle> obstacles,Position player, int visibility){
        for(SinglePointObstacle obs: obstacles){
            if(player.getY() == obs.getBottomLeftY() && obs.getBottomLeftX() >= player.getX()){
                if(obs.getBottomLeftX() <= player.getX() + visibility){
                    this.seenObjects.add(new LookObjects("EAST", "OBSTACLE", Math.abs(obs.getBottomLeftX() - player.getX())));
                }
            }
        }

    }

    public void westObstacles(List<SinglePointObstacle> obstacles,Position player, int visibility){
        for(SinglePointObstacle obs: obstacles){
            if(player.getY() == obs.getBottomLeftY() && obs.getBottomLeftX() <= player.getX()){
                if(obs.getBottomLeftX() >= -visibility + player.getX() ) {
                    this.seenObjects.add(new LookObjects("WEST", "OBSTACLE", Math.abs(obs.getBottomLeftX() - player.getX())));
                }
            }
        }

    }

    public void southObstacles(List<SinglePointObstacle> obstacles,Position player, int visibility){
        for(SinglePointObstacle obs: obstacles){
            if(player.getX() == obs.getBottomLeftX() && obs.getBottomLeftY() <= player.getY()){
                if(obs.getBottomLeftY() >= player.getY() + (-visibility)){
                    this.seenObjects.add(new LookObjects("SOUTH", "OBSTACLE", Math.abs(obs.getBottomLeftY() - player.getY())));
                }
            }
        }

    }

}


  















