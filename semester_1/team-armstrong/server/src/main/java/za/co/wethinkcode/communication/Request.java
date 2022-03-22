package za.co.wethinkcode.communication;


/**
 * Request class creates the model the model of a request
 */
public class Request {
    private String robot;
    private String command;
    private String[] arguments;

    public Request(){}
    /**
     * Constructor that creates a request
     * @param command The command to post
     * @param robot The name of the robot
     * @param arguments The arguments for the command
     */
    public Request(String command, String robot, String[] arguments ){
        this.robot = robot;
        this.command = command;
        this.arguments = arguments;
    }

    /**
     * @return the arguments
     */
    public String[] getArguments() {
        return arguments;
    }

    /**
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * @return the robot
     */
    public String getRobot() {
        return robot;
    }

}
 