package za.co.wethinkcode.server.commands;

import java.util.*;

import za.co.wethinkcode.communication.*;
import za.co.wethinkcode.server.*;

public class StateCommand extends Command{
    private Request clientRequest;

    public StateCommand(Request request) {
        this.clientRequest = request;
    }

    @Override
    public Response execute() throws RequestErrorException {

        try {
            // Getting Database
            Db db = MultiServer.dataBase.get(0);
            Map<String, Object> data = new HashMap<>();

            // Get robot
            Robots robot = db.findOne(clientRequest.getRobot());
            if(robot == null) {
                data.put("message", "Robot does not exist");
                return new Response("ERROR", data, null);
            }

            // Build State
            return new Response("OK", new HashMap<>(), buildState(robot));
        } catch (Exception e) {
            throw new RequestErrorException("Could not parse arguments");
        }
    }
}
