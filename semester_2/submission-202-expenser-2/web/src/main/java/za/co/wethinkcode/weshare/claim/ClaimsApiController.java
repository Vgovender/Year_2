package za.co.wethinkcode.weshare.claim;

import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.model.Claim;
import za.co.wethinkcode.weshare.app.model.Expense;
import za.co.wethinkcode.weshare.app.model.Person;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;

/**
 * Controller for handling API calls for Claims
 */
public class ClaimsApiController {
    public static final String PATH = "/api/claims";
    private static String CLAIMS_SERVER = "http://localhost:8080";

    public static void create(@NotNull Context context) {
        ClaimViewModel claimViewModel = context.bodyAsClass(ClaimViewModel.class);
        Expense expense = context.sessionAttribute("expense");

        String claimFromEmail = claimViewModel.getClaimedBy();

        Person p = DataRepository.getInstance().addPerson(new Person(claimFromEmail));
        Claim c = expense.createClaim(p, claimViewModel.getAmount(), claimViewModel.dueDateAsLocalDate());
        DataRepository.getInstance().addClaim(c);
        DataRepository.getInstance().updateExpense(expense);

        try {
            Unirest.post(CLAIMS_SERVER + "/claim")
            .header("accept", "application/json")
            .body(c.toString())
            .asJson()
            .ifFailure(response -> {
                System.out.println(response.toString());
            });

        } catch (UnirestException e){
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        claimViewModel.setId(expense.getClaims().size());
        context.status(201);
        context.json(claimViewModel);
    }
}