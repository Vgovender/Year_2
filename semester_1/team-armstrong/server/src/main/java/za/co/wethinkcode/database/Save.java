//package za.co.wethinkcode.database;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.util.List;
//import za.co.wethinkcode.server.Db;
//import za.co.wethinkcode.server.world.ServerWorld;
//
//
//public class Save {
//    public static Connection conn = za.co.wethinkcode.database.DbConnect.createConnection();
//
//    Save(){
//        // addPlayerTable(Robots.getServerThreadName());
//        // addRobotTable(Robots.getName(), Robots.getRealPosition(), Robots.updateStatus(), Robots.getCurrentDirection(), Robots.mine,
//        //  Robots.getShots(), Robots.getShields());
//        addWorldTable(Db.getHeight(), Db.getVisibility());
//        addMineTable(ServerWorld.getMyMinesList());
//        addObstacleTable(ServerWorld.getMyObtaclesList());
//        addPitTable(ServerWorld.getMyPitList());
//    }
//    // /------------------------------------saving into tables
//
//    // public static void addPlayerTable(String string){
//    //     try(PreparedStatement stmt = conn.prepareStatement("INSERT INTO playerTable(client_id) VALUES (?)")){
//    //         stmt.setString(1, string);
//    //         stmt.execute();
//    //     } catch (Exception e) {
//    //         System.out.println(e);
//    //     }
//    // }
//
//    // public static void addRobotTable(String name,Position position,Status status,Direction direction,int mine,int shots,int shields){
//    //     try(PreparedStatement stmt = conn.prepareStatement("INSERT INTO robotTable(name,position_linkList,status,"+
//    //     "current_direction,mine,shots,shields) VALUES (?,?,?,?,?,?,?)")){
//    //         stmt.setString(1,name);
//    //         stmt.setObject(2, position);
//    //         stmt.setObject(3,status);
//    //         stmt.setObject(4,direction);
//    //         stmt.setInt(5,mine);
//    //         stmt.setInt(6,shots);
//    //         stmt.setInt(7,shields);
//
//    //         stmt.execute();
//    //     } catch (Exception e) {
//    //         System.out.println(e);
//    //     }
//    // }
//
//    public static void addWorldTable(int height,int visibility){
//        try(PreparedStatement stmt = conn.prepareStatement("INSERT INTO worldTable(border_linkList,visibility) VALUES (?,?)")){
//
//            // stmt.setInt[](1, positionArray);
//            stmt.setObject(1, height);
//            stmt.setInt(2, visibility);
//            stmt.execute();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
//
//    public static void addObstacleTable(List obstical_linkList){
//        try(PreparedStatement stmt = conn.prepareStatement("INSERT INTO obstacleTable(obstical_linkList) VALUES (?)")){
//            stmt.setObject(1, obstical_linkList);
//
//            stmt.execute();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
//
//    public static void addPitTable(List pit_linkList){
//        try(PreparedStatement stmt = conn.prepareStatement("INSERT INTO pitTable(pit_linkList) VALUES (?)")){
//            stmt.setObject(1, pit_linkList);
//
//            stmt.execute();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
//
//    public static void addMineTable(List mine_linkList){
//        try(PreparedStatement stmt = conn.prepareStatement("INSERT INTO mineTable(mine_linkList) VALUES (?)")){
//            stmt.setObject(1, mine_linkList);
//            stmt.execute();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
//}