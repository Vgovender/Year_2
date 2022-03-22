package za.co.wethinkcode.server;

import java.io.*;
// import java.time.Year;
// import java.util.HashMap;
import java.util.*;

//import za.co.wethinkcode.database.Restore;

// import com.hexagonkt.serialization.Yaml;

// import org.yaml.snakeyaml.*;
// import org.yaml.snakeyaml.*;
// import za.co.wethinkcode.*;

import za.co.wethinkcode.server.world.Pits;
// import za.co.wethinkcode.server.commands.*;
import za.co.wethinkcode.server.world.ServerWorld;

public class Db {

    private List<Robots> robotList = new ArrayList<>();
    private Map<Position, Position> emptySpots;
    public static List<Mine> mineList = new ArrayList<>();
    private ServerWorld world;
    public static int visibility;
    private int reload;
    private int repair;
    private int mine;
    private int shields;
    public static int height;
    private int width;
    private int gun;


    public Db(ServerWorld serverWolrd, Map<String, Object> yamlConfigs) throws FileNotFoundException {
        this.visibility = (int) yamlConfigs.get("visibility");
        this.height = (int) yamlConfigs.get("height");
        this.mine = (int) yamlConfigs.get("mine");
        this.reload = (int) yamlConfigs.get("reloadWeapon");
        this.shields = (int) yamlConfigs.get("maxShieldStrength");
        this.gun = 5;
        this.repair = (int) yamlConfigs.get("repairShields");
        this.width = (int) yamlConfigs.get("width");
        this.world = serverWolrd;
    }


    /**
     * @return the robotList
     */
    public List<Robots> getRobotList() {
        return robotList;
    }

    /**
     * @return the world
     */
    public ServerWorld getWorld() {
        return world;
    }

    public List<SinglePointObstacle> getObstacleList(){
        
        return this.world.getMyObtaclesList();
    }

    public List<Pits> getPitsList(){

        return this.world.getMyPitList();
    }

    public void addToRobotList(Robots robot){
        robotList.add(robot);
    }

    public Robots findOne(String name){
        for(int i = 0; i < robotList.size(); i++){
            if(robotList.get(i).getName().equals(name)){
                return robotList.get(i);
            }
        }
        return null;
    }

    public void KillRobot(Robots robot){
        robotList.remove(robot);
    }

    public void updateRobot(Robots updatedRobot){
        for(int i = 0; i < robotList.size(); i++){
            if(robotList.get(i).getName().equals(updatedRobot.getName())){
                robotList.set(i, updatedRobot);
                break;
            }
        }


    }
    /**
     * @return the visibility
     */
    public static int getVisibility() {
        return visibility;
    }

    /**
     * @return the reload
     */
    public int getReload() {
        return reload;
    }

    /**
     * @return the mine
     */
    public int getMine() {
        return mine;
    }

    /**
     * @return the shields
     */
    public int getShields() {
        return shields;
    }
    /** 
     * @return reloaded gun with one 
    */
    public int getGun()
    {
        return gun;
    }

    /**
     * @return the repair
     */
    public int getRepair() {
        return repair;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    // private  Map<Position, Position> createEmptySpots(){
    //     List<Position> listMade = new ArrayList<>();
    //     Map<Position, Position> dictionary = new HashMap<>();
    //     // this.maze = maze;
    //     // puts all the points that will be used
    //     for (int i = -100; i <= 100; i++) {
    //         for (int v = -200; v <= 200; v++) {
    //             Position position = new Position(i, v);
    //             listMade.add(position);

    //             dictionary.put(position, position);
    //             // f++;
    //         }
    //     }
    //     for (int i = 0; i < world.getObstacles().size(); i++) {
    //         Position pos = new Position(world.getObstacles().get(i).getBottomLeftX(),
    //                 world.getObstacles().get(i).getBottomLeftY());

    //         if (listMade.contains(pos)) {

    //             for (int v = 0; v < 5; v++) {
    //                 for (int b = 0; b < 5; b++) {
    //                     if (world.getObstacles().get(i).blocksPosition(new Position(pos.getX() + v, pos.getY() + b)))
    //                         ;
    //                     {
    //                         try {
    //                             listMade.remove(listMade.indexOf(new Position(pos.getX() + v, pos.getY() + b)));
    //                             dictionary.remove(new Position(pos.getX() + v, pos.getY() + b));
    //                         } catch (Exception e) {
    //                         }

    //                     }
    //                 }
    //             }
    //         }
    //     }
    //     return dictionary;
    // }

    /**
     * @return the emptySpots
     */
    public Map<Position, Position> getEmptySpots() {
        return emptySpots;
    }

    /**
     * @return the height
     */
    public static int getHeight() {
        return height;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    public void addMine(Mine mine){
        mineList.add(mine);
    }

    public void removeMine(Mine mine){
        for(int i = 0; i < mineList.size(); i++){
            if(mine.getBottomLeftX() == mineList.get(i).getBottomLeftX() && mine.getBottomLeftY() == mineList.get(i).getBottomLeftY()){
                mineList.remove(i);
            }
        }

        mineList.remove(mine);
    }

    public boolean robotPositionTaken(int x, int y){
        for(int i = 0; i < this.robotList.size(); i++){
            Robots robot = this.robotList.get(i);
            if(robot.getRealPosition().getX() == x && robot.getRealPosition().getY() == y){
                return true;
            }
        }
        return false;

    }

    @Override
    public String toString() {
        return "Db{" +
                "robotList=" + robotList +
                ", emptySpots=" + emptySpots +
                ", mineList=" + mineList +
                ", world=" + world +
                ", visibility=" + visibility +
                ", reload=" + reload +
                ", repair=" + repair +
                ", mine=" + mine +
                ", shields=" + shields +
                ", height=" + height +
                ", width=" + width +
                ", gun=" + gun +
                '}';
    }
}
