package za.co.wethinkcode.weshare.claims;

import com.google.common.collect.ImmutableList;
import io.javalin.http.HttpCode;
import kong.unirest.json.JSONObject;
import org.jetbrains.annotations.NotNull;
import io.javalin.http.Context;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.models.Claim;
import za.co.wethinkcode.weshare.app.models.ClaimViewModel;
import za.co.wethinkcode.weshare.app.models.Person;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ClaimController {

    static DataRepository db = DataRepository.getInstance();

    public static void findClaim(@NotNull Context context){
        UUID claimId = UUID.fromString(context.pathParam("id"));

        if (claimId == null) {
            context.status(HttpCode.BAD_REQUEST);
            return;
        }

        Optional<Claim> maybeClaim = db.getClaim(claimId);

        assert maybeClaim.isPresent();
        final Claim claim = maybeClaim.get();

        if (claim == null) {
            context.status(HttpCode.NOT_FOUND);
            context.result("Invalid claim");
            return;
        }

        context.json(serializeClaimData(claim).toString());
        context.status(201);
    }

    public static void findClaimsBy(@NotNull Context context){

        try {
            String claimOwner = context.pathParam("email");
            boolean settled = context.queryParam("settled")
                    .equals("true");

            ImmutableList<Claim> claims = db.getClaimsBy(new Person(claimOwner), settled);

            List<JSONObject> claimsList = new ArrayList<>();

            claims.forEach((claim) -> claimsList.add(serializeClaimData(claim)));

            context.status(200);
            context.json(claimsList.toString());
        } catch (NullPointerException e) {
            context.status(HttpCode.BAD_REQUEST);
            context.json(new ArrayList<Claim>().toString());
        }

    }

    public static void findClaimsFrom(@NotNull Context context){

        try {
            String owingPerson = context.pathParam("email");
            boolean settled = context.queryParam("settled")
                    .equals("true");

            ImmutableList<Claim> claims = db.getClaimsFrom(new Person(owingPerson), settled);

            List<JSONObject> claimsList = new ArrayList<>();

            claims.forEach((claim) -> claimsList.add(serializeClaimData(claim)));

            context.json(claimsList.toString());
            context.status(200);
        } catch (NullPointerException e) {
            context.status(HttpCode.BAD_REQUEST);
            context.json(new ArrayList<Claim>());
        }

    }

    private static ClaimViewModel makeClaimModel(Claim claim){
        ClaimViewModel model = new ClaimViewModel();
        model.setClaimAmount(claim.getAmount());
        model.setClaimFromWho(claim.getClaimedFrom().getEmail());
        model.setDueDate(claim.getDueDate().toString());

        return model;
    }

    private static JSONObject serializeClaimData(Claim claim){
        final JSONObject jClaim = new JSONObject();

        jClaim.put("claimedBy",claim.getClaimedBy().getName());
        jClaim.put("claimedFrom", claim.getClaimedFrom().getName());
        jClaim.put("emailClaimedFrom", claim.getClaimedFrom().getEmail());
        jClaim.put("claimId", claim.getId().toString());
        jClaim.put("description", claim.getDescription());
        jClaim.put("claimAmount", claim.getAmount());
        jClaim.put("dueDate", claim.getDueDate().toString());
        jClaim.put("expenseId", claim.getExpense().getId().toString());

        return jClaim;
    }

}
