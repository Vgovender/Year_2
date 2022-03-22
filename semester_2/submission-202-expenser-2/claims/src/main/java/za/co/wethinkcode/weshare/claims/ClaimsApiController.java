package za.co.wethinkcode.weshare.claims;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.javalin.http.Context;
import kong.unirest.HttpResponse;
import io.javalin.http.HttpCode;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import org.jetbrains.annotations.NotNull;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.models.Claim;

public class ClaimsApiController {

    public static String EXPENSES_BASE_URL = "http://localhost:6969";

    public static void create(@NotNull Context context) throws JsonProcessingException {
        Claim claim = context.bodyAsClass(Claim.class);

        if (claim.equals(null)) {
            context.result("Invalid request, claim cannot be created!");
            context.status(HttpCode.BAD_REQUEST);

            return;
        }

        DataRepository.getInstance().addClaim(claim);

        try{
            //update expense here by calling the expense service, since it's master for expenses
            HttpResponse<JsonNode> expenseUpdateResp =  Unirest
                .post(EXPENSES_BASE_URL + "/expenses")
                .body(claim.getExpense().toString())
                .asJson();
        }
        catch (UnirestException e) {System.out.println("Expenses service is currently down!"); }
        catch (Exception e) {e.printStackTrace();}
            
        context.status(HttpCode.OK);
        context.result("Claim created successfully!");
    }

    public static void updateClaim(@NotNull Context context){
        Claim newClaim = context.bodyAsClass(Claim.class);

        if (newClaim.equals(null)){
            context.status(HttpCode.BAD_REQUEST);
            context.result("Invalid claim!");

            return;
        }

        DataRepository.getInstance().updateClaim(newClaim);
        context.status(HttpCode.OK);
        context.result("Claim updated successfully!");

        try {
            HttpResponse<JsonNode> expenseResponse =  Unirest
                .post(EXPENSES_BASE_URL + "/expenses")
                .body(newClaim.getExpense())
                .asJson();

            if (!expenseResponse.isSuccess()){
                context.result("Claim updated successfully, but expense could not be updated!");
            }

        } catch (UnirestException e) {System.out.println("Expenses service is currently down!"); }
        catch (Exception e) {e.printStackTrace();}

    }
}
