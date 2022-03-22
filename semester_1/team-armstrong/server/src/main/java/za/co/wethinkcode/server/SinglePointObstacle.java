package za.co.wethinkcode.server;

public class SinglePointObstacle implements Obstacle{
    private int xCoordinate;
    private int yCoordinate;
    private int size;


    /**
     * Constructor for the Obstacle that sets the x and y values
     * @param xCoordinate The X value of the obstacle
     * @param yCoordinate The Y value of the obstacle
     */
    public SinglePointObstacle(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.size = 0;
    }

    @Override
    public int getBottomLeftX() {
        return xCoordinate;
    }

    @Override
    public int getBottomLeftY() {
        return yCoordinate;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean blocksPosition(Position position) {
        return position.getX() == this.xCoordinate && position.getY() == yCoordinate;
    }

    @Override
    public boolean blocksPath(Position a, Position b) {
        return false;
    }

    @Override
    public String toString() {
        return "SinglePointObstacle{" +
                "xCoordinate=" + xCoordinate +
                ", yCoordinate=" + yCoordinate +
                ", size=" + size +
                '}';
    }
}
