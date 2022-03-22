package za.co.wethinkcode.weshare.person;

import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import kong.unirest.json.JSONObject;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.model.Person;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class PersonController {
    public static final String GET_PERSON_EMAIL = "/person";
    public static final int CLAIM_BASE_URL = 5050;

    public static boolean isValidEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public static void getPersonViaEmail(Context context){
        System.out.println("im here");
        String value = context.queryParam("email");
        if (isValidEmail(value)) {
            getData(context);
        }
        else{
            context.status(HttpCode.BAD_REQUEST);
        }
    }

    public static void getData(Context context){
        Person p = new Person(context.queryParam("email"));
        JSONObject obj=new JSONObject();

        Double totalExpenses = DataRepository.getInstance().getTotalExpensesFor(p);
        Double getNettExpense = DataRepository.getInstance().getNettExpensesFor(p);
        LocalDate date = DataRepository.getInstance().getDate();
        Double unsettledClaimBy = 0.0;
        Double unsettledClaimFrom = 0.0;
        Double settledClaimBy = 0.0;
        Double settledClaimfrom = 0.0;

        try {
            String claimedFrom =  Unirest.get("http://localhost:"+CLAIM_BASE_URL+"/totalclaimvalue/from/{email}?false")
                    .routeParam("email",context.queryParam("email")).asString().getBody();
            unsettledClaimFrom = Double.valueOf(claimedFrom);

        }catch (UnirestException e){
            System.out.println(e);
        }

        try {
            String claimedBy =  Unirest.get("http://localhost:5050/totalclaimvalue/by/{email}?false")
                    .routeParam("email",context.queryParam("email")).asString().getBody();
            unsettledClaimBy = Double.valueOf(claimedBy);
        }catch (UnirestException e){
            System.out.println(e);
        }

        try {
            String settledcClaimedFrom =  Unirest.get("http://localhost:"+CLAIM_BASE_URL+"/totalclaimvalue/from/{email}?true")
                    .routeParam("email",context.queryParam("email")).asString().getBody();
            settledClaimfrom = Double.valueOf(settledcClaimedFrom);

        }catch (UnirestException e){
            System.out.println(e);
        }

        try {
            String settledClaimedBy =  Unirest.get("http://localhost:5050/totalclaimvalue/by/{email}?false")
                    .routeParam("email",context.queryParam("email")).asString().getBody();
            settledClaimBy = Double.valueOf(settledClaimedBy);
        }catch (UnirestException e){
            System.out.println(e);
        }

        obj.put("lastupdate", date);
        obj.put("totalexpenses",totalExpenses);
        obj.put("unsettledclaims", unsettledClaimBy);
        obj.put("settledclaims", settledClaimfrom);
        obj.put("nettexpense",getNettExpense);
        context.json(obj.toString());
    }
}
