package za.co.wethinkcode.toyrobot;

import za.co.wethinkcode.toyrobot.maze.EmptyMaze;
import za.co.wethinkcode.toyrobot.maze.Maze;
import za.co.wethinkcode.toyrobot.maze.SimpleMaze;
import za.co.wethinkcode.toyrobot.world.AbstractWorld;
import za.co.wethinkcode.toyrobot.world.TextWorld;
import za.co.wethinkcode.toyrobot.world.TurtleWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Play {
    static Scanner scanner;

    public static void main(String[] args) {
        Maze maze = new EmptyMaze();
        AbstractWorld world = new TextWorld(maze);
        scanner = new Scanner(System.in);
        int num = args.length;
        System.out.println(num);
        for (String arg: args) {
            if (arg.toLowerCase().equals("simplemaze")){
                maze = new SimpleMaze();
            }
        }
        for (String arg: args) {
            if (arg.toLowerCase().equals("turtle")) {
                world = new TurtleWorld(maze);
                System.out.println("TURTLE WORLD LOADED");
            }
        }
//        if (num > 0) {
//            if (args[0].equals("text")) {
//                System.out.println("TEXT WORLD LOADED");
//            }else if (args[0].equals("turtle")) {
//                System.out.println("TURTLE WORLD LOADED");
//                world = new TurtleWorld()
//            }
//        }
        Robot robot;
        List<String> commandList = new ArrayList<>();
        String name = getInput("What do you want to name your robot?");
        world.reset();
        Command.commandList = new ArrayList<>();
        robot = new Robot(name, world);
        System.out.println("Hello Kiddo!");

        System.out.println(robot.toString());

        Command command;
        boolean shouldContinue = true;
        do {
            String instruction = getInput(robot.getName() + "> What must I do next?").strip().toLowerCase();
            try {
                command = Command.create(instruction);
                shouldContinue = robot.handleCommand(command);
            } catch (IllegalArgumentException e) {
                robot.setStatus("Sorry, I did not understand '" + instruction + "'.");
            }
            String[] split_instruction = instruction.toLowerCase().trim().split(" ");
            if (!split_instruction[0].equals("sprint")) {
                System.out.println(robot);
            }
        } while (shouldContinue);
    }

    private static String getInput(String prompt) {
        System.out.println(prompt);
        String input = scanner.nextLine();

        while (input.isBlank()) {
            System.out.println(prompt);
            input = scanner.nextLine();
        }
        return input;
    }

    public static void showStatus(Robot robot){
        System.out.println(robot);
    }
}
