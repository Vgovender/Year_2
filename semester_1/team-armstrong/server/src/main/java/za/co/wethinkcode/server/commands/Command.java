package za.co.wethinkcode.server.commands;

import java.io.*;
// import java.rmi.ServerError;
import java.util.*;

import com.google.gson.stream.JsonReader;

import za.co.wethinkcode.JSONHandler;
import za.co.wethinkcode.communication.Request;
import za.co.wethinkcode.communication.Response;
import za.co.wethinkcode.server.*;
// import za.co.wethinkcode.server.commands.*;

public abstract class Command {

    public static Command create(Request request, Server server) throws RequestErrorException {
        switch (request.getCommand().toLowerCase()) {
            case "state":
                return new StateCommand(request);
        case "look":
            return new LookCommand(request);
        case "launch":
            return new LaunchCommand(request, server);
        case "forward":
            return new ForwardCommand(request);
        case "back":
            return new BackCommand(request);
        case "turn":
            return new TurnCommand(request);
        case "mine":
            return new CommandSetMine(request);
        case "fire":
            return new AttackCommand(request, server);
        case "repair":
            return new RepairCommand(request);
        case "reload":
            return new ReloadCommand(request);
        default:
            throw new RequestErrorException("Unsupported command");
        }
    }

    public abstract Response execute() throws RequestErrorException;

    public Db getDataBase() {
        try {
            JsonReader reader = new JsonReader(new FileReader("src/main/java/za/co/wethinkcode/server/DB.JSON"));
            return JSONHandler.deserializeDb(reader);
        } catch (Exception e) {
            return null;
        }

    }

    public void putDatabase(Db db) {
        try {
            FileWriter writer = new FileWriter(new File("src/main/java/za/co/wethinkcode/server/DB.JSON"));
            System.out.println(db.toString());
            JSONHandler.serializeJavaObjectDb(db, writer);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public Map<String, Object> buildState(Robots robot) {
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
        Map<String, Object> look = new HashMap<String, Object>();
        look.put("direction", getDirection(robotDirection, direction));
        look.put("type", object);
        look.put("distance", distance);

        return look;
    }

    private String getDirectionEast(String directionTaken){
        if (directionTaken.equals("NORTH")) {
            return "WEST";
        } else if (directionTaken.equals("EAST")) {
            return "NORTH";
        } else if (directionTaken.equals("SOUTH")) {
            return "EAST";
        } else {
            return "SOUTH";
        }
    }

    private String getDirectionSouth(String directionTaken){
        if (directionTaken.equals("NORTH")) {
            return "SOUTH";
        } else if (directionTaken.equals("EAST")) {
            return "WEST";
        } else if (directionTaken.equals("SOUTH")) {
            return "NORTH";
        } else {
            return "EAST";
        }
    }

    private String getDirectionWest(String directionTaken){
        if (directionTaken.equals("NORTH")) {
            return "EAST";
        } else if (directionTaken.equals("EAST")) {
            return "SOUTH";
        } else if (directionTaken.equals("SOUTH")) {
            return "WEST";
        } else {
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
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("objects", object);


        return data;
    }


    


}




