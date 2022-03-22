package za.co.wethinkcode.worldrobotapi.robotmake;

import za.co.wethinkcode.worldrobotapi.world.Robot;

public class Mine extends Robot {
    public Mine(String name, int shields, int shots, int... positionArray) {
        super(name, shields, 0, positionArray);
    }
}
