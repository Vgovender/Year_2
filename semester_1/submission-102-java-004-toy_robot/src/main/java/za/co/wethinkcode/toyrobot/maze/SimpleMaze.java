package za.co.wethinkcode.toyrobot.maze;

import za.co.wethinkcode.toyrobot.Position;
import za.co.wethinkcode.toyrobot.world.Obstacle;
import za.co.wethinkcode.toyrobot.world.SquareObstacle;

import java.util.ArrayList;
import java.util.List;

public class SimpleMaze implements Maze {

    List<Obstacle> obstacles = new ArrayList<>();

    public SimpleMaze(){
        this.obstacles.add(new SquareObstacle(1, 1));
    }
    /**
     * @return the list of obstacles, or an empty list if no obstacles exist.
     */
    @Override
    public List<Obstacle> getObstacles() {
        return this.obstacles;
    }

    /**
     * Checks if this maze has at least one obstacle that blocks the path that goes from coordinate (x1, y1) to (x2, y2).
     * Since our robot can only move in horizontal or vertical lines (no diagonals yet), we can assume that either x1==x2 or y1==y2.
     *
     * @param a first position
     * @param b second position
     * @return `true` if there is an obstacle is in the way
     */
    @Override
    public boolean blocksPath(Position a, Position b) {
        for (Obstacle obstacle:this.obstacles) {
            if (obstacle.blocksPath(a, b)) return true;
        }
        return false;
    }

    @Override
    public boolean blocksPosition(Position a) {
        for (Obstacle obstacle:this.obstacles) {
            if (obstacle.blocksPosition(a)) return true;
        }
        return false;
    }
}