package za.co.wethinkcode.worldrobotapi.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnection {
    public DbConnection() {
    }

    public static Connection startDbConnection (){
        final String world_table = "CREATE TABLE IF NOT EXISTS world_data"
                                    + " (id integer PRIMARY KEY, "
                                    + "name text NOT NULL, "
                                    + "visibility integer NOT NULL, "
                                    + "size integer NOT NULL );";
        final String obstacle_table = "CREATE TABLE IF NOT EXISTS obstacles"
        + " (id integer PRIMARY KEY, "
        + "x_val integer NOT NULL, "
        + "y_val integer NOT NULL, "
        + "size integer NOT NULL, "
        +"obstacle_world integer NOT NULL, "
        +"FOREIGN KEY (obstacle_world) REFERENCES world_data (id));";
        try{
                Connection connection = DriverManager.getConnection("jdbc:sqlite:new_world.db");
                System.out.println("Connected to database successfully");
                Statement smtb = connection.createStatement();
                smtb.execute(world_table);
                smtb.execute(obstacle_table);
                System.out.println("tables created");
            //Class.forName("org.sqlite.JDBC");

        return connection;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }

    }

    public static void StopDbConnection (Connection conn) throws SQLException{
        conn.close();
    }


    public static void connect() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:world.db")) {

            System.out.println();
            System.out.println("Connected to database successfully");
            runQ(conn);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public static void runQ(Connection conn){
        try(final Statement stmt = conn.createStatement()){
            System.out.println(stmt.executeUpdate("INSERT INTO world_data (id, name, size) VALUES (1, 'FIREFOX', 4)"));
            System.out.println(stmt.executeUpdate( "INSERT INTO obstacles (ID, X_VAL, Y_VAL, SIZE, OBSTACLE_WORLD) VALUES (1, 2, 2, 0, 1)" ));
        }catch( SQLException e ){
            e.printStackTrace();
        }
    }
}
