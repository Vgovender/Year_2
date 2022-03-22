package za.co.wethinkcode.weshare.webModule;

import io.javalin.http.Context;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.model.Person;
import com.google.common.collect.ImmutableList;

public class PersonController {
    public static final String PATH = "/web/persons";

    public static ImmutableList<Person> renderPersons(Context context) {
        System.out.println("in persons");

        ImmutableList<Person> persons = DataRepository.getInstance().allPersons();
        context.json(persons);
        return persons;
    }
}
