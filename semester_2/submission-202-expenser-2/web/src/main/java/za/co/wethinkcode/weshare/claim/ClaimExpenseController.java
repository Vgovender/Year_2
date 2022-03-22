package za.co.wethinkcode.weshare.claim;

import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import kong.unirest.GenericType;
import kong.unirest.Unirest;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.model.Claim;
import za.co.wethinkcode.weshare.app.model.Expense;
import za.co.wethinkcode.weshare.app.model.Person;
import za.co.wethinkcode.weshare.nettexpenses.NettExpensesController;

import java.time.LocalDate;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/**
 * Controller for handling calls from form submits for Claims
 */
public class ClaimExpenseController {
    public static final String PATH = "/claimexpense";
    // private static final ObjectMapper mapper = new ObjectMapper();
    private static final Gson gsnMapper = new Gson();

    public static void renderClaimExpensePage(Context context){
        UUID expenseId = UUID.fromString(context.queryParam("expenseId"));

        String expenseString = Unirest
            .get(NettExpensesController.EXPENSE_SERVER + "/expenses/{id}")
            .routeParam("id", expenseId.toString())
            .asString()
            .getBody();

        // Expense expense = mapper.readValue();
        Expense expense = gsnMapper.fromJson(expenseString, Expense.class);

        if (expense.equals(null)) {
            context.status(HttpCode.BAD_REQUEST);
            context.result("Invalid expense");
            return;
        }

        context.sessionAttribute("expense", expense);
        context.render("claimexpense.html",
                Map.of("newClaim", new Claim( expense, new Person("email@domain.com"), 0.0, LocalDate.now() ),
                        "expense", expense));
    }
}