package za.co.wethinkcode.weshare;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import za.co.wethinkcode.weshare.app.DefaultAccessManager;
import za.co.wethinkcode.weshare.app.Sessions;
import za.co.wethinkcode.weshare.controllers.RatingApiController;


import java.time.LocalDate;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.get;

public class RatingsWebServer {

    private static final int RATINGS_PORT = 7070;

    //private static final String STATIC_DIR = "/html";
    private final Javalin app;


    /**
     * The main class starts the server on the default port 7070.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        RatingsWebServer server = new RatingsWebServer();
        server.start(RATINGS_PORT);
    }

    public RatingsWebServer() {
        configureThymeleafTemplateEngine();
        //app = createAndConfigureServer();
        app = Javalin.create();
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

    /**
     * Setup the Thymeleaf template engine to load templates from 'resources/templates'
     */
    private void configureThymeleafTemplateEngine() {
        TemplateEngine templateEngine = new TemplateEngine();

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/");
        templateEngine.setTemplateResolver(templateResolver);

        templateEngine.addDialect(new LayoutDialect());
        JavalinThymeleaf.configure(templateEngine);
    }

    private void setupRoutes(Javalin server) {
        server.routes(() -> {
            RatingsRoutes();
            loginAndLogoutRoutes();
            homePageRoute();
            expenseRoutes();
            claimRoutes();
            settlementRoutes();
        });
    }

    private static void settlementRoutes() {
//        path(SettlementViewController.PATH, () -> {
//            get(SettlementViewController::renderSettleClaimForm);
//            post(SettlementViewController::submitSettlement);
//        });
    }

    private static void claimRoutes() {
//        get(ClaimExpenseController.PATH, ClaimExpenseController::renderClaimExpensePage);
//        post(ClaimsApiController.PATH, ClaimsApiController::create);
    }

    private static void RatingsRoutes() {
        get(RatingApiController.PATH, RatingApiController::create);

    }

    private static void expenseRoutes() {
//        path("/newexpense", () -> get(context -> {
//            Expense expense = new Expense(0.00, LocalDate.now(), "?", context.sessionAttribute("user"));
//            context.render("expenseform.html", Map.of("expense", expense));
//        }));
//        post(CaptureExpenseController.PATH, CaptureExpenseController::createExpense);
    }

    private void homePageRoute() {
//        path(NettExpensesController.PATH, () -> get(NettExpensesController::renderHomePage));
    }

    private void loginAndLogoutRoutes() {
//        post(LoginController.LOGIN_PATH, LoginController::handleLogin);
//        get(LoginController.LOGOUT_PATH, LoginController::handleLogout);
    }

    @NotNull
    private Javalin createAndConfigureServer() {
        return Javalin.create(config -> {
            //config.addStaticFiles(STATIC_DIR, Location.CLASSPATH);
            config.sessionHandler(Sessions::nopersistSessionHandler);
            config.accessManager(new DefaultAccessManager());
        });
    }

}


