package za.co.wethinkcode.server.configs;

import za.co.wethinkcode.server.*;

public class PurgeCommand extends ServerCommands {
    public String robotNmae;

    public PurgeCommand(String robotName) {
        this.robotNmae = robotName;
    }

    @Override
    public boolean runCommand() {
        Db db = MultiServer.dataBase.get(0);
        boolean wentIn = false;
        boolean foundRobot = false;
        for (int i = 0; i < db.getRobotList().size(); i++) {
            wentIn = true;
            if (db.getRobotList().get(i).getName().equals(robotNmae)) {
                db.getRobotList().remove(db.getRobotList().get(i));
                foundRobot = true;
            }
        }

        if (!wentIn) {
            System.out.println("No Robots to show.");
        }

        if (!foundRobot) {
            System.out.println("Robot not found.");
        }
        return true;
    }

}
