//package za.co.wethinkcode.database;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
//
//import za.co.wethinkcode.server.Db;
//import za.co.wethinkcode.server.Mine;
//import za.co.wethinkcode.server.SinglePointObstacle;
//import za.co.wethinkcode.server.configs.ConfigControl;
//import za.co.wethinkcode.server.world.Pits;
//import za.co.wethinkcode.server.world.ServerWorld;
//
//public class Restore {
//    public static Connection conn = null;
//    public static int vis;
//    public static int border2;
//    public static List<Mine> mineList2 = new ArrayList<>();
//
//    // public static Connection conn = za.co.wethinkcode.database.DbConnect.createConnection();
//    public static Connection createConnection(){
//        // initalizing the connection
//        // Connection conn = null;
//        while(true){
//            try {
//                //registering the connection
//                Class.forName("org.sqlite.JDBC");
//                //creating and getting the connection
//                conn = DriverManager.getConnection("jdbc:sqlite:src/main/java/za/co/wethinkcode/database/"
//                +ConfigControl.getArg1()+"");
//                // System.out.println("Opened database successfully"+ConfigControl.getArg1()+"RestorE");
//                return conn;
//            } catch ( Exception e ) {
//                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//                System.exit(0);
//            }
//        }
//    }
//    public Restore(){
//        createConnection();
//        getDataWorldTable();
//        getDataMineTable();
//        getDataPitTable();
//        getDataObstacleTable();
//        try {
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void getDataWorldTable(){
//        String sql = "SELECT * FROM worldTable";
//
//        try( final Statement stmt = conn.createStatement()){
//            // System.out.println(ConfigControl.getArg1());
//            ResultSet rs = stmt.executeQuery(sql);
//            while(rs.next()){
//                Object border = rs.getObject("border_linkList");
//                border2 = (int) border;
//                Db.height = border2;
//                vis = rs.getInt("visibility");
//                Db.visibility = vis;
//                System.out.println("Successfully Restored"+
//                "\nWorldSize:   " + Db.height+
//                "\nVisibility:  " + Db.visibility);
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
//
//
//    public static void getDataMineTable(){
//        String sql = "SELECT mine_linkList FROM mineTable";
//
//        try( final Statement stmt = conn.createStatement()){
//            // System.out.println(ConfigControl.getArg1());
//            ResultSet rs = stmt.executeQuery(sql);
//            while(rs.next()){
//                Object listOfMines = rs.getObject("mine_linkList");
//                String stringListofMines = (String) listOfMines;
//                int length = stringListofMines.length();
//                stringListofMines = stringListofMines.substring(1,length -1);
//                List h = new ArrayList<>();
//                h.add(stringListofMines);
//                ServerWorld.minesList = (List<Mine>)h;
//                System.out.println("Mines:       "+ServerWorld.minesList);
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
//
//    public static void getDataPitTable(){
//        String sql = "SELECT pit_linkList FROM pitTable";
//
//        try( final Statement stmt = conn.createStatement()){
//            // System.out.println(ConfigControl.getArg1());
//            ResultSet rs = stmt.executeQuery(sql);
//            while(rs.next()){
//                Object listOfPits = rs.getObject("pit_linkList");
//                String stringListOfPits = (String) listOfPits;
//                int length = stringListOfPits.length();
//                stringListOfPits = stringListOfPits.substring(1,length -1);
//                List h = new ArrayList<>();
//                h.add(stringListOfPits);
//                ServerWorld.myPitList = (List<Pits>)h;
//                System.out.println("Pits:        "+ServerWorld.myPitList);
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
//
//    public static void getDataObstacleTable(){
//        String sql = "SELECT obstical_linkList FROM obstacleTable";
//
//        try( final Statement stmt = conn.createStatement()){
//            // System.out.println(ConfigControl.getArg1());
//            ResultSet rs = stmt.executeQuery(sql);
//            while(rs.next()){
//                Object listOfObstacles = rs.getObject("obstical_linkList");
//                // System.out.println(listOfObstacles);
//                String stringListOfObstacles = (String) listOfObstacles;
//                int length = stringListOfObstacles.length();
//                stringListOfObstacles = stringListOfObstacles.substring(1,length -1);
//                List h = new ArrayList<>();
//                h.add(stringListOfObstacles);
//                ServerWorld.myObticlesList = (List<SinglePointObstacle>)h;
//                System.out.println("Obstacles:   "+ServerWorld.myObticlesList );
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
//}