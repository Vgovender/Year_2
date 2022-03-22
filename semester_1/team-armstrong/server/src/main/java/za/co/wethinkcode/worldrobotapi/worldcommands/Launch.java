package za.co.wethinkcode.worldrobotapi.worldcommands;

import za.co.wethinkcode.communication.Request;
import za.co.wethinkcode.communication.Response;
import za.co.wethinkcode.server.*;
import za.co.wethinkcode.worldrobotapi.robotmake.Mine;
import za.co.wethinkcode.worldrobotapi.robotmake.Sniper;
import za.co.wethinkcode.worldrobotapi.world.Robot;
import za.co.wethinkcode.worldrobotapi.worldapi.RobotApi;

import java.util.*;

public class Launch extends WorldApiCommands{
    private Request request;

    public Launch(Request request){this.request = request;}

    @Override
    public Response execute() throws RequestErrorException {
        try{
            Map<String, Object> data = new HashMap<>();
            int shots = Integer.parseInt(request.getArguments()[1]);
            int shields = Integer.parseInt(request.getArguments()[2]);

            for (Robot robot: RobotApi.gameWorld.getRobotList()){
                if (robot.getName().equals(request.getRobot())){
                    data.put("message", "Too many of you in this world");
                    return new Response("ERROR", data, null);
                }
            }
            int[] position = RobotApi.gameWorld.getRobotList().isEmpty() ? new int[]{0, 0} : getSpot();

            if(position.length == 0) {

                data.put("message", "No more space in this world");
                return new Response("ERROR", data, null);
            }

            if (Arrays.asList(request.getArguments()).contains("sniper")){
                Sniper sniperRobot = new Sniper(request.getRobot(), shields, shots, position[0], position[1]);
                RobotApi.gameWorld.addToRobotList(sniperRobot);
                return new Response("OK", buildData(data, sniperRobot), buildState(sniperRobot));
            }else if(Arrays.asList(request.getArguments()).contains("mine")){
                za.co.wethinkcode.worldrobotapi.robotmake.Mine mine = new Mine(request.getRobot(), shields, shots, position[0], position[1]);
                RobotApi.gameWorld.addToRobotList(mine);

                return new Response("OK", buildData(data, mine), buildState(mine));
            }

            Robot robot = new Robot(request.getRobot(), shields, shots, position[0], position[1]);
            RobotApi.gameWorld.addToRobotList(robot);
            return new Response("OK", buildData(data, robot), buildState(robot));
        }catch (Exception e){ throw new RequestErrorException("Could not parse arguments");}
    }

    private List<Position> createPositions(){
        List<Position> positions = new ArrayList<>();
        int width = -Math.floorDiv(RobotApi.gameWorld.getWidth(), 2);
        int height = Math.floorDiv(RobotApi.gameWorld.getHeight(), 2);

        for(int i = width; i <= Math.abs(width); i++){
            for(int j = height; j >= -height; j--){
                positions.add(new Position(i, j));
            }
        }
        return positions;
    }

    private int[] getSpot() {
        Random rand = new Random();
        List<Position> allPositions = createPositions();

        int[] position = new int[2];
        while (!allPositions.isEmpty()) {
            Position foundPosition = allPositions.get(rand.nextInt(allPositions.size()));
            int proposedX = foundPosition.getX();
            int proposedY = foundPosition.getY();

            if (!checkProposed(new int[] { proposedX, proposedY })) {
                position[0] = proposedX;
                position[1] = proposedY;
                return position;
            }
            allPositions.remove(foundPosition);
        }
        return new int[]{};

    }

    private boolean checkProposed(int[] position) {
        boolean obsCheck = RobotApi.gameWorld.checkObstacles(position[0], position[1]).equals(true);
        boolean takenRobotCheck = RobotApi.gameWorld.robotPositionTaken(position[0], position[1]);
        return !obsCheck || takenRobotCheck;
    }


    private Map<String, Object> buildData(Map<String, Object> data, Robot robot){
        data.put(("position"), robot.getPosition());
        data.put("visibility" , RobotApi.gameWorld.getVisibility());
        data.put("reload", RobotApi.gameWorld.getReload());
        data.put("repair", RobotApi.gameWorld.getRepair());
        data.put("mine", RobotApi.gameWorld.getMine());
        data.put("shields", RobotApi.gameWorld.getShields());
        return data;
    }
}
