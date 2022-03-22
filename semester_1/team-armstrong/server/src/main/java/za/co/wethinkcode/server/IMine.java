package za.co.wethinkcode.server;

import za.co.wethinkcode.server.Position;

import za.co.wethinkcode.server.Position;

public interface IMine  {
    /**
     * Get X coordinate of bottom left corner of obstacle.
     * @return x coordinate
     */
    int getBottomLeftX();

    /**
     * Get Y coordinate of bottom left corner of obstacle.
     * @return y coordinate
     */
    int getBottomLeftY();

    /**
     * Gets the side of an obstacle (assuming square obstacles)
     * @return the length of one side in nr of steps
     */
    int getSize();

    boolean stepsOnMine(Position position);

    boolean PathThrewMine(Position a, Position b);

}
