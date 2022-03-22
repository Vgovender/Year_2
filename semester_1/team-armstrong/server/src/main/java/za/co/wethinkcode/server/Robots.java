package za.co.wethinkcode.server;

public class Robots {
    private static String serverThreadName;
    private static String name;
    private static Status status;
    private static Position position;
    private static Direction currentDirection;
    public static int mine;
    private static int shots;
    private static int shields;
    private int maxReload;

    public Robots(String serverThread,String name, int shields, int shots, int... positionArray) {
        this.serverThreadName = serverThread;
        this.name = name;
        this.status = Status.NORMAL;
        this.shields = shields;
        this.shots = shots;

        this.maxReload = this.shots;
        this.position = new Position(positionArray[0], positionArray[1]);
        this.currentDirection = Direction.NORTH;
    }


    /**
     * @return the name
     */
    public static String getName() {
        return name;
    }

    /**
     * @return the state
     */
    public Status getState() {
        return this.status;
    }

    public int getRepair() {
        return this.shields+=1;
    }
    
    /**
     * @return the position
     */
    public int[] getPosition() {
        return new int[] { this.position.getX(), this.position.getY() };
    }

    /**
     * @return the direction
     */
    public Direction getDirection() {
        return this.currentDirection;
    }

    /**
     * Changes current direction to the direction on the left of it
     */
    private void turnLeft() {
        // Turn the robot left
        if (Direction.NORTH.equals(this.currentDirection)) {
            this.currentDirection = Direction.WEST;
        } else if (Direction.SOUTH.equals(this.currentDirection)) {
            this.currentDirection = Direction.EAST;
        } else if (Direction.WEST.equals(this.currentDirection)) {
            this.currentDirection = Direction.SOUTH;
        } else {
            this.currentDirection = Direction.NORTH;
        }
    }

    /**
     * Changes current direction to the direction on the right of it
     */
    private void turnRight() {
        // Turn the robot right
        if (Direction.NORTH.equals(this.currentDirection)) {
            this.currentDirection = Direction.EAST;
        } else if (Direction.SOUTH.equals(this.currentDirection)) {
            this.currentDirection = Direction.WEST;
        } else if (Direction.WEST.equals(this.currentDirection)) {
            this.currentDirection = Direction.NORTH;
        } else {
            this.currentDirection = Direction.SOUTH;
        }
    }

    /**
     * Change the direction of the world either to the left or right
     * 
     * @param turnRight if 'true' it will turn the world direction to the right else
     *                  the left
     */
    public void changeDirection(boolean turnRight) {
        if (turnRight)
            turnRight();
        else
            turnLeft();
    }

    public UpdateResponse updatePosition(Db db, int nrSteps) {
        int newX = this.position.getX();
        int newY = this.position.getY();

        if (Direction.NORTH.equals(this.currentDirection)) {
            newY = newY + nrSteps;
        }
        if (Direction.SOUTH.equals(this.currentDirection)) {
            newY = newY - nrSteps;
        }
        if (Direction.WEST.equals(this.currentDirection)) {
            newX = newX - nrSteps;
        }

        if (Direction.EAST.equals(this.currentDirection)) {
            newX = newX + nrSteps;
        }

        Position newPosition = new Position(newX, newY);
        UpdateResponse isOk = isNewPositionAllowed(db, newPosition);
        UpdateResponse isGood = takeMineShields(db, newPosition);

        if (isOk.equals(UpdateResponse.SUCCESS) && isGood.equals(UpdateResponse.SUCCESS)) {
            this.position = newPosition;
            // return UpdateResponse.SUCCESS;
        }

        if (isGood.equals(UpdateResponse.FAILED_OBSTRUCTED)){
            if (shields >= 3){
                //remove robot.
            }
            if(shields >= 4){
                mineDamage();
                this.position = newPosition;
            }
        }

        // if (newPosition.isIn(TOP_LEFT, BOTTOM_RIGHT)) {
        // return UpdateResponse.FAILED_OBSTRUCTED;
        // }

        return isOk;
    }

    public UpdateResponse isNewPositionAllowed(Db db, Position position) {
        for (int i = 0; i < db.getWorld().getMyObtaclesList().size(); i++) {
            if (db.getWorld().getMyObtaclesList().get(i).blocksPosition(position)) {
                return UpdateResponse.FAILED_OBSTRUCTED;
            }
        }

        for (int i = 0; i < db.getWorld().getMyPitList().size(); i++) {
            if (db.getWorld().getMyPitList().get(i).isPositionBlock(position)
                    || db.getWorld().getMyPitList().get(i).isInsidePit(this.position, position)) {
                return UpdateResponse.KILLED_BY_A_PIT;
            }
        }

        for(int i = 0; i < db.getRobotList().size(); i++) {
            if (!db.getRobotList().get(i).getName().equals(this.name)) {
                if (position.equals(db.getRobotList().get(i).getRealPosition())) {
                    return UpdateResponse.FAILED_OBSTRUCTED;
                }
            }
        }
        // boolean isInResponse = position.isIn(TOP_LEFT, BOTTOM_RIGHT);
        // if (!isInResponse) {
        // return false;
        // }
        if (Math.abs(position.getY()) > Math.floorDiv(db.getHeight(), 2) || Math.abs(position.getX()) > Math.floorDiv(db.getHeight(), 2)){
            return UpdateResponse.ON_EDGE;
        }
        return UpdateResponse.SUCCESS;
    }

    public UpdateResponse takeMineShields(Db db, Position position) {
        for (int i = 0; i < db.getWorld().getMyMinesList().size(); i++) {
            if (db.getWorld().getMyMinesList().get(i).stepsOnMine(position) || db.getWorld().getMyMinesList().get(i).PathThrewMine(this.position, position)) {
                //mineDamage();
                return UpdateResponse.FAILED_OBSTRUCTED;
            }
        }
        return UpdateResponse.SUCCESS;
    }

    // public int takeShields

    /**
     * @return the serverThread
     */
    public static String getServerThreadName() {
        return serverThreadName;
    }

    /**
     * @return the shields
     */
    public static int getShields() {
        if (shields > 5)
        {
            return shields = 5;
        }
        else
        {
            return shields;
        }
        
    }

    /**
     * @return the shots
     */
    public static int getShots() {
        if (shots > 5)
        {
            return shots = 5;
        }
        else
        {
            return shots;
        }
        
    }

    public void mineDamage(){
        this.shields-=3;
    }

    public void damage(){
        this.shields-=1;
    }


    /**
     * @return the currentDirection
     */
    public static Direction getCurrentDirection() {
        return currentDirection;
    }

    public static Position getRealPosition(){
        return position;
    }

    public static Status updateStatus()
    {
        
        if(Status.NORMAL.equals(status))
        {
            status = Status.REPAIRING;
        }
        if(Status.REPAIRING.equals(status))
        {
            status = Status.NORMAL;
        }
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    public void fire(){
        this.shots -= 1;
    }

    public void reloadGun(){
        this.shots = this.maxReload;
    }

    @Override
    public String toString() {
        return "Robots{" +
                "serverThreadName='" + serverThreadName + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", position=" + position +
                ", currentDirection=" + currentDirection +
                ", mine=" + mine +
                ", shots=" + shots +
                ", shields=" + shields +
                ", maxReload=" + maxReload +
                '}';
    }
}
