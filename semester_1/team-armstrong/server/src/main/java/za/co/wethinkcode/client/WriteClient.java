package za.co.wethinkcode.client;

import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.Scanner;

import za.co.wethinkcode.JSONHandler;
import za.co.wethinkcode.YAMLHandler;
import za.co.wethinkcode.communication.Request;


/**
 * WriteClient is a Thread used for writing to the server
 */
public class WriteClient extends Thread {
    private Scanner scanner = new Scanner(System.in);
    private static PrintWriter out;
    private static Socket socket;


    /**
     * Constructor for WriteClient
     * @params socket The socket from the client
     */
    public WriteClient(Socket socket) {

        try {
            OutputStream output = socket.getOutputStream();
            out = new PrintWriter(output, true);
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void run() {
        String robotName;
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        Request req = new Request("command", "", new String[0]);

        do {
            String userCommand = getUserInput(userInput, "Enter Command");
            if (userCommand.indexOf("@") >= 0) {
                handleClientCommand(userCommand);
                continue;
            }
            if (userCommand.split(" ")[0].toLowerCase().trim().equals("launch") && userCommand.split(" " ).length > 2) {
                Client.newRobot = true;
                Client.tempRobotName = userCommand.split(" ")[2];
                Client.robotUsed = Client.tempRobotName;
            } else {
                Client.newRobot = false;
            }

            req = createRequest(userCommand, Client.robotUsed);
            String requestString = JSONHandler.serializeJavaObjectRequest(req);

            if(!Client.wait){
                out.println(requestString);
            }

        } while (!req.getCommand().equals("off"));

        try {
            out.close();
            // socket.close();
        } catch (Exception ex) {

            System.out.println("Error writing to server: " + ex.getMessage());
        }
    }

    private static Request createRequest(String userInput, String robotName) {
        String[] args = userInput.split(" ");
        String command;
        String name;
        String[] arguments;

        if (!Client.newRobot) {

            arguments = args.length > 1 ? new String[args.length - 1] : new String[0];

            if (args.length > 1) {
                for (int i = 1; i < args.length; i++) {
                    arguments[i - 1] = args[i];
                }
            }
            name = robotName;
            command = args[0];
        } else {
            name = args[2];
            Client.tempRobotName = name;
            command = args[0];
            Map<String, Object> yamlConfigs = YAMLHandler.getYamlObject("Configurations.yml");
            arguments = new String[3];
            arguments[0] = args[1];
            arguments[1] = yamlConfigs.get("shield").toString();
            arguments[2] = yamlConfigs.get("shots").toString();
            //arguments = args.length > 3 ? new String[args.length - 2] : new String[0];
//            if (arguments.length > 0) {
//                arguments[0] = args[1];
//                // System.out.println(Arrays.toString(args));
//                if (args.length > 3) {
//                    // System.out.println(args.length);
//                    for (int i = 3; i < args.length; i++) {
//
//                        // System.out.println(args[i]);
//                        arguments[i - 2] = args[i];
//                    }
//                }
//
//            }

        }
        return new Request(command, name, arguments);

    }

    private static String getUserInput(BufferedReader userInput, String prompt) {
        while (true) {
            try {
                String input = userInput.readLine();
                return input;
            } catch (Exception e) {
                System.out.println("Please enter valid input");

                continue;
            }
        }

    }

    public void handleClientCommand(String input) {
        switch (input.split(" ")[0].toLowerCase().trim()) {
        case "@use":
            if (Client.getRobots().contains(input.split(" ")[1].toLowerCase().trim())) {
                Client.robotUsed = input.split(" ")[1].toLowerCase().trim();
            }
            break;
        case "@robots":
            for (int i = 0; i < Client.getRobots().size(); i++) {
                System.out.println("" + (i + 1) + ". " + Client.getRobots().get(i));
            }
        }
    }

    public static void closeClientWriter(){
        try {
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
