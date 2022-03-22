package za.co.wethinkcode.weshare;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import za.co.wethinkcode.weshare.app.DefaultAccessManager;
import za.co.wethinkcode.weshare.app.Sessions;
import za.co.wethinkcode.weshare.app.model.Expense;
import za.co.wethinkcode.weshare.expense.CaptureExpenseController;
import za.co.wethinkcode.weshare.person.PersonController;

import java.time.LocalDate;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.get;

public class ExpenseWebServer {
    private static final int EXPENSE_PORT = 6969;
    private final Javalin app;

    public static void main(String[] args) {
        ExpenseWebServer server = new ExpenseWebServer();
        server.start(EXPENSE_PORT);

    }

    public ExpenseWebServer() {
//        app = createAndConfigureServer();
        app = Javalin.create(config -> {
            config.defaultContentType = "application/json";
            config.enableCorsForAllOrigins();
        });
        setupRoutes(app);
    }

    public void start(int port) {
        app.start(port);
    }

    public int port() {
        return app.port();
    }

    public void close() {
        app.close();
    }

    private void setupRoutes(Javalin server) {
        server.routes(() -> {
            expenseRoutes();
            personRoute();
        });
    }

    private void expenseRoutes() {
        get(CaptureExpenseController.GET_PATH_ID,CaptureExpenseController::getExpenseViaID);
        get(CaptureExpenseController.GET_PATH_EMAIL,CaptureExpenseController::getExpenseViaEmail);
        post(CaptureExpenseController.PATH,CaptureExpenseController::createExpense);
    }

    private static void personRoute(){
        System.out.println("im here 2");
        get(PersonController.GET_PERSON_EMAIL, PersonController::getPersonViaEmail);
    }

    @NotNull
    private Javalin createAndConfigureServer() {
        return Javalin.create(config -> {
            config.defaultContentType = "application/json";
            config.sessionHandler(Sessions::nopersistSessionHandler);
            config.accessManager(new DefaultAccessManager());
        });
    }
}
