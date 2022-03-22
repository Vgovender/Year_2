package za.co.wethinkcode.weshare.ratings;

import io.javalin.http.Context;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import za.co.wethinkcode.weshare.webModule.PersonController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RatingsController {
        public static final String PATH = "/ratings";
        public static String RATINGS_SERVER = "http://localhost:7070";

        public static void renderRatingsPage(Context context) {
                System.out.println("in ratings controller web");
                String value = context.queryParam("value");

                String personas = Unirest
                        .get(RATINGS_SERVER + "/ratings/" + value)
                        .header("accept", "application/json")
                        .header("persons", PersonController.renderPersons(context).toString())
                        .asString()
                        .getBody();

                System.out.println(personas);
                JSONArray ratingsColl = new JSONArray(personas);

                List<JSONObject> ratingsList = new ArrayList<JSONObject>();
                List<JSONObject> ratingsList2 = new ArrayList<JSONObject>();
                List<ratingPerson> personsList = new ArrayList<ratingPerson>();

                ratingsColl.forEach((rating) -> ratingsList.add(new JSONObject(rating)));

                ratingsList.forEach((rating) -> ratingsList2.add(new JSONObject(rating.get("element").toString())));



                ratingsList2.forEach((exp) -> personsList.add(new ratingPerson((Double) exp.get("amount"),exp.get("who").toString(),exp.get("when").toString())));

                System.out.println("rendering ratings");
                System.out.println(personsList);
                Map<String, Object> ViewModel = Map.of(
                                "title", "Ratings By " + value,
                                "persons", personsList);
                context.render("/ratings.html", ViewModel);

        }
}
