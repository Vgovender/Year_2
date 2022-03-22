package za.co.wethinkcode.weshare.settle;

import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import java.util.UUID;
import java.time.LocalDate;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.models.Claim;
import za.co.wethinkcode.weshare.app.models.Expense;
import za.co.wethinkcode.weshare.app.models.Settlement;
import za.co.wethinkcode.weshare.claims.ClaimsApiController;

public class SettleApiController {
    static DataRepository db = DataRepository.getInstance();

    public static void settleClaim(@NotNull Context context){
        //since the claim exists in this service it would be good that 
        //the settlement be generated this side
        // Settlement settlement = context.bodyAsClass(Settlement.class);

        final UUID claimId = UUID.fromString(context.queryParam("claimId")); 

        if (claimId == null) {
            context.status(HttpCode.BAD_REQUEST);
            return;
        }

        Optional<Claim> maybeClaim = db.getClaim(claimId);

        assert maybeClaim.isPresent();
        final Claim claim = maybeClaim.get();

        if (claim.isSettled()){
            context.status(HttpCode.BAD_REQUEST);
            context.result("Claim has already been settled or settlement body is invalid!");
            return;
        }

        Settlement settlement = claim.settleClaim(LocalDate.now());

        //first update the status of the claim and update it on the db. Saving settlement as well
        claim.setSettled(true);
        db.updateClaim(settlement.getClaim());
        db.addSettlement(settlement);

        Expense updatedExp = settlement.getGeneratedExpense();

        try{
            //update expense here by calling the expense service, since it's master for expenses
            HttpResponse<JsonNode> expenseUpdateResp =  Unirest
                    .post(ClaimsApiController.EXPENSES_BASE_URL + "/expenses")
                    .body(updatedExp.toString())
                    .asJson();

            if (expenseUpdateResp.getStatus() != 200) {
                context.result("Claim has been updated and saved in db! Failed to update the expense.");
            }
            
        } catch (UnirestException e) {System.out.println("Expenses service is currently down!"); }
        catch (Exception e) {e.printStackTrace();}
        
        context.status(HttpCode.OK);
        context.result("Claim has been settled and update in db!");

    }
}
