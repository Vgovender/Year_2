package za.co.wethinkcode.toyrobot.world;

import org.turtle.StdDraw;
import org.turtle.Turtle;
import za.co.wethinkcode.toyrobot.Position;
import za.co.wethinkcode.toyrobot.maze.Maze;

public class TurtleWorld extends AbstractWorld{
    private Turtle turtle;
    public TurtleWorld(Maze maze) {
        super(maze);
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(-100, 100);
        StdDraw.setYscale(-200, 200);
        this.turtle = new Turtle(0, 0, 90);
    }
    @Override
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
        this.turtle.right(90);
    }
    @Override
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
        this.turtle.left(90);
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
            this.turtle.forward(nrSteps);
            return UpdateResponse.SUCCESS;
        }
        return UpdateResponse.FAILED_OUTSIDE_WORLD;
    }
}
