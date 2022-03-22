package za.co.wethinkcode.worldrobotapi.worldcommands;


import za.co.wethinkcode.communication.Request;
import za.co.wethinkcode.communication.Response;
import za.co.wethinkcode.server.RequestErrorException;
import za.co.wethinkcode.worldrobotapi.world.Robot;

import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class WorldApiCommands {
    public static WorldApiCommands create(Request request) throws RequestErrorException {
        switch (request.getCommand().toLowerCase()) {
            case "launch":
                return new Launch(request);
            case "forward":
                return new MoveForward(request);
            case "turn":
                return new Turn(request);
            case "add":
            case "delete":
                return new ModifyObs(request);
            default:
                throw new RequestErrorException("Unsupported command");
        }
    }

    public abstract Response execute() throws RequestErrorException;

    public Map<String, Object> buildState(Robot robot) {
        Map<String, Object> state = new HashMap<>();

        state.put("position", robot.getPosition());
        state.put("direction", robot.getCurrentDirection().name());
        state.put("shields", robot.getShields());
        state.put("shots", robot.getShots());
        state.put("status", robot.getState().name());

        return state;
    }

    public Map<String, Object> viewObject(int distance, String object, String robotDirection, String direction)
    {
        Map<String, Object> look = new HashMap<>();
        look.put("direction", getDirection(robotDirection, direction));
        look.put("type", object);
        look.put("distance", distance);

        return look;
    }

    private String getDirectionEast(String directionTaken){
        switch (directionTaken) {
            case "NORTH":
                return "WEST";
            case "EAST":
                return "NORTH";
            case "SOUTH":
                return "EAST";
            default:
                return "SOUTH";
        }
    }

    private String getDirectionSouth(String directionTaken){
        switch (directionTaken) {
            case "NORTH":
                return "SOUTH";
            case "EAST":
                return "WEST";
            case "SOUTH":
                return "NORTH";
            default:
                return "EAST";
        }
    }

    private String getDirectionWest(String directionTaken){
        switch (directionTaken) {
            case "NORTH":
                return "EAST";
            case "EAST":
                return "SOUTH";
            case "SOUTH":
                return "WEST";
            default:
                return "NORTH";
        }
    }

    private String getDirection(String robotDirection, String directionTaken) {
        switch (robotDirection) {
            case "EAST":
                return getDirectionEast( directionTaken);
            case "SOUTH":
                return getDirectionSouth(directionTaken);
            case "WEST":
                return getDirectionWest(directionTaken);
            default:
                return directionTaken;
        }
    }

    public Map<String, Object> buildData(List<Map<String, Object>> object)
    {
        Map<String, Object> data = new HashMap<>();
        data.put("objects", object);


        return data;
    }
}
