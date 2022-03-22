package za.co.wethinkcode.weshare.nettexpenses;

import io.javalin.http.Context;
import za.co.wethinkcode.weshare.app.model.ExpenseViewModel;
import za.co.wethinkcode.weshare.app.model.Person;
import za.co.wethinkcode.weshare.claim.ClaimViewModel;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class NettExpensesController {
    public static final String PATH = "/home";
    public static final String EXPENSE_SERVER="http://localhost:6969";
    public static final String CLAIM_SERVER="http://localhost:5050";
    public static final String RATINGS_SERVER="http://localhost:7070";

    public static void renderHomePage(Context context) throws JsonMappingException, JsonProcessingException{
        Person currentPerson = context.sessionAttribute("user");
 
        JSONObject totalsMap = new JSONObject(getTotals(currentPerson.getEmail()));

        Map<String, Object> viewModel =
            Map.of(
                "expenses", getExpenses(currentPerson.getEmail()),
                "totalExpenses", (Double) totalsMap.get("totalexpenses"),
                "owedToOthers", getClaims(currentPerson.getEmail(), "from", "true"),
                "totalIOwe",  (Double) totalsMap.get("unsettledclaims"),
                "owedToMe", getClaims(currentPerson.getEmail(), "by", "true"),
                "totalOwedToMe", (Double) totalsMap.get("settledclaims"),
                "nettExpenses", (Double) totalsMap.get("nettexpense")
            );

        context.render("home.html", viewModel);
    }

    private static List<ClaimViewModel> getClaims(String email, String byOrFrom, String settled){
        String getClaimBy = Unirest
            .get(CLAIM_SERVER + "/claims/"+byOrFrom+"/{email}").routeParam("email", email)
            .queryString("settled", settled)
            .header("accept", "application/json")
            .asString()
            .getBody();

        JSONArray claimsColl = new JSONArray(getClaimBy);

        List<JSONObject> claimsList = new ArrayList<JSONObject>();
        List<JSONObject> claimsList2 = new ArrayList<JSONObject>();
        List<ClaimViewModel> claimedBy = new ArrayList<ClaimViewModel>();

        claimsColl.forEach( (claim) -> claimsList.add(new JSONObject(claim)));

        claimsList.forEach((claim) ->  claimsList2.add( new JSONObject(claim.get("element").toString())));

        claimsList2.forEach((claim) ->  claimedBy.add(
            new ClaimViewModel((Double) claim.get("claimAmount"),
            claim.get("claimedBy").toString(), claim.get("claimedFrom").toString(), claim.get("dueDate").toString(),
            claim.get("claimId").toString(), claim.get("expenseId").toString(),
            claim.get("description").toString())));

        return claimedBy;
    }

    private static String getTotals(String email){
        return Unirest
        .get(EXPENSE_SERVER + "/person").queryString("email", email)
        .header("accept", "application/json")
        .asJson()
        .getBody()
        .toString();
    } 

    private static List<ExpenseViewModel> getExpenses(String email){
        String getExpenses = Unirest
            .get(EXPENSE_SERVER + "/expenses")
            .queryString("email", email)
            .header("accept", "application/json")
            .asString()
            .getBody();

        JSONArray expensesColl = new JSONArray(getExpenses);

        List<JSONObject> expeList = new ArrayList<JSONObject>();
        List<JSONObject> expeList2 = new ArrayList<JSONObject>();
        List<ExpenseViewModel> expenses = new ArrayList<ExpenseViewModel>();

        expensesColl.forEach( (exp) -> expeList.add(new JSONObject(exp)));

        expeList.forEach((exp) ->  expeList2.add( new JSONObject(exp.get("element").toString())));

        expeList2.forEach((exp) -> System.out.println(exp.getClass() + "---->"+exp.get("id")));
        expeList2.forEach((exp) ->  expenses.add(
            new ExpenseViewModel((Double) exp.get("amount"),
            (Double) exp.get("nettAmount"), exp.get("id").toString(),
             exp.get("paidBy").toString(), exp.get("date").toString(),
              exp.get("description").toString())));

        return expenses;
    }
}