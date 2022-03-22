package za.co.wethinkcode.server.configs;

import java.util.Arrays;

import za.co.wethinkcode.server.Db;
import za.co.wethinkcode.server.MultiServer;

public class ListCommand extends ServerCommands {

    @Override
    public boolean runCommand() {
        Db db = MultiServer.dataBase.get(0);

        boolean wentIn = false;
        for (int i = 0; i < db.getRobotList().size(); i++) {
            System.out.println("" + (i + 1) + ". Robot : " + db.getRobotList().get(i).getName());
            System.out.println("        Position  : " + Arrays.toString(db.getRobotList().get(i).getPosition()));
            System.out.println("        Direction : " + db.getRobotList().get(i).getCurrentDirection().name());
            System.out.println("        Shields   : " + db.getRobotList().get(i).getShields());
            System.out.println("        Shots     : " + db.getRobotList().get(i).getShots());
            System.out.println("        Status    : " + db.getRobotList().get(i).getState().name());
            wentIn = true;
        }

        if (!wentIn) {
            System.out.println("No Robots to show.");
        }
        return true;
    }

}
