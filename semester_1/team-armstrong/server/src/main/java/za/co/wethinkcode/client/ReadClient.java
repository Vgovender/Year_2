package za.co.wethinkcode.client;

import java.io.*;
import java.net.Socket;
import java.util.*;

import za.co.wethinkcode.JSONHandler;
import za.co.wethinkcode.communication.Response;
import za.co.wethinkcode.server.Direction;

/**
 * Handle response from server
 */
public class ReadClient extends Thread {

    public static int[] positionArray;
    public static int visibility;
    public static int visibility2;
    private BufferedReader reader;
    private Socket socket;

    /**
     * Constructor for the reading class
     * 
     * @param socket connection to server
     */
    public ReadClient(Socket socket) {
        this.socket = socket;

        try {
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException ex) {
            System.out.println("Error getting input stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Starting method for Thread
     */
    public void run() {
        while (true) {
            try {
                String response = reader.readLine();

                // When the server disconnects it returns a null response, we use it to kill the
                // client
                if (response == null) {
                    throw new IOException();
                }
                handleResponse(response);
            } catch (IOException ex) {
                System.out.println("Disconnected from server, Goodbye!!");
                try {
                    reader.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                }
                WriteClient.closeClientWriter();
                break;
            }

        }
    }

    /**
     * DEserializes the response string from the server
     */
    private static boolean handleResponse(String response) {
        // Create response object from response String
        if (response.equals("1")) {
            return true;
        }
        Response res = JSONHandler.deserializeResponse(response);

        // if there is a message it means we response not for a request we made

        if (Client.newRobot && Client.tempRobotName != null) {
            Client.newRobot = false;
            Client.addToListOfRobots(Client.tempRobotName);
            Client.robotUsed = Client.tempRobotName;
            Client.tempRobotName = null;
        }
        printResults(res);
        return true;
    }

    private static void printResults(Response res) {
        RobotState state;
        try {

            if (res.getResult() != null && res.getResult().equals("ERROR")) {
                try {
                    System.out.println(res.getData().get("message"));
                } catch (Exception e) {
                }
            } else {
                try {
                    currentData(res);
                } catch (Exception e) {
                }

                try {
                    state = currentState(res);
                } catch (Exception e) {
                    state = null;
                }

                if (state != null) {
                    printState(state);
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to print message from server");
        }
    }

    private static void printState(RobotState state) {
        System.out.println("" + Client.robotUsed + " at (" + state.positionArray[0] + ", " + state.positionArray[1]
                + ") | Direction : " + state.direction + " | Status : " + state.status + " | Shields : " + state.shields
                + " | Shots: " + state.shots);
        boolean start = false;

        if (state.status.equals("REPAIRING") || state.status.equals("RELOADING") || state.status.equals("SETMINE")) {
            start = true;
        }

        if (start) {
            Client.wait = true;
            WaitingClient waitingClient;
            System.out.println(state.status);
            switch (state.status) {
            case "REPAIRING":
                waitingClient = new WaitingClient(WorldConfigs.repair);
                break;
            case "RELOADING":
                waitingClient = new WaitingClient(WorldConfigs.reload);
                break;
            case "SETMINE":
                waitingClient = new WaitingClient(WorldConfigs.mine);
                break;
            default:
                waitingClient = new WaitingClient(WorldConfigs.repair);
                break;
            }
            Thread t = new Thread(waitingClient);
            t.start();
        }

    }

    private static int[] positionObjectToArray(Object ObjectArray) {
        ArrayList<Object> objectArray = (ArrayList<Object>) ObjectArray;
        int[] newArray = { (int) (double) objectArray.get(0), (int) (double) objectArray.get(1) };
        return newArray;
    }

    public static RobotState currentState(Response res) {
        int[] positionArray = positionObjectToArray(res.getState().get("position"));
        String status = ((String) res.getState().get("status"));
        String direction = ((String) res.getState().get("direction"));
        int shields = ((int) (double) res.getState().get("shields"));
        int shots = ((int) (double) res.getState().get("shots"));
        // System.out.println("6");

        return new RobotState(positionArray, direction, shields, shots, status);

    }

    /**
     * Reading the data value from DD
     */
    public static void currentData(Response res) {
        int[] positionArray;
        try {
            positionArray = positionObjectToArray(res.getState().get("position"));
        } catch (Exception e) {

        }
        try {
            List<Map<String, Object>> objects = ((List<Map<String, Object>>) res.getData().get("objects"));
            for (int i = 0; i < objects.size(); i++) {
                String direction = ((String) objects.get(i).get("direction"));
                String type = ((String) objects.get(i).get("type"));
                int distance = (int)(double)objects.get(i).get("distance");
                System.out
                        .println("    - " + type + "     - Direction   : " + direction + "   - Distance : " + distance);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }

        try {
            visibility = (int) (double) res.getData().get("visibility");
            
            WorldConfigs.visibility = visibility;
            System.out.println("Visibility Range        : " + visibility);
            visibility2 = visibility;
            System.out.println(visibility2+"  vis2");
        } catch (Exception e) {
        }
        try {
            int reload = (int) (double) res.getData().get("reload");
            WorldConfigs.reload = reload;
            System.out.println("Reload speed  (sec)     : " + reload);
        } catch (Exception e) {
        }

        try {
            int repair = (int) (double) res.getData().get("repair");
            WorldConfigs.repair = repair;
            System.out.println("Repair Speed (sec)      : " + repair);
        } catch (Exception e) {
        }

        try {
            int mine = (int) (double) res.getData().get("mine");
            WorldConfigs.mine = mine;
            System.out.println("Mine Speed (sec)        : " + mine);
        } catch (Exception e) {
        }

        try {
            int shields = (int) (double) res.getData().get("shields");
            WorldConfigs.shields = shields;
            System.out.println("Max Shield Strength     : " + shields);
        } catch (Exception e) {
        }

        try {
            
            String message = (String) res.getData().get("message");
            if (message != null)
                System.out.println(message);

        } catch (Exception e) {
        }
        // String status = ((String) res.getState().get("status"));
        // String direction = ((String) res.getState().get("direction"));
        // int shields = ((int) res.getState().get("shields"));
        // int shots = ((int) res.getState().get("shots"));
        // System.out.println("6");

    }

    // private void currentData(Response res){

    // }

}
