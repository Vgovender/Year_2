package za.co.wethinkcode.game_of_life.database;
// please note my taxi took 2 hours to go home
import za.co.wethinkcode.game_of_life.domain.World;

import java.sql.PreparedStatement;

import static za.co.wethinkcode.game_of_life.database.DbConnect.conn;

public class UpdateTable {

    UpdateTable(){
//        System.out.println(World.getWorldName());
////        System.out.println(World.getState().toString());
//        System.out.println(World.getWorldSize());
//        addValues(World.getWorldName(),0,"lll",World.getWorldSize());
    }

    public static void addValues(String name,int epoch,String state,int size){
        try(PreparedStatement stmt = conn.prepareStatement("INSERT INTO worldTable(name,epoch,state) VALUES (?,?,?,?)")){
//         stmt.setInt(1, id);
         stmt.setString(1, name);
         stmt.setInt(2, epoch);
         stmt.setString(3, state);
         stmt.setInt(4, size);
         stmt.execute();
     } catch (Exception e) {
         System.out.println(e);
     }
 }
}
