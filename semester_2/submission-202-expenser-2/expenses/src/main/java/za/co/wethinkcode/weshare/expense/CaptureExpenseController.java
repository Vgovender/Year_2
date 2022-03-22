package za.co.wethinkcode.weshare.expense;

import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import kong.unirest.json.JSONObject;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.model.Expense;
import za.co.wethinkcode.weshare.app.model.Person;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Controller for handling API calls for Expenses
 */
public class CaptureExpenseController {
    public static final String PATH = "/expenses";
    public static final String GET_PATH_ID = "/expenses/{id}";
    public static final String GET_PATH_EMAIL = "/expenses";

    public static boolean isValidEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public static boolean isValidId(String id)
    {
        String idRegex = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";

        Pattern pat = Pattern.compile(idRegex);
        if (id == null)
            return false;
        return pat.matcher(id).matches();
    }

    public static void getExpenseViaEmail(Context context){
        String value = context.queryParam("email");

        if (isValidEmail(value)) {
            //get person email from path
            Person p = new Person(context.queryParam("email"));
            //check if empty
            // TODO:
            //give it back as a json list
            List<Expense> list = DataRepository.getInstance().getExpenses(p);
            List<JSONObject> list2 = new ArrayList<>();

            for (Expense expense: list) {
//                System.out.println(expense.getDate());
                JSONObject obj2 = new JSONObject();
                obj2.put("date",expense.getDate());
                obj2.put("description",expense.getDescription());
                obj2.put("paidBy",expense.getPaidBy().getEmail());
                obj2.put("id",expense.getId().toString());
                obj2.put("amount",expense.getAmount());
                obj2.put("nettAmount", expense.getNettAmount());
                list2.add(obj2);
            }
//            System.out.println(list2);
            context.json(list2.toString());
            //On success/ so send a success back
            context.status(200);
        }
        else{
            context.status(HttpCode.BAD_REQUEST);
        }

    }

    public static void getExpenseViaID(Context context){
        String value = context.pathParamAsClass("id",String.class).get();
        if(isValidId(value)){
//            context.json("valid ID");
            if (DataRepository.getInstance().getExpense(UUID.fromString(value)).isEmpty()==true){
                context.status(HttpCode.NOT_FOUND);
                context.json("UUID does not exist");
            }else{
                final Optional<Expense> expen = DataRepository.getInstance().getExpense(UUID.fromString(value));
                assert expen.isPresent();
                final Expense xp = expen.get();
                Expense ex = DataRepository.getInstance().getExpense(UUID.fromString(value)).get();

                JSONObject obj2 = new JSONObject();
                // obj2.put("date",ex.getDate());
                // obj2.put("description",ex.getDescription());
                // obj2.put("paidBy",ex.getPaidBy().getEmail());
                // obj2.put("id",ex.getId().toString());
                // obj2.put("amount",ex.getAmount());

                obj2.put("expense", ex);

                context.json(xp.toString());
                context.status(200);
            }
        }
        else{
            context.status(HttpCode.BAD_REQUEST);
        }
    }

    public static void createExpense(Context context){
        double amount;
        
        JSONObject expJsonObject = new JSONObject(context.body());
        String description = expJsonObject.get("description").toString();

        try {
            amount = Double.parseDouble(expJsonObject.get("amount").toString());
        } catch (NumberFormatException e){
            context.status(HttpCode.BAD_REQUEST);
            context.result("Invalid amount specified");
            return;
        }
        LocalDate date;
        try {
            date = LocalDate.parse(expJsonObject.get("date").toString());
        } catch (DateTimeException e){
            context.status(HttpCode.BAD_REQUEST);
            context.result("Invalid due date specified");
            return;
        }

        Person currentPerson = new Person(expJsonObject.get("paidBy").toString());

        Expense expense = new Expense( amount, date, description, currentPerson );
        DataRepository.getInstance().addExpense(expense);
    }

}