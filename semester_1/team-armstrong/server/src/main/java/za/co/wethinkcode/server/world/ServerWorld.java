package za.co.wethinkcode.server.world;
import za.co.wethinkcode.server.*;
import java.util.*;
import java.util.logging.Logger;

public class ServerWorld {
    private static final Logger LOGGER = Logger.getLogger(ServerWorld.class.getName());
    public static List<Pits> myPitList = new ArrayList<>();
    public static List<SinglePointObstacle> myObticlesList= new ArrayList<>();
    public static List<Mine> minesList = new ArrayList<>();
    private Random genRandomPnts = new Random();




    public ServerWorld(Map<String, String> listOfArgs, int x, int y){

        obstacleGenarator(listOfArgs);
        //pitsGenerator(x,y);
        showObstacles();
        //showPits();
    }

    public static List<Pits> getMyPitList() {
        return myPitList;
    }

    public static List<SinglePointObstacle> getMyObtaclesList(){
        return myObticlesList;
    }

    public static List<Mine> getMyMinesList(){return minesList;}




    public void obstacleGenarator(Map<String, String> listOfArgs){
        if (!listOfArgs.isEmpty()) {
            String obs = listOfArgs.get("obstacle");
            String[] obstacleList = obs == null ? new String[]{} : obs.split(",");
            boolean checker = true;
            for (String s : obstacleList) {
                if (!s.matches("[0-9]+")) {
                    checker = false;
                    break;
                }
            }

            if (checker && obstacleList.length == 2) {
                this.myObticlesList.add(new SinglePointObstacle(Integer.parseInt(obstacleList[0]), Integer.parseInt(obstacleList[1])));
            }
        }

    }

    public void pitsGenerator(int serverX, int serverY){
        int count = 0;
        while(count != 10) {
            int myX = this.genRandomPnts.nextInt(serverX)+(-serverX/2);
            int myY = this.genRandomPnts.nextInt(serverY)+(-serverY/2);

            this.myPitList.add(new Pits(myX, myY));
            count++;
        }
    }
    
    
    public Boolean checkObstacles(int newObsX,int newObsY){
        for(SinglePointObstacle goThroughList : this.getMyObtaclesList()){
            int x = goThroughList.getBottomLeftX();
            int y = goThroughList.getBottomLeftY();

            if((newObsX == x && newObsY == y )){
                    return false;
            }
        }
        return  true;
    }

    public Boolean checkMines(int newObsX,int newObsY){
        for(Mine goThroughList : this.getMyMinesList()){
            int x = goThroughList.getBottomLeftX();
            int y = goThroughList.getBottomLeftY();

            if((newObsX >= x && newObsX <= x+5 || newObsX+5>=100)&&(newObsY >= y && newObsY <= y+5)){
                    return false;

            }else if((newObsY >= y && newObsY <= y+5 || newObsY+5>=185) &&(newObsX >= x && newObsX <= x+5)){
                    return false;
            }
        }
        return  true;
    }

    public Boolean checkPits(int newObsX,int newObsY){
        for(Pits goThroughList : this.getMyPitList()){
            int x = goThroughList.getPitBottomLeftX();
            int y = goThroughList.getPitBottomLeftY();

            if((newObsX >= x && newObsX <= x+10 || newObsX+10>100) &&(newObsY > y && newObsY < y+15)){
                    return false;
            }else if((newObsY >= y && newObsY <= y+15 || newObsY + 15>=185)
                && (newObsX >= x && newObsX <= x+10 || newObsX + 10 >= 100)){
                    return false;
            }
        }
        return  true;
    }

    public void showObstacles() {
        LOGGER.info("There are some obstacles:");
        List<SinglePointObstacle> myObstacles = this.getMyObtaclesList();
        for (Obstacle obj : myObstacles) {
            LOGGER.info("- At position " + obj.getBottomLeftX() + "," + obj.getBottomLeftY());
        }
    }
    public void showPits() {
        LOGGER.info("There are some pits:");
        List<Pits> myPits = this.getMyPitList();
        for (Pits pits : myPits) {
            LOGGER.info("- At position " + pits.getPitBottomLeftX() + "," + pits.getPitBottomLeftY() + " (to " + (pits.getPitBottomLeftX() + 10) + "," + (pits.getPitBottomLeftY() + 15) + ")");
        }
    }

    @Override
    public String toString() {
        return "ServerWorld{" +
                "myPitList=" + myPitList +
                ", myObticlesList=" + myObticlesList +
                ", minesList=" + minesList +
                ", genRandomPnts=" + genRandomPnts +
                '}';
    }
}
