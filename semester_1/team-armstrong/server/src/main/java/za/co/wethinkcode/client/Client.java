package za.co.wethinkcode.client;

import java.net.*;
import java.util.*;

//Testing build pipeline team armstrong
/**
 *Client is the starting pont of the game
 */
public class Client {
    private static List<String> robots = new ArrayList<>();
    public static boolean newRobot = false;
    public static String tempRobotName = null;
    public static String robotUsed = null;
    public static boolean wait = false;


    
    public static void main(String args[]) throws Exception {

        System.out.println("Starting Game...");
        System.out.println("Max Shots: 5\n" + "Max Shields: 5\n");
        Socket socket = new Socket("localhost", 5000);

        try {
            new WriteClient(socket).start();
            new ReadClient(socket).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addToListOfRobots(String name) {
        robots.add(name);
    }

    /**
     * @return the robots
     */
    public static List<String> getRobots() {
        return robots;
    }
}