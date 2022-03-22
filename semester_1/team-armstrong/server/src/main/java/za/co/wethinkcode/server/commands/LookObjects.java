package za.co.wethinkcode.server.commands;

public class LookObjects {

    private String direction;
    private String type;
    private int distance;

    public LookObjects(String direction, String type, int distance) {
        this.direction = direction;
        this.type = type;
        this.distance = distance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getDirections() {
        return direction;
    }

    public void setDirections(String direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "LookObjects{" +
                "direction='" + direction + '\'' +
                ", type='" + type + '\'' +
                ", distance=" + distance +
                '}';
    }
}
