package za.co.wethinkcode.server.commands;


import java.util.*;

import za.co.wethinkcode.communication.*;
import za.co.wethinkcode.server.*;
import za.co.wethinkcode.server.RequestErrorException;

public class ReloadCommand extends Command{
    private Request clientRequest;

    public ReloadCommand(Request req){
        this.clientRequest = req;

    }





    @Override
    public Response execute() throws RequestErrorException {
        try {
            // Getting Database
            Db target = MultiServer.dataBase.get(0);

            // Get robot
            Robots robot = target.findOne(clientRequest.getRobot());
            if(robot.getShots()<= 0){
                robot.setStatus(Status.RELOADING);
                robot.reloadGun();
                
                // System.out.println(robot.getName() + " repaired shield now at " + robot.getShields());
            }
            Map<String, Object> data = new HashMap<>();

            // Make changes to robot
            // robot.updateStatus();
            robot.setStatus(Status.RELOADING);
            data.put("message", "Done");
            // System.out.println(robot.getShields());
            // target.updateRobot(robot);
            //putDatabase(target);

            return new Response("OK", data, buildState(robot));
        } catch (Exception e) {
            throw new RequestErrorException("Could not parse arguments");
        }
    }
    
}
