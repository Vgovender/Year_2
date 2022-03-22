package za.co.wethinkcode.toyrobot;


import za.co.wethinkcode.toyrobot.world.AbstractWorld;
import za.co.wethinkcode.toyrobot.world.IWorld.Direction;
import za.co.wethinkcode.toyrobot.world.IWorld;

public class Robot<World> {
    private static String status;
    private final String name;
    private AbstractWorld world;

    public Robot(String name, AbstractWorld world) {
        this.name = name;
        status = "Ready";
        this.world = world;
        this.getPosition();
    }

    public void r2(AbstractWorld world){

    }

    public String getStatus() {
        return status;
    }

    public void turnRight(){
        this.world.turnRight();
    }

    public void turnLeft(){
        this.world.turnLeft();
    }


    public IWorld.Direction getCurrentDirection() {
        return this.world.getCurrentDirection();
    }


    public boolean handleCommand(Command command) {
       if (command.getName().equals("right")) {
            turnRight();
       }else if (command.getName().equals("left")) {
            turnLeft();
       }
       return command.execute(this);
     }

     public boolean updatePosition(int nrSteps) {
         if (this.world.updatePosition(nrSteps) == IWorld.UpdateResponse.SUCCESS) return true;
         return false;
     }

    public boolean updatePositionSprint(int nrSteps) {
        if (this.world.updatePositionSprint(nrSteps) == IWorld.UpdateResponse.SUCCESS) return true;
        return false;
    }


    public String toString() {
       return "[" + this.world.getPosition().getX() + "," + this.world.getPosition().getY() + "] "
               + this.name + "> " + status;
    }

    public Position getPosition() {
        return this.world.getPosition();
    }

    public void setStatus(String status) {
        Robot.status = status;
    }

    public String getName() {
        return name;
    }
}