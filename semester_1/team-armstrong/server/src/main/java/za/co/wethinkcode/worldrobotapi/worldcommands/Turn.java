package za.co.wethinkcode.worldrobotapi.worldcommands;

import za.co.wethinkcode.communication.Request;
import za.co.wethinkcode.communication.Response;
import za.co.wethinkcode.server.RequestErrorException;
import za.co.wethinkcode.worldrobotapi.world.Robot;
import za.co.wethinkcode.worldrobotapi.worldapi.RobotApi;

import java.util.HashMap;

public class Turn extends WorldApiCommands {
    private Request request;

    public Turn(Request request) {
        this.request = request;
    }

    @Override
    public Response execute() throws RequestErrorException {
        try {
            // Get Robot
            Robot robot = RobotApi.gameWorld.findOne(request.getRobot());

            // Make changes to robot
            boolean turnRight;
            if (request.getArguments()[0].equalsIgnoreCase("right")) {
                turnRight = true;
            } else if (request.getArguments()[0].equalsIgnoreCase("left")) {
                turnRight = false;
            } else {
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
