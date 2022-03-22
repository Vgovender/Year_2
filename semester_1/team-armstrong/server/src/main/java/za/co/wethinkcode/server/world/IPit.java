package za.co.wethinkcode.server.world;

import za.co.wethinkcode.server.Position;

public interface IPit {

    int getPitBottomLeftX();
    int getPitBottomLeftY();
    int getHieght();
    int getWidth();
    boolean isPositionBlock(Position position);
    boolean isInsidePit(Position currentPos,Position futurePos);
}

