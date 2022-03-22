package za.co.wethinkcode.server;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.logging.Logger;



import za.co.wethinkcode.JSONHandler;
import za.co.wethinkcode.communication.Request;
import za.co.wethinkcode.communication.Response;
import za.co.wethinkcode.server.commands.Command;


public class Server implements Runnable {
    private final BufferedReader in;
    private final PrintStream out;
    private final String clientMachine;
    private String name;
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    // private boolean exit;
    // Comment

    public Server(Socket socket, String name) throws IOException {
        this.clientMachine = socket.getInetAddress().getHostName();
        // System.out.println("Connection from " + clientMachine);

        // Output to client
        out = new PrintStream(socket.getOutputStream());

        // Input from client
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        // System.out.println("Waiting for client...");
        this.name = name;
        // this.exit = false;
        CheckConnectivity connectivity = new CheckConnectivity(out, name);
        Thread t = new Thread(connectivity);
        t.start();
    }

    public void run() {
        try {
            String clientMessage;
            while ((clientMessage = in.readLine()) != null && !Thread.interrupted()) {
                Request clientRequest = testCommands(clientMessage);

                Response response;
                try {
                    Command command = Command.create(clientRequest, this);
                    response = command.execute();
                } catch (RequestErrorException e) {
                    response = e.execute();
                }
                
                String serialisedResponse = JSONHandler.serializeJavaObjectResponse(response);
                // System.out.println(serialisedResponse);
                out.println(serialisedResponse);
            }
        } catch (IOException e) {

            System.out.println("Client connection closed : " + getName());
            //put this in the finnaly block
        } finally {
            deleteRobotInfo();
            closeQuietly();
        }
    }

    private void deleteRobotInfo(){
        Db db = MultiServer.dataBase.get(0);
        db.getRobotList().removeIf(robot -> robot.getServerThreadName().equals(getName()));

    }

    private void closeQuietly() {
        try {
            out.close();
        } catch (Exception e) {
            LOGGER.info("failed to close quietly");
        }
    }

    private Request testCommands(String Client) {
        return JSONHandler.deserializeRequest(Client);
    }

    /**
     * @return the out
     */
    public PrintStream getOut() {
        return out;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }


    public void sendResponse(String serverThreadName, Response res) {
        Set<Server> threads = MultiServer.getThreadsList();

        for (Server singleThread : threads) {
            if (singleThread.getName().equals(serverThreadName)){
                singleThread.getOut().println(JSONHandler.serializeJavaObjectResponse(res));
            }
        }
    }

    public void stop(){
        this.closeQuietly();
    }

}