package za.co.wethinkcode.toyrobot.world;

import za.co.wethinkcode.toyrobot.Position;

public class SquareObstacle implements Obstacle {
    private int x;
    private int y;
    private int size = 5;

    public SquareObstacle(int x, int y) {
        this.x = x;
        this.y = y;

    }

    @Override
    public int getBottomLeftX() {
        return x;
    }

    @Override
    public int getBottomLeftY() {
        return y;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean blocksPosition(Position position) {
        if(position.isIn(new Position(this.x,this.y+4),new Position(this.x+4,this.y))){
            return true;
        }return false;
        //        int x1, x2, y1, y2;
//        x1 = position.getX();
//        x2 = this.x;
//        y1 = position.getY();
//        y2 = this.y;
//        return (x1 >= x2 && x1 <= x2 + 4 && y1 >= y2 && y1 <= y2 + 4);
    }

    @Override
    public boolean blocksPath(Position a, Position b) {
        if (a.getX() == b.getX()) {
            if (a.getY() < b.getY()) {
                for (int i = a.getY(); i <= b.getY(); i++) {
                    if (blocksPosition(new Position(a.getX(), i))) {
                        return true;
                    }
                }
            } else {
                for (int i = b.getY(); i <= a.getY(); i++) {
                    if (blocksPosition(new Position(a.getX(), i))) {
                        return true;
                    }
                }
            }
        } else {
            if (a.getX() < b.getX()) {
                for (int i = a.getX(); i <= b.getX(); i++) {
                    if (blocksPosition(new Position(i, a.getY()))) {
                        return true;
                    }
                }
            } else {
                for (int i = b.getX(); i <= a.getX(); i++) {
                    if (blocksPosition(new Position(i, a.getX()))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
