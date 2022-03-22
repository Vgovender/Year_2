package za.co.wethinkcode.weshare.settle;

import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.models.Person;

public class SettlementController {

    public static void totalClaimBy(Context context){
        Person claimsOwner = new Person(context.pathParam("email"));

        double moneyOwedToThem = DataRepository.getInstance()
                .getTotalUnsettledClaimsClaimedBy(claimsOwner);

        context.json(Double.toString(moneyOwedToThem));
        context.status(HttpCode.OK);

    }

    public static void totalClaimFrom(Context context) {
        Person claimsDebtor = new Person(context.pathParam("email"));

        double moneyOwedByPerson = DataRepository.getInstance()
                .getTotalUnsettledClaimsClaimedBy(claimsDebtor);

        context.json(moneyOwedByPerson);
        context.status(HttpCode.OK);
    }

}
