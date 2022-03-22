package za.co.wethinkcode.server.commands;

import za.co.wethinkcode.server.Db;
import za.co.wethinkcode.server.Robots;
import za.co.wethinkcode.server.Status;
import za.co.wethinkcode.server.UpdateResponse;

import java.util.HashMap;
import java.util.Map;

public class MoveUtil {
    private static final String MESSAGE = "message";
    public MoveUtil() {
    }

    public static Map<String, Object> checkUpdateResponse(Db db, Robots robot, UpdateResponse updated){
        Map<String, Object> data = new HashMap<>();
        if (updated.equals(UpdateResponse.SUCCESS)) {
            robot.setStatus(Status.NORMAL);
            data.put(MESSAGE, "Done");
        } else if (updated.equals(UpdateResponse.FAILED_OBSTRUCTED)) {
            data.put(MESSAGE, "Obstructed");
            robot.setStatus(Status.NORMAL);

        }else if(updated.equals(UpdateResponse.ON_EDGE)){
            data.put(MESSAGE, "At the "+ robot.getCurrentDirection() +" edge");
            robot.setStatus(Status.NORMAL);
        }
        else if (updated.equals(UpdateResponse.KILLED_BY_A_PIT)){
            data.put(MESSAGE, "Fell");
            robot.setStatus(Status.DEAD);
            db.KillRobot(robot);

        }else if(updated.equals(UpdateResponse.ON_MINE)){
            data.put(MESSAGE, "Mine");
            if(robot.getShields() <= 0){

                robot.setStatus(Status.DEAD);
            }
            else{
                robot.damage();
                robot.setStatus(Status.NORMAL);
            }
        }
        return data;
    }
}
