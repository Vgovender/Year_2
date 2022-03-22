package za.co.wethinkcode.weshare.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.javalin.http.Context;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import za.co.wethinkcode.weshare.app.model.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RatingApiController {
    public static String EXPENSE_PORT = "6969";
    public static String RATINGS_PORT = "7070";
    public static String WEB_PORT = "8080";
    public static String CLAIM_PORT = "5050";
    public static  String WEB_SERVER = "http://localhost:"+WEB_PORT;
    public static  String EXPENSE_SERVER="http://localhost:"+EXPENSE_PORT;
    public static  String CLAIM_SERVER="http://localhost:"+CLAIM_PORT;
    public static  String RATINGS_SERVER="http://localhost:"+RATINGS_PORT;
    public static  String PATH= "/ratings/{value}";

    public static void create(Context context) throws JsonProcessingException {

        Person currentUser = context.sessionAttribute("user");
        String value = context.pathParamAsClass("value", String.class).get();

        String persons = context.header("persons");
        //used for testing
        persons = "[{\"id\": \"12\", \"name\": \"herman\",\"email\": \"herman@wethinkcode.co.za\"},{\"id\": \"13\", " +
                "\"name\": \"herman\",\"email\": \"Mike@wethinkcode.co.za\"}," +
                "{\"id\": \"12\", \"name\": \"herman\",\"email\": \"sett@wethinkcode.co.za\"}]";
        JSONArray pList = new JSONArray(persons);
        List< JSONObject> objList = new ArrayList<>();
        List< JSONObject> objList2 = new ArrayList<>();
        List< webPerson> webPeople = new ArrayList<>();

        pList.forEach( (exp) -> objList.add(new JSONObject(exp)));
        objList.forEach((exp) -> System.out.println(exp.getClass() + "===="+exp));
        objList.forEach((exp) -> objList2.add(new JSONObject(exp.get("element").toString())));
        objList2.forEach((exp) -> System.out.println(exp.getClass() + "-----"+exp));
        objList2.forEach((exp) ->webPeople.add(new webPerson(exp.get("email").toString(),Integer.parseInt(exp.get("id").toString()),exp.get("name").toString() ))) ;
        List<ratingPerson> payload  = new ArrayList<>();

        switch (value) {
            case "claims":
                for (webPerson person : webPeople) {
                    //call to expense here
                    String email = person.getEmail();
                    String exRes = Unirest
                            .get(EXPENSE_SERVER + "/person")
                            .queryString("email", email)
                            .header("accept", "application/json")
                            .asString()
                            .getBody();
                    //assign who,last_update,amount

                    JSONObject newOb = new JSONObject(exRes);

                    String lUpdate = newOb.get("lastupdate").toString();
                    Double NetExp = Double.parseDouble(newOb.get("nettexpense").toString());

                    Double unsetclaims = Double.parseDouble(newOb.get("unsettledclaims").toString());
                    Double totExpense = Double.parseDouble(newOb.get("totalexpenses").toString());
                    Double setclaims = Double.parseDouble(newOb.get("settledclaims").toString());
                    Double sumcliams =   setclaims + unsetclaims;

                    ratingPerson pers = new ratingPerson(email,sumcliams,lUpdate);
                    payload.add(pers); // or might have to add dataload obj
                }
                Collections.sort(payload,Collections.reverseOrder());
                context.status(200);
                context.json(payload);
                break;
            case "expense":
                for (webPerson person : webPeople) {
                    //call to expense here
                    String email = person.getEmail();
                    String exRes = Unirest
                            .get(EXPENSE_SERVER + "/person")
                            .queryString("email", email)
                            .header("accept", "application/json")
                            .asString()
                            .getBody();
                    //assign who,last_update,amount
                    JSONObject newOb = new JSONObject(exRes);

                    String lUpdate = newOb.get("lastupdate").toString();
                    Double NetExp = Double.parseDouble(newOb.get("nettexpense").toString());
                    Double unsetclaims = Double.parseDouble(newOb.get("unsettledclaims").toString());
                    Double totExpense = Double.parseDouble(newOb.get("totalexpenses").toString());
                    Double setclaims = Double.parseDouble(newOb.get("settledclaims").toString());
                    Double sumcliams =   setclaims + unsetclaims;

                    ratingPerson pers = new ratingPerson(email,totExpense,lUpdate);
                    payload.add(pers); // or might have to add dataload obj
                }

                Collections.sort(payload,Collections.reverseOrder());

                context.status(200);
                context.json(payload);


                break;
            case "settled":
                for (webPerson person : webPeople) {
                    //call to expense here
                    String email = person.getEmail();
                    String exRes = Unirest
                            .get(EXPENSE_SERVER + "/person")
                            .queryString("email", email)
                            .header("accept", "application/json")
                            .asString()
                            .getBody();
                    //assign who,last_update,amount
                    JSONObject newOb = new JSONObject(exRes);

                    String lUpdate = newOb.get("lastupdate").toString();
                    Double NetExp = Double.parseDouble(newOb.get("nettexpense").toString());
                    Double unsetclaims = Double.parseDouble(newOb.get("unsettledclaims").toString());
                    Double totExpense = Double.parseDouble(newOb.get("totalexpenses").toString());
                    Double setclaims = Double.parseDouble(newOb.get("settledclaims").toString());
                    Double sumcliams =   setclaims + unsetclaims;

                    ratingPerson pers = new ratingPerson(email,setclaims,lUpdate);
                    payload.add(pers); // or might have to add dataload obj
                }
                Collections.sort(payload,Collections.reverseOrder());
                context.status(200);
                context.json(payload);

                break;
            case "unsettled":
                for (webPerson person : webPeople) {
                    //call to expense here
                    String email = person.getEmail();
                    String exRes = Unirest
                            .get(EXPENSE_SERVER + "/person")
                            .queryString("email", email)
                            .header("accept", "application/json")
                            .asString()
                            .getBody();
                    //assign who,last_update,amount
                    JSONObject newOb = new JSONObject(exRes);

                    String lUpdate = newOb.get("lastupdate").toString();
                    Double NetExp = Double.parseDouble(newOb.get("nettexpense").toString());
                    Double unsetclaims = Double.parseDouble(newOb.get("unsettledclaims").toString());
                    Double totExpense = Double.parseDouble(newOb.get("totalexpenses").toString());
                    Double setclaims = Double.parseDouble(newOb.get("settledclaims").toString());
                    Double sumcliams =   setclaims + unsetclaims;

                    ratingPerson pers = new ratingPerson(email,unsetclaims,lUpdate);
                    payload.add(pers); // or might have to add dataload obj
                }
                Collections.sort(payload,Collections.reverseOrder());
                context.status(200);
                context.json(payload);

                break;
            default:

                context.status(200);
                context.json("Error,No case for parameter!");
        }
    }
    //                JSONObject dataload = new JSONObject();
//                    dataload.put("who",email);
//                    dataload.put("last_update",lUpdate);
//                    dataload.put("amount",sumcliams);
//                    use above if json malforms
}
