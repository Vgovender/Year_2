package za.co.wethinkcode.server.commands;

import za.co.wethinkcode.communication.*;
import za.co.wethinkcode.server.*;

import java.util.*;

public class TurnCommand extends Command {
    private Request request;

    public TurnCommand(Request request) {
        this.request = request;
    }

    @Override
    public Response execute() throws RequestErrorException {
        try {
            // Get Dtatabase
            Db db = MultiServer.dataBase.get(0);

            // Get Robot
            Robots robot = db.findOne(request.getRobot());

            // Make changes to robot
            boolean turnRight;
            if(request.getArguments()[0].toLowerCase().equals("right")){
                turnRight = true;
            }else if(request.getArguments()[0].toLowerCase().equals("left")){
                turnRight = false;
            }else{
                throw new RequestErrorException("Could not parse arguments");
            }
            robot.changeDirection(turnRight);

            // Update Database
            //putDatabase(db);
            return new Response("OK", new HashMap<>(), buildState(robot));
        } catch (Exception e) {
            throw new RequestErrorException("Could not parse arguments");
        }
    }

}