package za.co.wethinkcode.linklists;
//query tool
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.*;
//import requests;
import io.javalin.http.util.JsonEscapeUtil;
import kotlin.Pair;
import za.co.wethinkcode.linklists.database.Dbconnect;
import za.co.wethinkcode.linklists.database.GetDatabase;
import za.co.wethinkcode.linklists.motd.MotdResponse;
import za.co.wethinkcode.linklists.remix.Remixer;
import za.co.wethinkcode.linklists.words.WordUrn;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.DoubleBinaryOperator;


public class ApiHandler {
    public static void createNew(Context context,String mes){
//        context.json(new MotdResponse(mes));
        MotdResponse motd = new MotdResponse(mes);
        context.json(motd);
    }
    public static void createNew(Context context){
        context.json(new MotdResponse());
//        MotdResponse motd = new MotdResponse(mes);
//        context.json(motd);
    }

//    public String hh(){
//        return "";    }

    public static void PostNew(Context context) {
        System.out.println(context.body());
        String json = "";
        String h = "";
        String abc;

        ArrayList<String> list = new ArrayList<>();
        json = context.body();
        ObjectMapper objectMapper = new ObjectMapper();
        Dbconnect db = new Dbconnect();
        GetDatabase gd = new GetDatabase();
//        db.createConnection();
        gd.getData();
        //check number of values, generate random words/join strings together/put in json

        try {
            JsonNode mix = objectMapper.readTree(json);

//            System.out.println(mix.get("long").asText());
            abc = objectMapper.writeValueAsString(mix.get("long").asText());
            context.json(abc);

//            System.out.println(mix.get("max_words").asText());
            int max = Integer.parseInt(mix.get("max_words").asText());
            //check number of values,
//             generate random words/
//             join strings together/
//             put in json
            for (int j = 0; j < max; j++) {
                Random ran = new Random();
                h = gd.list2.get(ran.nextInt(gd.list2.size()));

                list.add(h);
//                System.out.println(gd.list2.get(ran.nextInt(gd.list2.size())));
            }
            //apologies for sloppy code, time is almost up
            if (max == 1) {
//                context.json(abc + "\n" + list.get(0));
                String joinedH = "{" +
                        "\"long\":" +
                        ""+abc+""+
                        ","+
                        "\n\n"+
                        "\"short\":"+
                        "\""+list.get(0)+"\""+
                        "}";
                context.json(joinedH);
                context.status(HttpCode.CREATED);
            } else if (max == 2) {
                String joinedH = "{" +
                        "\"long\":" +
                        ""+abc+""+
                        ","+
                        "\n\n"+
                        "\"short\":"+
                        "\""+ list.get(0) + "-" + list.get(1)+"\""+
                        "}";
                context.json(joinedH);
                context.status(HttpCode.CREATED);
//                context.json(abc + "\n" + list.get(0) + "-" + list.get(1));
            } else if (max == 3) {
                String joinedH = "{" +
                        "\"long\":" +
                        ""+abc+""+
                        ","+
                        "\n\n"+
                        "\"short\":"+
                        "\"" + list.get(0) + "-" + list.get(1) + "-" + list.get(2)+"\""+
                        "}";
                context.json(joinedH);
                context.status(HttpCode.CREATED);
//                context.json(abc + "\n" + list.get(0) + "-" + list.get(1) + "-" + list.get(2));
            }
//            String joinedH = "{\"long\": \"" + abc +"\", \"short\":"
//                    + c + "}";
//            System.out.println(list);
//            System.out.println(h);


        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
//        String json = objectMapper.writeValueAsString(mix);
//        JsonNode mix = readJsonIntoJsonNode();;

//        context.json(context.body());

//        context.get("long",mix.get);
//        response.get("short,make new str")
    }

}
