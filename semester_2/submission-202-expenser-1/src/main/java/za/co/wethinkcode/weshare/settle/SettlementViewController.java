package za.co.wethinkcode.weshare.settle;

// import java.lang.management.MemoryUsage;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import io.javalin.http.Context;
// import javassist.expr.NewArray;
import za.co.wethinkcode.weshare.app.db.DataRepository;
// import za.co.wethinkcode.weshare.app.db.memory.MemoryDb;
import za.co.wethinkcode.weshare.app.model.Claim;
import za.co.wethinkcode.weshare.app.model.Expense;
// import za.co.wethinkcode.weshare.app.model.Person;
// import za.co.wethinkcode.weshare.app.model.Settlement;
import za.co.wethinkcode.weshare.nettexpenses.NettExpensesController;

/**
 * Controller for handling calls from form submits for Claims
 */
public class SettlementViewController {

    public static final String PATH = "/settleclaim";
    public static String uuidAString;

    public static void renderSettleClaimForm(Context context) {
        final DataRepository db = DataRepository.getInstance();
        uuidAString = context.queryParam("claimId");
        UUID claimUuid = UUID.fromString(uuidAString);
        // testing

        final Optional<Claim> optionalClaim = db.getClaim(claimUuid);
        assert optionalClaim.isPresent();
        final Claim claim = optionalClaim.get();

        Map<String, Object> viewModel = Map.of(
                "email", claim.getClaimedBy().getEmail(),
                "dueDate", claim.getDueDate(),
                "description", claim.getDescription(),
                "amount", claim.getAmount(),
                "claimId", uuidAString);

        context.render("settleclaim.html", viewModel);
    }

    public static void submitSettlement(Context context) {
        final DataRepository db = DataRepository.getInstance();
        UUID claimUuid = UUID.fromString(uuidAString);

        final Optional<Claim> optionalClaim = db.getClaim(claimUuid);
        assert optionalClaim.isPresent();
        final Claim claim = optionalClaim.get();

        Expense generatedExp = claim.getExpense();
        Expense updateExpense = new Expense(generatedExp.getNettAmount() - claim.getAmount(), generatedExp.getDate(),
                generatedExp.getDescription(), generatedExp.getPaidBy());

        db.updateExpense(updateExpense);
        db.addSettlement(claim.settleClaim(LocalDate.now()));
        context.redirect(NettExpensesController.PATH);
    }
}