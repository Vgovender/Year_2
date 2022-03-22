package za.co.wethinkcode.weshare.claim;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import org.eclipse.jetty.util.ajax.JSON;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.model.Claim;
import za.co.wethinkcode.weshare.app.model.Expense;
import za.co.wethinkcode.weshare.app.model.Person;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
//import org.eclipse.jetty.http.HttpStatus;

/**
 * Controller for handling API calls for Claims
 */
public class ClaimsApiController {
    public static final String PATH ="/api/claims";

    public static void create(Context context) throws JsonProcessingException {

        //System.out.println("we are supposed to send a json response back");

        ObjectMapper map = new ObjectMapper();
        ObjectNode res = map.createObjectNode();

        Person currentUser = context.sessionAttribute("user");
        JsonNode jNode = map.readTree(context.body().toString());


        Person person = new Person(jNode.get("email").asText());
        DataRepository db = DataRepository.INSTANCE;

        Expense exp = db.getExpense(ClaimExpenseController.claimUuid).get();
        //System.out.println(context.body());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");
        LocalDate due_date = LocalDate.parse(jNode.get("due_date").asText(), formatter);

        db.addClaim(new Claim(new Expense(jNode.get("claim_amount").asDouble(), LocalDate.now(),
                "",person),person,jNode.get("claim_amount").asDouble(), LocalDate.now()));
        //the addClaim function adds a claim meaning we can add a claim and display it immediately after
        //so the process above must come before all others in the
        db.addClaim(exp.createClaim(person,jNode.get("claim_amount").asDouble(), due_date));

        Double amnt = jNode.get("claim_amount").asDouble();
        Map<String,Object> resp = Map.of(

                "id",ClaimExpenseController.claimUuid.toString(),
                "claimFromWho",person.getEmail(),
                "dueDate",jNode.get("due_date"),
                "claimAmount",amnt
        );

        context.json(resp);
        context.status(HttpCode.CREATED);
    }
}