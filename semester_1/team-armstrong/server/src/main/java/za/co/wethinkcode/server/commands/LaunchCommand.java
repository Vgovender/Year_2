package za.co.wethinkcode.server.commands;

import za.co.wethinkcode.server.*;
import java.util.*;
import za.co.wethinkcode.communication.*;


public class LaunchCommand extends Command {
    private Request req;
    private Server server;

    public LaunchCommand(Request req, Server server) {
        this.req = req;
        this.server = server;
    }

    @Override
    public Response execute() throws RequestErrorException {
        try {
            Map<String, Object> data = new HashMap<>();
            int shots = Integer.parseInt(req.getArguments()[1]);
            int shields = Integer.parseInt(req.getArguments()[2]);
            Db db = MultiServer.dataBase.get(0);


            for(int i = 0 ; i < db.getRobotList().size(); i++){
                if(db.getRobotList().get(i).getName().equals(req.getRobot())){

                    data.put("message", "Too many of you in this world");
                    return new Response("ERROR", data, null);
                }
            }
            int[] position = db.getRobotList().isEmpty() ? new int[]{0, 0} : getSpot(db);

            if(position.length == 0) {
                
                data.put("message", "No more space in this world");
                return new Response("ERROR", data, null);
            }
            if (Arrays.asList(req.getArguments()).contains("sniper")){
                SniperRobot sniperRobot = new SniperRobot(server.getName(), req.getRobot(), shields, shots, position[0], position[1]);
                db.addToRobotList(sniperRobot);
                return new Response("OK", buildData(data, sniperRobot, db), buildState(sniperRobot));
            }else if(Arrays.asList(req.getArguments()).contains("mine")){
                MineRobot mine = new MineRobot(server.getName(), req.getRobot(), shields, shots, position[0], position[1]);
                db.addToRobotList(mine);

                return new Response("OK", buildData(data, mine, db), buildState(mine));
            }
            
            
            Robots robot = new Robots(server.getName(), req.getRobot(), shields, shots, position[0], position[1]);

            db.addToRobotList(robot);

            return new Response("OK", buildData(data, robot, db), buildState(robot));
        } catch (Exception e) {
            throw new RequestErrorException("Could not parse arguments");
        }

    }

    private List<Position>  createPositions(Db db){
        List<Position> positions = new ArrayList<>();
        int width = -Math.floorDiv(db.getWidth(), 2);
        int height = Math.floorDiv(db.getHeight(), 2);

        for(int i = width; i <= Math.abs(width); i++){
            for(int j = height; j >= -height; j--){
                positions.add(new Position(i, j));
            }
        }
        return positions;
    }

    private int[] getSpot(Db db) {
        Random rand = new Random();
        List<Position> allPositions = createPositions(db);

        int[] position = new int[2];
        while (!allPositions.isEmpty()) {
            Position foundPosition = allPositions.get(rand.nextInt(allPositions.size()));
            int proposedX = foundPosition.getX();
            int proposedY = foundPosition.getY();

            if (!checkProposed(new int[] { proposedX, proposedY }, db)) {
                position[0] = proposedX;
                position[1] = proposedY;
                return position;
            }
            allPositions.remove(foundPosition);
        }
        return new int[]{};

    }

    private boolean checkProposed(int[] position, Db db) {
        boolean obsCheck = db.getWorld().checkObstacles(position[0], position[1]).equals(true);
        boolean pitsCheck = db.getWorld().checkPits(position[0], position[1]).equals(true);
        boolean takenRobotCheck = db.robotPositionTaken(position[0], position[1]);
        return !obsCheck || !pitsCheck || takenRobotCheck;
    }


    private Map<String, Object> buildData(Map<String, Object> data, Robots robot, Db db){
        data.put(("position"), robot.getPosition());
        data.put("visibility" , db.getVisibility());
        data.put("reload", db.getReload());
        data.put("repair", db.getRepair());
        data.put("mine", db.getMine());
        data.put("shields", db.getShields());
        return data;
    }

}
