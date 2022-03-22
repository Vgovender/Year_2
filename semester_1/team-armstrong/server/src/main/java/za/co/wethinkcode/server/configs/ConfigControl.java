package za.co.wethinkcode.server.configs;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

import za.co.wethinkcode.database.DbConnect;
//import za.co.wethinkcode.database.Restore;

public class ConfigControl implements Runnable{
    ServerSocket multiServerSocket;
    static String input;

    public ConfigControl(ServerSocket multiServerSocket) {
        this.multiServerSocket = multiServerSocket;
    }

    @Override
    public void run(){
        Scanner scan = new Scanner(System.in);
        boolean keepRunning = true;
        while(keepRunning){
                System.out.println("Enter server command");
                input = scan.nextLine();
                keepRunning = confirmCommand(input);
        }

        // Close Server Socket
        try {
            multiServerSocket.close();
            scan.close();
        } catch (IOException ignored) {}
    }

    public static String getArg1(){
        return input.split(" ")[1];
    }

    public boolean confirmCommand(String command){
        switch(command.split(" ")[0].toLowerCase()){
            case "quit":
                ServerCommands quitCommand =  new QuitCommand();
                return quitCommand.runCommand();
            case "robots":
                ServerCommands listCommand =  new ListCommand();
                return listCommand.runCommand();
            case "purge":
                if(command.split(" ").length > 1){
                    ServerCommands purgeCommand=  new PurgeCommand(command.split(" ")[1]);
                    return purgeCommand.runCommand();
                }
                System.out.println("Command Incomplete\n");
                System.out.println("    USAGE : purge <robot name>\n");
                return true;
            case "dump":
                ServerCommands dumpCommand =  new DumpCommand();
                return dumpCommand.runCommand();
            case "restore":
            String[] extensions = {"",""};
            if (command.split(" ").length == 2) {
                extensions =getArg1().split("[.]");
                String home2 = System.getProperty("user.home");
                File tempFile2 = new File(home2+"/team-armstrong/src/main/java/za/co/wethinkcode/database/"+getArg1());
                boolean exists2 = tempFile2.exists();
                if (exists2==true) {
                    return true;//new Restore() != null;
                }else{
                    if (command.split(" ").length != 2 ||command.split(" ").length == 2 && !"db".equalsIgnoreCase(extensions[1])){
                        System.out.println("db".equalsIgnoreCase(extensions[1]));
                        System.out.println("Command Incorrect\n");
                        System.out.println("    USAGE : restore <filename.db>\n");
                    }
                    System.out.println("File does not exist");
                    return true;
                }
            }
            case "save":
                String[] extension = {"",""};
                String home = System.getProperty("user.home");
                File tempFile = new File(home+"/team-armstrong/src/main/java/za/co/wethinkcode/database/"+getArg1());
                boolean exists = tempFile.exists();
                if (exists) {
                    System.out.println("File already exists\nWhat would you like to do?");
                    System.out.println("-Refuse\n-Overwrite\n-Let the user decide\n");
                    Scanner kb = new Scanner(System.in);
                    String response = kb.nextLine();
                    if (response.toLowerCase().equals("refuse")) {
                        return true;
                    }else if (response.toLowerCase().equals("overwrite")){
                        tempFile.delete();
                        if (
                        tempFile.exists()) {
                            System.out.println("failed to delete " + tempFile);
                        }else{
                            System.out.println("file deleted, creating new file");   
                            return DbConnect.run();                     
                        }
                    }
                    else if(response.toLowerCase().equals("Let the user decide")){
                        System.out.println("Do something");
                    }else{
                        System.out.println("Invalid command");
                        return true;
                    }
                    return true;
                }else{
                    if (command.split(" ").length == 2) {
                        extension =getArg1().split("[.]");
                    }
                    if (command.split(" ").length != 2 ||command.split(" ").length == 2 && !"db".equalsIgnoreCase(extension[1])){
                        System.out.println("Command Incorrect\n");
                        System.out.println("    USAGE : save <filename.db>\n");
                        return true;
                    }
                    return DbConnect.run();
                }

            default:
                System.out.println("Command not found!");
                return true; 
        }
    }

    public String confirmTests(){
        return "Tests Work";
    }
}