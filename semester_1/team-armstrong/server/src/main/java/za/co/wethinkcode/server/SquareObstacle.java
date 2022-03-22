package za.co.wethinkcode.server;

public class SquareObstacle implements Obstacle{
    
    private int xCoordinate;
    private int yCoordinate;
    private int size;

    /**
     * Constructor for the Obstacle that sets the x and y values
     * @param x The X value of the obstacle
     * @param y The Y value of the obstacle
     */
    public SquareObstacle(int x, int y) {
        this.xCoordinate = x;
        this.yCoordinate = y;
        size = 5;
    }

    @Override
    public boolean blocksPosition(Position position) {
        if (position.getX() >= this.xCoordinate && position.getX() <= this.xCoordinate + 4
                && position.getY() >= yCoordinate && position.getY() <= yCoordinate + 4) {
            return true;
        }
        return false;
    }

    @Override
    public boolean blocksPath(Position a, Position b) {
        if (a.getX() == b.getX()) {
            if (a.getY() <= b.getY()) {
                for (int z = 0; z < Math.abs(b.getY() - a.getY()); z++) {
                    if (ifInObstacle(a.getX(), a.getY() + z)) {
                        return true;
                    }
                }
            } else {
                for (int z = 0; z < Math.abs(a.getY() - b.getY()); z++) {
                    if (ifInObstacle(a.getX(), b.getY() + z)) {
                        return true;
                    }
                }
            }
        }
        if (a.getY() == b.getY()) {
            if (a.getX() <= b.getX()) {
                for (int z = 0; z < Math.abs(b.getX() - a.getX()); z++) {
                    if (ifInObstacle(a.getX() + z, a.getY())) {
                        return true;
                    }
                }
            } else {
                for (int z = 0; z < Math.abs(a.getX() - b.getX()); z++) {
                    if (ifInObstacle(b.getX() + z, a.getY())) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    @Override
    public int getBottomLeftX() {
        return xCoordinate;
    }

    /**
     * Get Y coordinate of bottom left corner of obstacle.
     * 
     * @return y coordinate
     */
    @Override
    public int getBottomLeftY() {
        return yCoordinate;
    }

    @Override
    public int getSize() {
        return size;
    }
    
    /**
     * Checks if a point defined by x and y is on the obstacle 
     * @param x The X value of the point
     * @param y The Y value of the point
     * @return  'true' if the point is on the obstacle 
     */
    public boolean ifInObstacle(int x, int y) {
        boolean withinTop = y <= yCoordinate + 4;
        boolean withinBottom = y >= yCoordinate;
        boolean withinLeft = x >= xCoordinate;
        boolean withinRight = x <= xCoordinate + 4;
        return withinTop && withinBottom && withinLeft && withinRight;
    }

    @Override
    public String toString() {
        return "SquareObstacle{" +
                "xCoordinate=" + xCoordinate +
                ", yCoordinate=" + yCoordinate +
                ", size=" + size +
                '}';
    }
}
