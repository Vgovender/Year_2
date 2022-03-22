package za.co.wethinkcode.client;

public class RobotState {
    public int[] positionArray = new int[2];
    public String direction;
    public int shields;
    public int shots;
    public String status;

    public RobotState(int[] positionArray, String direction, int shields, int shots, String status) {
        this.positionArray = positionArray;
        this.direction = direction;
        this.shields = shields;
        this.shots = shots;
        this.status = status;
    }

}
