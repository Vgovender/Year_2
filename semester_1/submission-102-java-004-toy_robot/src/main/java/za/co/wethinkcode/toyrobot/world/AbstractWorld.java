package za.co.wethinkcode.toyrobot.world;

import za.co.wethinkcode.toyrobot.world.IWorld.Direction;
import za.co.wethinkcode.toyrobot.Position;
import za.co.wethinkcode.toyrobot.maze.Maze;

import java.util.ArrayList;
import java.util.List;

public class AbstractWorld implements IWorld {
    public Direction currentDirection;
    public Position position;
    public final static Position CENTRE = new Position(0,0);
    public final static Position TOP_LEFT = new Position(-100,200);
    public final static Position BOTTOM_RIGHT = new Position(100,-200);
    public Maze maze;


    public AbstractWorld(Maze maze){
        this.currentDirection=Direction.UP;
        this.position=CENTRE;
        this.maze = maze;
    }

    public IWorld.UpdateResponse updatePositionSprint(int nrSteps) {
        int newX = this.position.getX();
        int newY = this.position.getY();
        if (Direction.UP.equals(this.currentDirection)) {
            newY = newY + nrSteps;
        }else if (Direction.RIGHT.equals(this.currentDirection)) {
            newX = newX + nrSteps;
        }else if (Direction.DOWN.equals(this.currentDirection)) {
            newY = newY - nrSteps;
        }else if (Direction.LEFT.equals(this.currentDirection)) {
            newX = newX - nrSteps;
        }

        Position newPosition = new Position(newX, newY);
        if (newPosition.isIn(TOP_LEFT,BOTTOM_RIGHT)){
            position = newPosition;
            return UpdateResponse.SUCCESS;
        }
        return UpdateResponse.FAILED_OUTSIDE_WORLD;
    }

    @Override
    public UpdateResponse updatePosition(int nrSteps) {
        int newX = position.getX();
        int newY = position.getY();
        //forward
        if (Direction.UP.equals(currentDirection)) {
            newY = newY + nrSteps;
        }else if (Direction.RIGHT.equals(currentDirection)) {
            newX = newX + nrSteps;
        }else if (Direction.DOWN.equals(currentDirection)) {
            newY = newY - nrSteps;
        }else if (Direction.LEFT.equals(currentDirection)) {
            newX = newX - nrSteps;
        }

        Position newPosition = new Position(newX, newY);
        if (newPosition.isIn(TOP_LEFT,BOTTOM_RIGHT) && isNewPositionAllowed(newPosition)){
            position = newPosition;
            return UpdateResponse.SUCCESS;
        }
        return UpdateResponse.FAILED_OUTSIDE_WORLD;
    }

    @Override
    public void updateDirection(boolean turnRight) {
        // Up
        if(currentDirection == Direction.UP && turnRight){
            currentDirection=Direction.RIGHT;
        }
        else if(currentDirection == Direction.UP && !turnRight){
            currentDirection = Direction.LEFT;
        }

        // Right
        else if(currentDirection == Direction.RIGHT && turnRight){
            currentDirection=Direction.DOWN;
        }
        else if(currentDirection == Direction.RIGHT && !turnRight){
            currentDirection = Direction.UP;
        }

        else if(currentDirection == Direction.DOWN && turnRight){
            currentDirection=Direction.LEFT;
        }
        else if(currentDirection == Direction.DOWN && !turnRight){
            currentDirection = Direction.RIGHT;
        }

        // Right
        else if(currentDirection == Direction.LEFT && turnRight){
            currentDirection=Direction.UP;
        }
        else if(currentDirection == Direction.LEFT && !turnRight){
            currentDirection = Direction.DOWN;
        }
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public Direction getCurrentDirection() {
        return this.currentDirection;
    }

    public void turnRight(){
        if (currentDirection == Direction.UP) {
            currentDirection = Direction.RIGHT;
        }else if (currentDirection == Direction.RIGHT) {
            currentDirection = Direction.DOWN;
        }else if (currentDirection == Direction.DOWN) {
            currentDirection = Direction.LEFT;
        }else if (currentDirection == Direction.LEFT) {
            currentDirection = Direction.UP;
        }
    }

    public void turnLeft(){
        if (currentDirection == Direction.UP) {
            currentDirection = Direction.LEFT;
        }else if (currentDirection == Direction.LEFT) {
            currentDirection = Direction.DOWN;
        }else if (currentDirection == Direction.DOWN) {
            currentDirection = Direction.RIGHT;
        }else if (currentDirection == Direction.RIGHT) {
            currentDirection = Direction.UP;
        }
    }

    @Override
    public boolean isNewPositionAllowed(Position position) {
        boolean withinTop = position.getY() <= TOP_LEFT.getY();
        boolean withinBottom = position.getY() >= BOTTOM_RIGHT.getY();
        boolean withinLeft = position.getX() >= TOP_LEFT.getX();
        boolean withinRight = position.getX() <= BOTTOM_RIGHT.getX();
        if (this.maze.blocksPosition(position)) return false;
        if (this.maze.blocksPath(this.position, position)) return false;
        return withinTop && withinBottom && withinLeft && withinRight;
    }

    @Override
    public boolean isAtEdge() {
        if(position.getX() == -100 || position.getX() == 100 ) return true;
        if(position.getY() == -200 || position.getY() == 200 ) return true;
        return false;
    }

    @Override
    public void reset() {
        this.position=CENTRE;
        this.currentDirection = Direction.UP;
    }

    @Override
    public List<Obstacle> getObstacles() {
        return this.maze.getObstacles();
    }

    @Override
    public void showObstacles() {

    }
}
