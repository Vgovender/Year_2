package za.co.wethinkcode.toyrobot;
import java.util.*;

public class SprintCommand extends Command {
    @Override
    public boolean execute(Robot target) {
        int nrSteps = Integer.parseInt(getArgument());
        while (nrSteps !=0) {
        if (target.updatePositionSprint(nrSteps)) {
                target.setStatus("Moved forward by " + nrSteps + " steps.");
                Play.showStatus(target);
            } else {
                target.setStatus("Sorry, I cannot go outside my safe zone.");
            }
            nrSteps --;
        }
        return true;
    }

    public SprintCommand(String argument) {
        super("sprint", argument);
    }
}
