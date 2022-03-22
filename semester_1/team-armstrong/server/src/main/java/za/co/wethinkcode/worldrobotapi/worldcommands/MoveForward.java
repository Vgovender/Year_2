package za.co.wethinkcode.worldrobotapi.worldcommands;

import za.co.wethinkcode.communication.Request;
import za.co.wethinkcode.communication.Response;
import za.co.wethinkcode.server.RequestErrorException;
import za.co.wethinkcode.server.UpdateResponse;
import za.co.wethinkcode.worldrobotapi.utils.MoveHelper;
import za.co.wethinkcode.worldrobotapi.world.Robot;
import za.co.wethinkcode.worldrobotapi.worldapi.RobotApi;

public class MoveForward extends WorldApiCommands{
    private final Request clientRequest;

    public MoveForward(Request request){this.clientRequest = request;}

    @Override
    public Response execute() throws RequestErrorException {
        try{
            Robot robot = RobotApi.gameWorld.findOne(clientRequest.getRobot());

            UpdateResponse updated = UpdateResponse.SUCCESS;
            for(int i = 0; i <= Integer.parseInt(clientRequest.getArguments()[0]); i++ ){
                updated = robot.updatePosition(Integer.parseInt(String.valueOf(i == 0 ? 0 : 1)));
            }
            return new Response("OK", MoveHelper.checkUpdateResponse(robot, updated), buildState(robot));
        } catch (Exception e){
            throw new RequestErrorException("Could not parse arguments");
        }
    }
}
