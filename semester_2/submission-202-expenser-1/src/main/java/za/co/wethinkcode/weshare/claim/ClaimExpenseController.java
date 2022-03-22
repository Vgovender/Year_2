package za.co.wethinkcode.weshare.claim;

import com.google.common.collect.ImmutableList;
import io.javalin.http.Context;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.model.Claim;
import za.co.wethinkcode.weshare.app.model.Expense;
import za.co.wethinkcode.weshare.app.model.Person;

import java.util.*;

/**
 * Controller for handling calls from form submits for Claims
 */
public class ClaimExpenseController {
    public static final String PATH = "/claimexpense";
    public static UUID claimUuid ;

    public static Expense res;
    public static void renderClaimExpensePage(Context context){


        final DataRepository db = DataRepository.getInstance();


        String uuidAString = context.queryParam("expenseId");



        claimUuid = UUID.fromString(uuidAString);
//
        res = db.getExpense(claimUuid).get();
        List<Claim> claimsList = db.getClaims();
        ArrayList claimsForThisExpense = new ArrayList();

        double claimTotals = 0.0;

        for (Claim claim: claimsList) {
            String tester =claim.getExpense().getId().toString();
            if ( tester.equalsIgnoreCase(uuidAString)) {

                claimsForThisExpense.add(claim);
                claimTotals = claimTotals +claim.getAmount();
            }
        }
        double unclaimedTotal = res.getAmount()- claimTotals;
        Map<String,Object> viewModel2 = Map.of(

                "expense",res,
                "Claims",claimsForThisExpense,
                    "claimTotals",claimTotals,
                "unclaimedTotals",unclaimedTotal
        );
        context.render("claimexpense.html", viewModel2);

    }


}