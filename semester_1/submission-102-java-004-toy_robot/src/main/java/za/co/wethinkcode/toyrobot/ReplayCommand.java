package za.co.wethinkcode.toyrobot;

import java.util.Collections;

public class ReplayCommand extends Command {

    private static  boolean reversed = false;
    private static boolean rangeN = false;
    private static boolean rangeNRev = false;
    public static void setReversedTrue(){
        reversed = true;
    }

    public static void setReversedFalse(){
        reversed = false;
    }

    public boolean isReversed(){
        return reversed;
    }

    public static void set_N_True(){
        rangeN = true;
    }

    public static void set_N_False(){
        rangeN = false;
    }

    public boolean is_N(){
        return rangeN;
    }

    public static void set_N_rev_True(){
        rangeNRev = true;
    }

    public static void set_N_rev_False(){
        rangeNRev = false;
    }

    public boolean is_N_rev(){
        return rangeNRev;
    }

    public ReplayCommand() {
        super("replay");
    }

    @Override
    public boolean execute(Robot target) {
        replay(target);
        target.setStatus("replayed "+commandList.size()+" commands.");
        return true;
    }

    public void replay(Robot target){
        if (is_N() || is_N_rev()) {
            while (true) {
                if (commandList.size() > Command.getIndex()) {
                    commandList.remove(commandList.size() - 1);
                }else if (commandList.size() == Command.getIndex()) {
                    if (!isReversed()) {
                        Collections.reverse(commandList);
                    }
                    break;
                }
            }
        }

        for (String s : commandList) {
            String[] args1 = s.toLowerCase().trim().split(" ");
            switch (args1[0]) {
                case "forward":
                    target.updatePosition(Integer.parseInt(args1[1]));
                    target.setStatus("Moved forward by " + args1[1] + " steps.");
                    Play.showStatus(target);
                    break;
                case "back":
                    target.updatePosition(Integer.parseInt("-"+args1[1]));
                    target.setStatus("Moved back by " + args1[1] + " steps.");
                    Play.showStatus(target);
                    break;
                case "left":
                    target.turnLeft();
                    target.setStatus("Turned left.");
                    Play.showStatus(target);
                    break;
                case "right":
                    target.turnRight();
                    target.setStatus("Turned right.");
                    Play.showStatus(target);
                    break;
                case "sprint":
                    target.updatePositionSprint(Integer.parseInt(args1[1]));
                    target.setStatus("Moved forward by " + args1[1] + " steps.");
                    Play.showStatus(target);
                    break;
            }
        }
    }
}