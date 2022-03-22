package za.co.wethinkcode.server.world;

import za.co.wethinkcode.server.Position;

public class Pits {

    private int PitBottomLeftX;
    private int PitBottomLeftY;
    private int hieght;
    private int width;

    public Pits(int pitBottomLeftX,int pitBottomLeftY){
        this.PitBottomLeftY = pitBottomLeftY;
        this.PitBottomLeftX = pitBottomLeftX;
        this.hieght = 15;
        this.width  = 10;
    }
    public int getPitBottomLeftX() {
        return this.PitBottomLeftX;
    }


    public int getPitBottomLeftY() {
        return this.PitBottomLeftY;
    }

    public int getHieght() {
        return this.hieght;
    }

    public int getWidth() {
        return this.width;
    }

    public boolean isPositionBlock(Position position) {
        boolean withinTop = position.getY() <= this.PitBottomLeftY + this.hieght;
        boolean withinBottom = position.getY() >=  this.PitBottomLeftY;
        boolean withinLeft = position.getX() >= this.PitBottomLeftX;
        boolean withinRight = position.getX() <= this.PitBottomLeftX + this.width;
        return withinTop && withinBottom && withinLeft && withinRight;
    }


    public boolean isInsidePit(Position currentPos, Position futurePos) {
        if (currentPos.getX() == futurePos.getX()) {
            if (currentPos.getX() >= getPitBottomLeftX() && currentPos.getX() <= getPitBottomLeftX() + getWidth()) {
                if ((futurePos.getY() > currentPos.getY()) && (currentPos.getY() <= getPitBottomLeftY() + getHieght() && futurePos.getY() >= getPitBottomLeftY())) {
                    return true;
                } else if (futurePos.getY() < currentPos.getY() && (currentPos.getY() <= getPitBottomLeftY() + getHieght()) && futurePos.getY() >= getPitBottomLeftY() + getHieght()) {
                    return true;
                }
            }
        }else if (currentPos.getY() == futurePos.getY()){
            if (currentPos.getY() >= getPitBottomLeftY() && currentPos.getY() <= getPitBottomLeftY() + getHieght()){
                if ((futurePos.getX() > currentPos.getX()) && (currentPos.getX() <= getPitBottomLeftX() + getHieght() && futurePos.getX() >= getPitBottomLeftX())){
                    return true;
                } else if (futurePos.getX() < currentPos.getX() && (currentPos.getX() <= getPitBottomLeftX() + getWidth()) && futurePos.getX() >= getPitBottomLeftX() + getWidth()) {
                    return true;
                }
            }
        }
        return false;
    }
}