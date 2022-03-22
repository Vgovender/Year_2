package za.co.wethinkcode.weshare.nettexpenses;

import io.javalin.http.Context;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.db.memory.MemoryDb;
import za.co.wethinkcode.weshare.app.model.Person;

import java.util.Map;

public class NettExpensesController {
    public static final String PATH = "/home";

    public static void renderHomePage(Context context){
        
        Person currentUser = context.sessionAttribute("user");
        final DataRepository db = DataRepository.INSTANCE;

        Map<String, Object> viewModel = Map.of(
            "expenses", db.getExpenses(currentUser),
            "nettExpenses", db.getNettExpensesFor(currentUser),
            "expenseTotal", db.getTotalExpensesFor(currentUser),
            "theyOweMe", db.getClaimsBy(currentUser, true),
            "theyOweMeTotal", db.getTotalUnsettledClaimsClaimedBy(currentUser),
            "iOweThem", db.findUnsettledClaimsClaimedFrom(currentUser),    
            "iOweThemTotal", db.getTotalUnsettledClaimsClaimedFrom(currentUser)
        );

        context.render("home.html", viewModel);
    }
}