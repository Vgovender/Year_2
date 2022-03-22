package za.co.wethinkcode.server;

import java.io.*;
import java.net.*;

import java.util.*;
import java.util.logging.Logger;

import za.co.wethinkcode.YAMLHandler;
import za.co.wethinkcode.server.configs.ConfigControl;
import za.co.wethinkcode.server.world.ServerWorld;
import org.apache.commons.cli.*;

public class MultiServer {

    private static Set<Server> serverThreadsList = new HashSet<>();
    private static Map<String, Server> serverThreadMap = new HashMap<>();
    private static final Logger LOGGER = Logger.getLogger(MultiServer.class.getName());
    private static Map<String, String> arguments = new HashMap<>();
    public static List<Db> dataBase = new ArrayList<>();



    public static void main(String[] args) throws IOException {
        arguments = processArguments(args);
        int servPort = pickServerPort(arguments);
        ServerSocket s = new ServerSocket(servPort);
        LOGGER.info("Server running on port: " + servPort);
        Runnable config = new ConfigControl(s);
        Thread configTask = new Thread(config);
        configTask.start();
        Map<String, Object> yamlConfigs = YAMLHandler.getYamlObject("Configurations.yml");

        ServerWorld sw = new ServerWorld(arguments, (int) yamlConfigs.get("width"), (int) yamlConfigs.get("height"));
        dataBase.add(new Db(sw, yamlConfigs));
        setWorldSize(arguments);

        int num = 0;
        
        while (true) {

            try {

                Socket socket = s.accept();
                Server r = new Server(socket, "client " + num);
                Thread task = new Thread(r);
                serverThreadsList.add(r);
                serverThreadMap.put(r.getName(), r);
                try {

                    task.start();
                } catch (Exception e) {
                    LOGGER.fine(e.getMessage());
                }
                num++;

            } catch (IOException e) {
                LOGGER.fine(e.getMessage());
                break;
            }
        }
        LOGGER.info("GOODBYE!!!");

    }

    /**
     * @return the threadsList
     */
    public static Set<Server> getThreadsList() {
        return serverThreadsList;
    }


    public static Map<String, String> processArguments(String[] args){
        Map<String , String> listOfArgs = new HashMap<>();
        //create options
        Options options = new Options();
        Option worldSize = new Option("s", true, "size of the world");
        Option obstacle = new Option("o", true, "world obstacle");
        Option serverPort = new Option("p", true,  "server port");

        //add options
        options.addOption(worldSize);
        options.addOption(obstacle);
        options.addOption(serverPort);

        //create command line parser
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        if (args.length == 0){return listOfArgs;}
        try{
            cmd = parser.parse(options, args);
        }catch(ParseException e){
            LOGGER.info(e.getMessage());
            return listOfArgs;
        }
        listOfArgs.put("port", cmd.getOptionValue("p") != null ? cmd.getOptionValue("p") : "" );
        listOfArgs.put("size", cmd.getOptionValue("s") != null ? cmd.getOptionValue("s") : "");
        listOfArgs.put("obstacle", cmd.getOptionValue("o") != null ? cmd.getOptionValue("o") : "");

        return listOfArgs;
    }

    private static int pickServerPort(Map<String, String> arguments){
        if (arguments.isEmpty()){ return 5000;}
        return arguments.get("port").equals("") ? 5000 : Integer.parseInt(arguments.get("port"));
    }

    private static void  setWorldSize(Map<String, String> arguments){

            int s = 1;
            if (!arguments.isEmpty()) {
                s = arguments.get("size").equals("") ? 1 : Integer.parseInt(arguments.get("size"));
            }
            dataBase.get(0).setHeight(s);
            dataBase.get(0).setWidth(s);
    }
    
}


