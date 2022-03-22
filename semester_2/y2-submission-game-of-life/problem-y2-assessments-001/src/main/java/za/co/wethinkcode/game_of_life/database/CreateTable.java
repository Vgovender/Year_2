package za.co.wethinkcode.game_of_life.database;

import java.sql.Connection;
import java.sql.Statement;

import static za.co.wethinkcode.game_of_life.database.DbConnect.conn;

public class CreateTable {
    CreateTable(){
        worldTable();
    }

    public static void worldTable(){
        try (Statement stmt = conn.createStatement()){
            //creating the table if it does not exist
            String tableSql = "CREATE TABLE IF NOT EXISTS worldTable (" +
                    " id INTEGER AUTO INCREMENT," +
                    " name TEXT NOT NULL," +
                    " epoch INTEGER NOT NULL," +
                    " state TEXT NOT NULL," +
                    " size INTEGER NOT NULL,"+
                    " PRIMARY KEY(id)" +
                    ");";

            //executes the table or updates the table if it already exists
            stmt.execute(tableSql);

        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        System.out.println("Successfully populated table worldTable");
    }
}
