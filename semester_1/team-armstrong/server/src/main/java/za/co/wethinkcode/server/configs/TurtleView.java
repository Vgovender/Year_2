//package za.co.wethinkcode.server.configs;
//
//import org.turtle.StdDraw;
//import org.turtle.Turtle;
//import java.awt.Color;
//
//import za.co.wethinkcode.server.Db;
//import za.co.wethinkcode.server.Obstacle;
//import za.co.wethinkcode.server.world.Pits;
//
//public class TurtleView  implements Runnable{
//    private Turtle t;
//    private Db db;
//
//
//
//
//    @Override
//    public void run() {
//        // StdDraw.setCanvasSize(200, 400);
//        try {
//
//            StdDraw.setXscale((db.getWidth() / 2) * -1, db.getWidth());
//            StdDraw.setYscale((db.getHeight() / 2) * -1, db.getHeight());
//            this.t = new Turtle(0, 0, 0);
//            drawObstacles(t);
//            // StdDraw.cl
//        } catch (Exception e) {
//        }
//
//    }
//
//
//
//
//
//
//    /**
//     * Constructor for initializing the world by: - setting the window canvas -
//     * setting the X and Y scales - initializing the turtle
//     *
//     * @param db The database
//     */
//    public TurtleView(Db db) {
//        this.db = db;
//        // super(maze, CENTRE, Direction.UP);
//
//    }
//
//    /**
//    //  * Updates the position of your robot in the world by moving the nrSteps in the
//    //  * robots current direction.
//    //  *
//    //  * @param nrSteps steps to move in current direction
//    //  * @return true if this does not take the robot over the world's limits, or into
//    //  *         an obstacle.
//    //  */
//    // @Override
//    // public void updateDirection(boolean turnRight) {
//    //     if (turnRight) {
//    //         changeDirection(true);
//    //         t.right(90);
//    //     } else {
//    //         changeDirection(false);
//    //         t.left(90);
//    //     }
//    // }
//
//    // @Override
//    // public void showObstacles() {
//    //     drawObstacles(t);
//    // }
//
//    /**
//     * Draws the maze on the turtle world
//     *
//     * @param t the turtle object
//     */
//    private void drawObstacles(Turtle t) {
//        // StdDraw.setPenColor(Color.RED);
//        for (int i = 0; i < db.getWorld().getMyObtaclesList().size(); i++) {
//            draw(t, db.getWorld().getMyObtaclesList().get(i));
//        }
//        // StdDraw.setPenColor(Color.BLACK);
//        for (int i = 0; i < db.getWorld().getMyPitList().size(); i++) {
//            draw(t, db.getWorld().getMyPitList().get(i));
//        }
//        // t.setPosition(getPosition().getX(), getPosition().getY());
//        // t.setAngle(90);
//        // StdDraw.setPenColor(Color.BLUE);
//    }
//
//    /**
//     * Draws a single obstacle on the turtle window
//     *
//     * @param t        turtle object
//     * @param obstacle the obstacle you want to draw
//     */
//    private void draw(Turtle t, Obstacle obstacle) {
//        t.setColor(StdDraw.RED);
//        t.setAngle(0);
//        t.setPosition(obstacle.getBottomLeftX(), obstacle.getBottomLeftY());
//        t.forward(4);
//        t.left(90.0);
//        t.forward(4);
//        t.left(90.0);
//        t.forward(4);
//        t.left(90);
//        t.forward(4);
//        t.left(90.0);
//    }
//
//    private void draw(Turtle t, Pits pit) {
//        t.setColor(StdDraw.BLACK);
//        t.setAngle(0);
//        t.setPosition(pit.getPitBottomLeftX(), pit.getPitBottomLeftY());
//        t.forward(4);
//        t.left(90.0);
//        t.forward(4);
//        t.left(90.0);
//        t.forward(4);
//        t.left(90);
//        t.forward(4);
//        t.left(90.0);
//    }
//
//    // @Override
//    // public void reset() {
//    //     while (!getCurrentDirection().equals(IWorld.Direction.UP)) {
//    //         updateDirection(true);
//    //     }
//    //     setPosition(CENTRE);
//    //     StdDraw.clear();
//    //     setMaze(new EmptyMaze());
//    //     drawObstacles(t);
//    // }
//
//    // /**
//    //  * Get the World type as a string
//    //  *
//    //  * @return a string representing the type of world
//    //  */
//    // @Override
//    // public String getWorldType() {
//    //     return "turtle";
//    // }
//
//    // /**
//    //  * Updates the position of your robot in the world by moving the nrSteps in the
//    //  * robots current direction.
//    //  *
//    //  * @param nrSteps steps to move in current direction
//    //  * @return true if this does not take the robot over the world's limits, or into
//    //  *         an obstacle.
//    //  */
//    // @Override
//    // public UpdateResponse updatePosition(int nrSteps) {
//    //     int newX = getPosition().getX();
//    //     int newY = getPosition().getY();
//
//    //     if (Direction.UP.equals(getCurrentDirection())) {
//    //         newY = newY + nrSteps;
//    //     }
//    //     if (Direction.DOWN.equals(getCurrentDirection())) {
//    //         newY = newY - nrSteps;
//    //     }
//    //     if (Direction.LEFT.equals(getCurrentDirection())) {
//    //         newX = newX - nrSteps;
//    //     }
//
//    //     if (Direction.RIGHT.equals(getCurrentDirection())) {
//    //         newX = newX + nrSteps;
//    //     }
//
//    //     Position newPosition = new Position(newX, newY);
//
//    //     boolean isOk = isNewPositionAllowed(newPosition);
//    //     if (isOk) {
//    //         setPosition(newPosition);
//    //         move(nrSteps);
//    //         return UpdateResponse.SUCCESS;
//    //     }
//
//    //     if (newPosition.isIn(getTopLeftPosition(), getBottomRightPosition())) {
//    //         return UpdateResponse.FAILED_OBSTRUCTED;
//    //     }
//
//    //     return UpdateResponse.FAILED_OUTSIDE_WORLD;
//    // }
//
//    // /**
//    //  * Move the turtle a certain number of steps forward
//    //  *
//    //  * @param n number of steps to move forward by
//    //  */
//    // private void goForward(int n) {
//    //     t.forward(n);
//    // }
//
//    // /**
//    //  * Move the turtle a certain number of steps backwards
//    //  *
//    //  * @param n number of steps to move backwards by
//    //  */
//    // private void goBack(int n) {
//    //     t.backward(n);
//    // }
//
//    // /**
//    //  * moves the robot back or forward a certain number of steps
//    //  *
//    //  * @param n the number of steps robot should move in the world (if the number of
//    //  *          steps is negative, it will move backwards)
//    //  */
//    // public void move(int n) {
//    //     String type = "forward";
//    //     if (n < 0) {
//    //         type = "back";
//    //         n = n * -1;
//    //     }
//    //     switch (type) {
//    //     case "forward":
//    //         goForward(n);
//    //         break;
//    //     case "back":
//    //         goBack(n);
//
//    //     }
//    // }
//
//}
