package za.co.wethinkcode.weshare.expense;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import io.javalin.http.Context;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.model.Expense;
import za.co.wethinkcode.weshare.app.model.Person;

/**
 * Controller for handling API calls for Expenses
 */
public class CaptureExpenseController{
    public static final String CAPTURE_EXPENSE_PATH = "/newexpense";
    public static final String SUBMIT_EXPENSE_PATH = "/expenses";
//    public static final String SUBMIT_EXPENSE_PATH = "/home";

    
    public static void createExpense(Context context){
        Person currentUser = context.sessionAttribute("user");
        final DataRepository db = DataRepository.INSTANCE;

        Map<String, Object> viewModel = Map.of(
                "expenses", db.getExpenses(currentUser)

        );

        String expenseDescription = context.formParam("description");

        String tempAmount = context.formParam("amount");
        double expenseAmount = Double.parseDouble(tempAmount);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");
        LocalDate date = LocalDate.parse(context.formParam("date"), formatter);


        final Expense expense1 = DataRepository.getInstance().addExpense(new Expense(expenseAmount,date,expenseDescription,currentUser));
        db.addExpense(expense1);
        System.out.println(db.getExpenses(currentUser));

         context.redirect("/home");
//        rendExpensePage(context);
    }

    public static void rendExpensePage(Context context){
        Map<String, Object> viewModel = Map.of();
//        System.out.println("im here 2");
        context.render("expenseform.html", viewModel);
    }
}