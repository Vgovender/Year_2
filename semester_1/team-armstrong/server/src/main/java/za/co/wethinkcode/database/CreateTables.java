package za.co.wethinkcode.database;
import java.sql.Connection;
import java.sql.Statement;

public class CreateTables {
    public static Connection conn = za.co.wethinkcode.database.DbConnect.createConnection();

    CreateTables(){
        // playerTable();
        // robotTable();
        worldTable();
        obstacleTable();
        pitTable();   
        mineTable(); 
    }

    // public static void playerTable(){
    //     try (Statement stmt = conn.createStatement()){
    //         //creating the table if it does not exist
    //         String tableSql = "CREATE TABLE IF NOT EXISTS playerTable" 
    //                         + "(client_id int PRIMARY KEY)";

    //         //executes the table or updates the table if it already exists
    //         stmt.execute(tableSql);

    //     } catch (Exception e) {
    //         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
    //     }
    //     System.out.println("Successfully created table playerTable");
    // }

    // public static void robotTable(){
    //     try (Statement stmt = conn.createStatement()){
    //         //creating the table if it does not exist
    //         String tableSql = "CREATE TABLE IF NOT EXISTS robotTable" 
    //                         + "(name varchar(30),position_linkList JSON DEFAULT('[]'),"
    //                         + "status varchar(30),current_direction varchar(5),"
    //                         + "mine int, shots int,shields int)";

    //         //executes the table or updates the table if it already exists
    //         stmt.execute(tableSql);

    //     } catch (Exception e) {
    //         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
    //     }
    //     System.out.println("Successfully created table robotTable");
    // }

    public static void worldTable(){
        try (Statement stmt = conn.createStatement()){
            //creating the table if it does not exist
            String tableSql = "CREATE TABLE IF NOT EXISTS worldTable" 
                            + "(border_linkList JSON DEFAULT('[]'),visibility int)";

            //executes the table or updates the table if it already exists
            stmt.execute(tableSql);

        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        System.out.println("Successfully populated table worldTable");
    }

    public static void obstacleTable(){
        try (Statement stmt = conn.createStatement()){
            //creating the table if it does not exist
            String tableSql = "CREATE TABLE IF NOT EXISTS obstacleTable" 
                            + "(obstical_linkList JSON DEFAULT('[]'))";

            //executes the table or updates the table if it already exists
            stmt.execute(tableSql);

        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        System.out.println("Successfully populated table obstacleTable");
    }

    public static void pitTable(){
        try (Statement stmt = conn.createStatement()){
            //creating the table if it does not exist
            String tableSql = "CREATE TABLE IF NOT EXISTS pitTable" 
                            + "(pit_linkList JSON DEFAULT('[]'))";

            //executes the table or updates the table if it already exists
            stmt.execute(tableSql);

        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        System.out.println("Successfully populated table pitTable");
    }

    public static void mineTable(){
        try (Statement stmt = conn.createStatement()){
            //creating the table if it does not exist
            String tableSql = "CREATE TABLE IF NOT EXISTS mineTable" 
                            + "(mine_linkList JSON DEFAULT('[]'))";

            //executes the table or updates the table if it already exists
            stmt.execute(tableSql);

        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        System.out.println("Successfully populated table mineTable");
    }
}
