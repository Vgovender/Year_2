package za.co.wethinkcode.game_of_life.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnect {
    public static Connection conn = null;

    public static Connection createConnection(){
        while(true){
            try {
                //registering the connection
                Class.forName("org.sqlite.JDBC");
                //creating and getting the connection
                conn = DriverManager.getConnection("jdbc:sqlite:world.db");
                 System.out.println("Opened database successfully");
                return conn;
            } catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        createConnection();
        new CreateTable();
        new UpdateTable();
    }
}
