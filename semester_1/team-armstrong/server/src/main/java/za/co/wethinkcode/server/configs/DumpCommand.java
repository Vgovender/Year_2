package za.co.wethinkcode.server.configs;
// whats missing???
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import za.co.wethinkcode.YAMLHandler;
import za.co.wethinkcode.server.Db;
// import za.co.wethinkcode.server.commands.Command;
import za.co.wethinkcode.server.MultiServer;
import za.co.wethinkcode.server.Robots;

public class DumpCommand extends ServerCommands {
    public String robotNmae;
    public File myFile;

    public DumpCommand() {
        try {
            File myFile = new File("GameState.yml");
            if (!myFile.exists() && !myFile.isDirectory() && myFile.createNewFile()) {
                System.out.println("File created:       " + myFile.getName());
                System.out.println("File Absolute Path: " + myFile.getAbsolutePath());
            }
            this.myFile = myFile;
        } catch (Exception e) {
            System.out.println("An error occurred while creating Database file.");
        }
    }

    @Override
    public boolean runCommand() {
        Db db = MultiServer.dataBase.get(0);
        GameState gs = new GameState();
        gs.setRobotList(createRobotList(db.getRobotList()));

        YAMLHandler.writeGameState(gs, myFile);

        return true;
    }

    private Map<String, Object> buildState(Robots robot) {
        Map<String, Object> state = new HashMap<>();
        state.put("RobotName", robot.getName());
        state.put("position", robot.getPosition());
        state.put("direction", robot.getCurrentDirection().name());
        state.put("shields", robot.getShields());
        state.put("shots", robot.getShots());
        state.put("status", robot.getState().name());

        return state;

    }

    private List<Map<String, Object>> createRobotList(List<Robots> robotList) {
        List<Map<String, Object>> finalList = new ArrayList<>();
        for (int i = 0; i < robotList.size(); i++) {

            finalList.add(buildState(robotList.get(i)));
        }
        return finalList;
    }

}
