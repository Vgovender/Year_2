package za.co.wethinkcode.server.commands;

// import java.util.ArrayList;
import java.util.HashMap;
// import java.util.List;

import za.co.wethinkcode.communication.*;
import za.co.wethinkcode.server.*;

public class CommandSetMine extends Command {

    private final Request clientRequest;

    public CommandSetMine(Request request) {
        this.clientRequest = request;
    }

    // public List<Mine> mineList = new ArrayList<>();


    // private Position position;



    public Mine MineGenerator(Position position){
        int X = position.getX();
        int Y = position.getY();
        return new Mine(X, Y);
    }

        @Override
        public Response execute() throws RequestErrorException {

            try {
                // Getting Database
                Db db = MultiServer.dataBase.get(0);

                // Get robot
                Robots robot = db.findOne(clientRequest.getRobot());

                // Make changes to robot
               
                db.addMine( MineGenerator(robot.getRealPosition()));
                UpdateResponse updated = robot.updatePosition(db, 5);
                if (updated.equals(UpdateResponse.SUCCESS)) {
                    db.updateRobot(robot);
                }




                //putDatabase(db);
                return new Response("OK", new HashMap<>(), buildState(robot));
            } catch (Exception e) {
                throw new RequestErrorException("Could not parse arguments");
            }
        }
}

