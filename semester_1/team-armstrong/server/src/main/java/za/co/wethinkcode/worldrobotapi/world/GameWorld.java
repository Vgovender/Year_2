package za.co.wethinkcode.worldrobotapi.world;

import lombok.Getter;
import lombok.Setter;
import za.co.wethinkcode.server.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Getter
@Setter
public class GameWorld {
    private List<SinglePointObstacle> obstacleList= new ArrayList<>();
    private List<Robot> robotList;
    private int visibility;
    private int reload;
    private int repair;
    private int mine;
    private int shields;
    private int height;
    private String worldname = "default world";

    private int width;
    private int gun;

    public GameWorld(Map<String, Object> yamlConfigs){
        this.robotList = new ArrayList<>();
        this.visibility = (int) yamlConfigs.get("visibility");
        this.mine = (int) yamlConfigs.get("mine");
        this.reload = (int) yamlConfigs.get("reloadWeapon");
        this.shields = (int) yamlConfigs.get("maxShieldStrength");
        this.gun = 5;
        this.repair = (int) yamlConfigs.get("repairShields");
        this.height = (int) yamlConfigs.get("height");
        this.width = (int) yamlConfigs.get("width");
    }

    public void addToRobotList(Robot robot){
        robotList.add(robot);
    }

    public Robot findOne(String name){
        for (Robot robots : robotList) {
            if (robots.getName().equals(name)) {
                return robots;
            }
        }
        return null;
    }

    public boolean robotPositionTaken(int x, int y){
        for (Robot robot : this.robotList) {
            if (robot.getRealPosition().getX() == x && robot.getRealPosition().getY() == y) {
                return true;
            }
        }
        return false;

    }

    public Boolean checkObstacles(int newObsX,int newObsY){
        for(SinglePointObstacle goThroughList : this.obstacleList){
            int x = goThroughList.getBottomLeftX();
            int y = goThroughList.getBottomLeftY();

            if((newObsX == x && newObsY == y )){
                return false;
            }
        }
        return  true;
    }

    @Override
    public String toString() {
        return "GameWorld{" +
                ", visibility=" + visibility +
                ", height=" + height +
                ", width=" + width +
                '}';
    }
}
