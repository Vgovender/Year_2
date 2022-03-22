package za.co.wethinkcode.server.configs;

import java.util.ArrayList;

import za.co.wethinkcode.server.*;

import java.util.*;


public class GameState {
    public List<Map<String, Object>> robotList = new ArrayList<>();
    // Map<String, Object>

    public GameState (){}
        
    /**
     * @return the robotList
     */
    public List<Map<String, Object>> getRobotList() {
        return robotList;
    }

    /**
     * @param robotList the robotList to set
     */
    public void setRobotList(List<Map<String, Object>> robotList) {
        this.robotList = robotList;
    }

}
