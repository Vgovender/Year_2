package za.co.wethinkcode.linklists.database;

import kotlin.Pair;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static za.co.wethinkcode.linklists.database.Dbconnect.conn;

public class GetDatabase {

//    public static List<Pair> list = new ArrayList<Pair>();
    public static List<String> list2 = new ArrayList<String>();

    public static void getData(){
        String id;
        String word;
        String sql = "SELECT * FROM words";
        try( final Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
//                Pair<String,String> names = new Pair<String,String>(id,word);
//                System.out.println(rs.getString("id"));//check number of values, generate random words/join strings together/put in json
                id = rs.getString("id");
//                names.add(id);
//                System.out.println(rs.getString("word"));
                word = rs.getString("word");
//                Pair<String,String> names = new Pair<String,String>(id,word);
//                list.add(names);
                list2.add(word);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
//        System.out.println(list2.get(0));
    }

    public static Integer getId(){
        return 1;
    }
}
