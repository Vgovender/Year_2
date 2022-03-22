package za.co.wethinkcode.worldrobotapi.utils;

import za.co.wethinkcode.server.Status;
import za.co.wethinkcode.server.UpdateResponse;
import za.co.wethinkcode.worldrobotapi.world.Robot;


import java.util.HashMap;
import java.util.Map;

public class MoveHelper {
    private static final String MESSAGE = "message";
    public MoveHelper() {
    }

    public static Map<String, Object> checkUpdateResponse(Robot robot, UpdateResponse updated){
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
        return data;
    }
}
