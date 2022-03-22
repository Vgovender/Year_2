package za.co.wethinkcode.worldrobotapi.world;

import za.co.wethinkcode.server.Mine;
import za.co.wethinkcode.server.Obstacle;
import za.co.wethinkcode.server.Robots;
import za.co.wethinkcode.server.world.Pits;

import java.util.List;

public interface WorldInterface {
    /**
     * @return the list of obstacles, or an empty list if no obstacles exist.
     */
    List<Obstacle> getObstacles();

    /**
     * @return the list of robots, or an empty list if no robots exits.
     */
    List<Robots> getRobot();

    /**
     * @return the list of pits, or an empty list if no pits exist.
     */
    List<Pits> getPits();

    /**
     * @return the list of mines, or empty list if no mines exist.
     */

    List<Mine> getMines();
    /**
     * @return the list of obstacles, or an empty list if no obstacles exist.
     */

    /**
     * @deprecated
     * Gives opportunity to world to draw or list obstacles.
     */
    void showObstacles();
}
