package za.co.wethinkcode.weshare.settle;

import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.model.Claim;
import za.co.wethinkcode.weshare.app.model.Person;
import za.co.wethinkcode.weshare.app.model.Settlement;
import za.co.wethinkcode.weshare.claim.ClaimViewModel;
import za.co.wethinkcode.weshare.nettexpenses.NettExpensesController;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Controller for handling calls from form submits for Claims
 */
public class SettlementViewController {
    public static final String PATH = "/settleclaim";

    public static void renderSettleClaimForm(Context context){
        //make request for the claim with that id so you can render the page

        final String claimId = context.queryParam("claimId");

        String getClaim = Unirest
            .get(NettExpensesController.CLAIM_SERVER + "/claim/{id}").routeParam("id", claimId)
            .header("accept", "application/json")
            .asString()
            .getBody();

        if (getClaim.isEmpty()) {
            context.status(HttpCode.BAD_REQUEST);
            context.result("Invalid claim");
            return;
        }

        JSONObject getClaimObject = new JSONObject(getClaim);

        context.render("settleclaim.html", Map.of("claim", new ClaimViewModel(
            (Double) getClaimObject.get("claimAmount"),
            getClaimObject.get("emailClaimedFrom").toString(), 
            getClaimObject.get("dueDate").toString(),
            getClaimObject.get("claimId").toString(),
            getClaimObject.get("description").toString()))
        );

    }

    public static void submitSettlement(Context context) {
        UUID claimId = UUID.fromString(Objects.requireNonNull(context.formParam("id")));

        HttpResponse<JsonNode> settlemeResponse = Unirest
            .post(NettExpensesController.CLAIM_SERVER + "/settlement").queryString("claimId", context.formParam("id"))
            .header("accept", "application/json")
            .body(claimId)
            .asJson();
        
        // if (!settlemeResponse.isSuccess()) {
        //     context.status(HttpCode.BAD_REQUEST);
        //     context.result("Failed to submit settlement!");

        //     return;
        // }

        context.redirect("/home");
    }

}