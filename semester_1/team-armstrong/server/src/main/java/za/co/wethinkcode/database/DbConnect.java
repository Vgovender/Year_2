package za.co.wethinkcode.database;
import java.sql.Connection;
import java.sql.DriverManager;

import za.co.wethinkcode.server.configs.ConfigControl;

public class DbConnect
{

    // /----------------------------------creating the connection |
    //this is a comment   
    public static Connection conn = null;
    

    public static Connection createConnection(){
        // initalizing the connection
        // Connection conn = null;
        while(true){
            try {
                //registering the connection
                Class.forName("org.sqlite.JDBC");
                //creating and getting the connection
                conn = DriverManager.getConnection("jdbc:sqlite:src/main/java/za/co/wethinkcode/database/"
                +ConfigControl.getArg1()+"");
                // System.out.println("Opened database successfully"+ConfigControl.getArg1());
                return conn;
            } catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }
        }
    }

    public static boolean run(){
        //create the connection
        createConnection();
        //create the tables
        new CreateTables();
        // adding values to tables
//        new Save();
        return true;
    }

}