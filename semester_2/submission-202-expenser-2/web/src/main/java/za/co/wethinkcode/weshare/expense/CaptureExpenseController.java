package za.co.wethinkcode.weshare.expense;

import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import za.co.wethinkcode.weshare.app.model.Expense;
import za.co.wethinkcode.weshare.app.model.Person;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Controller for handling API calls for Expenses
 */
public class CaptureExpenseController {
    public static final String PATH = "/expenses";
    private static String EXPENSE_SERVER="http://localhost:6969";

    public static void createExpense(Context context){
        Person currentPerson = context.sessionAttribute("user");

        //TODO proper server-side validation of form params
        String description = context.formParam("description");
        double amount;
        try {
            amount = Double.parseDouble(Objects.requireNonNull(context.formParam("amount")));
        } catch (NumberFormatException e){
            context.status(HttpCode.BAD_REQUEST);
            context.result("Invalid amount specified");
            return;
        }
        
        LocalDate date;
        
        try {
            date = LocalDate.parse(Objects.requireNonNull(context.formParam("date")));
        } catch (DateTimeException e){
            context.status(HttpCode.BAD_REQUEST);
            context.result("Invalid due date specified");
            return;
        }

        Expense expense = new Expense( amount, date, description, currentPerson );

        try {
            Unirest.post(EXPENSE_SERVER + PATH)
            .header("accept", "application/json")
            .body(expense.toString())
            .asJson()
            .ifFailure(response -> {
                System.out.println(response.toString());
            });

        } catch (UnirestException e){
            context.status(200);
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        context.redirect("/home");
    }
}