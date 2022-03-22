package za.co.wethinkcode.worldrobotapi.world;

import za.co.wethinkcode.server.*;
import za.co.wethinkcode.worldrobotapi.worldapi.RobotApi;

public class Robot {
    private String name;
    private Status status;
    private Position position;
    private Direction currentDirection;
    private int shots;
    private int shields;
    private int maxReload;

    public Robot(String name, int shields, int shots, int... positionArray) {
        this.name = name;
        this.status = Status.NORMAL;
        this.shields = shields;
        this.shots = shots;
        this.maxReload = this.shots;
        this.position = new Position(positionArray[0], positionArray[1]);
        this.currentDirection = Direction.NORTH;
    }


    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the state
     */
    public Status getState() {
        return this.status;
    }

    public int getRepair() {
        return this.shields+=1;
    }

    /**
     * @return the position
     */
    public int[] getPosition() {
        return new int[] { this.position.getX(), this.position.getY() };
    }

    /**
     * @return the direction
     */
    public Direction getDirection() {
        return this.currentDirection;
    }



    /**
     * Changes current direction to the direction on the left of it
     */
    private void turnLeft() {
        // Turn the robot left
        if (Direction.NORTH.equals(this.currentDirection)) {
            this.currentDirection = Direction.WEST;
        } else if (Direction.SOUTH.equals(this.currentDirection)) {
            this.currentDirection = Direction.EAST;
        } else if (Direction.WEST.equals(this.currentDirection)) {
            this.currentDirection = Direction.SOUTH;
        } else {
            this.currentDirection = Direction.NORTH;
        }
    }

    /**
     * Changes current direction to the direction on the right of it
     */
    private void turnRight() {
        // Turn the robot right
        if (Direction.NORTH.equals(this.currentDirection)) {
            this.currentDirection = Direction.EAST;
        } else if (Direction.SOUTH.equals(this.currentDirection)) {
            this.currentDirection = Direction.WEST;
        } else if (Direction.WEST.equals(this.currentDirection)) {
            this.currentDirection = Direction.NORTH;
        } else {
            this.currentDirection = Direction.SOUTH;
        }
    }

    /**
     * Change the direction of the world either to the left or right
     *
     * @param turnRight if 'true' it will turn the world direction to the right else
     *                  the left
     */
    public void changeDirection(boolean turnRight) {
        if (turnRight)
            turnRight();
        else
            turnLeft();
    }

    public UpdateResponse updatePosition(int nrSteps) {
        int newX = this.position.getX();
        int newY = this.position.getY();

        if (Direction.NORTH.equals(this.currentDirection)) {
            newY = newY + nrSteps;
        }
        if (Direction.SOUTH.equals(this.currentDirection)) {
            newY = newY - nrSteps;
        }
        if (Direction.WEST.equals(this.currentDirection)) {
            newX = newX - nrSteps;
        }

        if (Direction.EAST.equals(this.currentDirection)) {
            newX = newX + nrSteps;
        }

        Position newPosition = new Position(newX, newY);
        UpdateResponse isOk = isNewPositionAllowed(newPosition);


        if (isOk.equals(UpdateResponse.SUCCESS)) {
            this.position = newPosition;
            // return UpdateResponse.SUCCESS;
        }

        // if (newPosition.isIn(TOP_LEFT, BOTTOM_RIGHT)) {
        // return UpdateResponse.FAILED_OBSTRUCTED;
        // }

        return isOk;
    }

    public UpdateResponse isNewPositionAllowed(Position position) {
        for (int i = 0; i < RobotApi.gameWorld.getObstacleList().size(); i++) {
            if ( RobotApi.gameWorld.getObstacleList().get(i).blocksPosition(position) ) {
                return UpdateResponse.FAILED_OBSTRUCTED;
            }
        }

        for(int i = 0; i < RobotApi.gameWorld.getRobotList().size(); i++) {
            if (!RobotApi.gameWorld.getRobotList().get(i).getName().equals(this.name)) {
                if (position.equals(RobotApi.gameWorld.getRobotList().get(i).getRealPosition())) {
                    return UpdateResponse.FAILED_OBSTRUCTED;
                }
            }
        }
        // boolean isInResponse = position.isIn(TOP_LEFT, BOTTOM_RIGHT);
        // if (!isInResponse) {
        // return false;
        // }
        if (Math.abs(position.getY()) > Math.floorDiv(RobotApi.gameWorld.getHeight(), 2) || Math.abs(position.getX()) > Math.floorDiv(RobotApi.gameWorld.getHeight(), 2)){
            return UpdateResponse.ON_EDGE;
        }
        return UpdateResponse.SUCCESS;
    }


    /**
     * @return the shields
     */
    public int getShields() {
        if (shields > 5)
        {
            return shields = 5;
        }
        else
        {
            return shields;
        }

    }

    /**
     * @return the shots
     */
    public int getShots() {
        if (shots > 5)
        {
            return shots = 5;
        }
        else
        {
            return shots;
        }

    }

    public void mineDamage(){
        this.shields-=3;
    }

    public void damage(){
        this.shields-=1;
    }


    /**
     * @return the currentDirection
     */
    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public Position getRealPosition(){
        return this.position;
    }

    public Status updateStatus()
    {

        if(Status.NORMAL.equals(this.status))
        {
            this.status = Status.REPAIRING;
        }
        if(Status.REPAIRING.equals(this.status))
        {
            this.status = Status.NORMAL;
        }
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    public void fire(){
        this.shots -= 1;
    }

    public void reloadGun(){
        this.shots = this.maxReload;
    }

    @Override
    public String toString() {
        return "Robots{" +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", position=" + position +
                ", currentDirection=" + currentDirection +
                ", shots=" + shots +
                ", shields=" + shields +
                ", maxReload=" + maxReload +
                '}';
    }
}
