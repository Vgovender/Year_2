package za.co.wethinkcode.server.commands;

import za.co.wethinkcode.communication.*;
import za.co.wethinkcode.server.*;


public class BackCommand extends Command {

    private Request clientRequest;

    public BackCommand(Request request) {
        this.clientRequest = request;
    }

    @Override
    public Response execute() throws RequestErrorException {

        try {
            // Getting Database
            Db db = MultiServer.dataBase.get(0);

            // Get robot
            Robots robot = db.findOne(clientRequest.getRobot());

            // Make changes to robot
            UpdateResponse updated =  UpdateResponse.SUCCESS;
            for(int i = 0; i <= Integer.parseInt(clientRequest.getArguments()[0]); i++){
                updated = robot.updatePosition(db, -1 * Integer.parseInt(clientRequest.getArguments()[0]));
            }

            // Build State
            return new Response("Back", MoveUtil.checkUpdateResponse(db, robot, updated), buildState(robot));
        } catch (Exception e) {
            throw new RequestErrorException("Could not parse arguments");
        }
    }
}