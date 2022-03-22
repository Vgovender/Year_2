package za.co.wethinkcode.server;

import za.co.wethinkcode.server.Position;

public class Mine implements IMine {
    private int xCoordinate;
    private int yCoordinate;
    private int size;

    public Mine(int x, int y) {
        this.xCoordinate = x;
        this.yCoordinate = y;
        size = 5;

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

    @Override
    public boolean stepsOnMine(Position position){
        if (position.getX() >= this.xCoordinate && position.getX() <= this.xCoordinate + 4
                && position.getY() >= yCoordinate && position.getY() <= yCoordinate + 4) {
            return true;
        }
        return false;
    }

    @Override
    public boolean PathThrewMine(Position a, Position b) {
        if (a.getX() == b.getX()) {
            if (a.getY() <= b.getY()) {
                for (int z = 0; z < Math.abs(b.getY() - a.getY()); z++) {
                    if (ifInMine(a.getX(), a.getY() + z)) {
                        return true;
                    }
                }
            } else {
                for (int z = 0; z < Math.abs(a.getY() - b.getY()); z++) {
                    if (ifInMine(a.getX(), b.getY() + z)) {
                        return true;
                    }
                }
            }
        }
        if (a.getY() == b.getY()) {
            if (a.getX() <= b.getX()) {
                for (int z = 0; z < Math.abs(b.getX() - a.getX()); z++) {
                    if (ifInMine(a.getX() + z, a.getY())) {
                        return true;
                    }
                }
            } else {
                for (int z = 0; z < Math.abs(a.getX() - b.getX()); z++) {
                    if (ifInMine(b.getX() + z, a.getY())) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    public boolean ifInMine(int x, int y) {
        boolean withinTop = y <= yCoordinate + 4;
        boolean withinBottom = y >= yCoordinate;
        boolean withinLeft = x >= xCoordinate;
        boolean withinRight = x <= xCoordinate + 4;
        return withinTop && withinBottom && withinLeft && withinRight;
    }

    @Override
    public String toString() {
        return "Mine{" +
                "xCoordinate=" + xCoordinate +
                ", yCoordinate=" + yCoordinate +
                ", size=" + size +
                '}';
    }
}
