//package za.co.wethinkcode.game_of_life.domain;
//
//public class World {
//    private static int[][] cells;
//    private static String worldName;
//    private static Integer worldSize;
//
//    private World(String worldName, Integer worldSize) {
//        this.worldName = worldName;
//        this.worldSize = worldSize;
//    }
//
//    public static World define(String worldName, Integer worldSize, int[][] worldInitialState) {
//        World world = new World(worldName, worldSize);
//        world.setCells(worldInitialState);
//
//        return world;
//    }
//
//    private void setCells(int[][] cells) {
//        this.cells = cells;
//    }
//
//    public static int[][] getState() {
//        return cells;
//    }
//
//    public static String getWorldName(){
//        return worldName;
//    }
//
//    public static Integer getWorldSize() {
//        return worldSize;
//    }
//}

package za.co.wethinkcode.game_of_life.domain;

public class World {
    private int[][] cells;
    private final String worldName;
    private Integer worldSize;

    private World(String worldName, Integer worldSize) {
        this.worldName = worldName;
        this.worldSize = worldSize;
    }

    public static World define(String worldName, Integer worldSize, int[][] worldInitialState) {
        World world = new World(worldName, worldSize);
        world.setCells(worldInitialState);

        return world;
    }

    private void setCells(int[][] cells) {
        this.cells = cells;
    }

    public int[][] getState() {
        return this.cells;
    }
}