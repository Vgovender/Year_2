package za.co.wethinkcode.toyrobot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Command {
    private final String name;
    private static String argument;
    static List<String> commandList = new ArrayList<>();
    private static int getIndex;
    private static int replayRangeIndex1;
    private static int replayRangeIndex2;
    private static boolean arg1IsInt = false;
    private static boolean arg2IsInt = false;
    private static boolean replayRange = false;
    private static boolean replayRangeReversed = false;
    public static int getIndex(){
        return getIndex;
    }

    public abstract boolean execute(Robot target);

    public Command(String name){
        this.name = name.trim().toLowerCase();
        argument = "";
    }

    public Command(String name, String argument) {
        this(name);
        Command.argument = argument.trim();
    }

    public String getName() {                                                                           //<2>
        return name;
    }

    public String getArgument() {
        return argument;
    }


    public static Command create(String instruction) {
        String[] args = instruction.toLowerCase().trim().split(" ");


        //checks to see if arg1 exists
        boolean arg;
        if (args.length == 1) {
            arg = false;
        }else{
            arg = true;
            String str = args[1];
            if (str.matches("\\d+")) {
                arg1IsInt = true;
                getIndex = Integer.parseInt(str);
            }else{
                arg1IsInt = false;
                if (str.contains("-")) {
                    replayRange = true;
                    String[] argSplit = str.toLowerCase().trim().split("-");
                    replayRangeIndex1=Integer.parseInt(argSplit[0]);
                    replayRangeIndex2=Integer.parseInt(argSplit[1]);
                }
        }
        // checks to see if arg1 isInt

        }
        //checking if arg2 exists
        boolean arg2;
        if (args.length == 1 ||args.length == 2) {
            arg2 = false;
        }else{
            arg2 = true;
            String str = args[2];
            if (str.matches("\\d+")) {
                arg2IsInt = true;
                getIndex = Integer.parseInt(str);
            }else {
                arg2IsInt = false;
                if (str.contains("-")) {
                    replayRangeReversed = true;
                    String[] argSplit = str.toLowerCase().trim().split("-");
                    replayRangeIndex1=Integer.parseInt(argSplit[0]);
                    replayRangeIndex2=Integer.parseInt(argSplit[1]);
                }
            }

        }

        //if arg1 exists and arg1 not = int and arg1 contains "-" then
        if ("shutdown".equals(args[0]) || "off".equals(args[0])) {
            commandList.clear();
            return new ShutdownCommand();
        } else if ("help".equals(args[0])) {
            return new HelpCommand();
        } else if ("forward".equals(args[0])) {
            commandList.add(instruction.toLowerCase());
            return new ForwardCommand(args[1]);
        } else if ("back".equals(args[0])) {
            commandList.add(instruction.toLowerCase());
            return new BackCommand(args[1]);
        } else if ("right".equals(args[0])) {
            commandList.add(instruction.toLowerCase());
            return new RightCommand();
        } else if ("left".equals(args[0])) {
            commandList.add(instruction.toLowerCase());
            return new LeftCommand();
        } else if ("sprint".equals(args[0])) {
            commandList.add(instruction.toLowerCase());
            return new SprintCommand(args[1]);
        } else if ("replay".equals(args[0])) {
            //replay all
            if (!arg) {
                ReplayCommand.setReversedFalse();
                ReplayCommand.set_N_False();
                ReplayCommand.set_N_rev_False();
                return new ReplayCommand();
            }//replay all reversed
            else if (args[1].equals("reversed") && !arg2) {
                Collections.reverse(commandList);
                return new ReplayCommand();
            }
            // replay n
            else if (arg1IsInt && !arg2) {
                ReplayCommand.set_N_True();
                Collections.reverse(commandList);
                return new ReplayCommand();
            }//replay n rev
            else if (!arg1IsInt && args[1].equals("reversed") && arg2IsInt) {
                Collections.reverse(commandList);
                commandList.subList(Integer.parseInt(args[2]), commandList.size()).clear();
                return new ReplayCommand();
            }//replayRange
            else if (!arg1IsInt && replayRange && !arg2) {
                commandList.subList(replayRangeIndex2, commandList.size()).clear();
                commandList.subList(replayRangeIndex1-replayRangeIndex2,commandList.size()).clear();
                return new ReplayCommand();
            }
            //replay range reversed
            else if (!arg1IsInt && args[1].equals("reversed") && replayRangeReversed) {
                commandList.subList(replayRangeIndex2, commandList.size()).clear();
                commandList.subList(replayRangeIndex1-replayRangeIndex2,commandList.size()).clear();
                Collections.reverse(commandList);
                return new ReplayCommand();
            }
            throw new IllegalArgumentException("Unsupported command: " + instruction);
        }
        throw new IllegalArgumentException("Unsupported command: " + instruction);
    }
}