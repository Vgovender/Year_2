package za.co.wethinkcode.linklists.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Dbconnect {
    public static Connection conn = null;

    public Dbconnect(){
        createConnection();
    }

        public static Connection createConnection() {
            while (true) {
                try {
                    //registering the connection
                    Class.forName("org.sqlite.JDBC");
                    //creating and getting the connection
                    conn = DriverManager.getConnection("jdbc:sqlite:words.db");
                    System.out.println("Opened database successfully");
                    return conn;
                } catch (Exception e) {
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                    System.exit(0);
                }
            }
        }
//    public static void main(String[] args) {
//        createConnection();
//        GetDatabase gd = new GetDatabase();
//        gd.getData();
//    }
}
