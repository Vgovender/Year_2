package za.co.wethinkcode.server.commands;

import java.util.HashMap;
// import java.util.List;
import java.util.Map;

import za.co.wethinkcode.communication.*;
import za.co.wethinkcode.server.*;

public class RepairCommand extends Command{
    private Request clientRequest;

    public RepairCommand(Request request) {
        this.clientRequest = request;
    }

    @Override
    public Response execute() throws RequestErrorException {

        try {
            // Getting Database
            Db target = MultiServer.dataBase.get(0);

            // Get robot
            Robots robot = target.findOne(clientRequest.getRobot());
            if(robot.getShields()> 0){
                robot.getRepair();
            }
            Map<String, Object> data = new HashMap<>();

            // Make changes to robot

            robot.setStatus(Status.REPAIRING);

            //putDatabase(target);

            return new Response("OK", new HashMap<>(), buildState(robot));
        } catch (Exception e) {
            throw new RequestErrorException("Could not parse arguments");
        }
    }   
}