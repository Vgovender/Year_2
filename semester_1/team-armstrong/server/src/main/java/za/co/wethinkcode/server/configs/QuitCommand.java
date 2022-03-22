package za.co.wethinkcode.server.configs;

import java.util.*;
import java.io.File;
import java.util.logging.Logger;

import za.co.wethinkcode.JSONHandler;
import za.co.wethinkcode.communication.Response;
import za.co.wethinkcode.server.MultiServer;
import za.co.wethinkcode.server.Server;

public class QuitCommand extends ServerCommands{
    private static final Logger LOGGER = Logger.getLogger(QuitCommand.class.getName());

    @Override
    public boolean runCommand() {
        LOGGER.info("Shutting down server...");
        Set<Server> threads = MultiServer.getThreadsList();
        Response res = new Response("", new HashMap<>(), new HashMap<>());
        // res.setMessage("Server shutting down");
        for(Server singleThread : threads){
            singleThread.getOut().println(JSONHandler.serializeJavaObjectResponse(res));
            singleThread.stop();
        }

        return false; 
    }
    
}
