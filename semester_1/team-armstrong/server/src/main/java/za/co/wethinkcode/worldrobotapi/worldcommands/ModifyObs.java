package za.co.wethinkcode.worldrobotapi.worldcommands;

import lombok.AllArgsConstructor;
import za.co.wethinkcode.communication.Request;
import za.co.wethinkcode.communication.Response;
import za.co.wethinkcode.server.RequestErrorException;
import za.co.wethinkcode.server.SinglePointObstacle;
import za.co.wethinkcode.worldrobotapi.worldapi.RobotApi;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class ModifyObs extends WorldApiCommands{
    private Request request;


    @Override
    public Response execute() throws RequestErrorException {
        try {
            Map<String, Object> data = new HashMap<>();
            if (request.getCommand().equals("add")) {
                for (String val : request.getArguments()) {
                    int x = Integer.parseInt(val);
                    int y = Integer.parseInt(val);
                    RobotApi.gameWorld.getObstacleList().add(new SinglePointObstacle(x, y));
                }
                data.put("message", "Obstacles added");
                return new Response("SUCCESS", data, null);
            } else if (request.getCommand().equals("delete")) {
                for (String val : request.getArguments()) {
                    int x = Integer.parseInt(val);
                    for (SinglePointObstacle point:RobotApi.gameWorld.getObstacleList()) {
                        if (point.getBottomLeftX() == x){
                            RobotApi.gameWorld.getObstacleList().remove(point);
                            break;
                        }
                    }
                }
                data.put("message", "Obstacles deleted");
                return new Response("SUCCESS", data, null);
            } else {
                throw new RequestErrorException("Could not parse arguments");
            }
        } catch (RequestErrorException e) {
            throw new RequestErrorException("Could not parse arguments");
        }
    }

}
