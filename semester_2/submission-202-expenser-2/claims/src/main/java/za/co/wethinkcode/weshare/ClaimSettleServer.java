package za.co.wethinkcode.weshare;

import io.javalin.Javalin;
import za.co.wethinkcode.weshare.claims.ClaimController;
import za.co.wethinkcode.weshare.claims.ClaimsApiController;
import za.co.wethinkcode.weshare.settle.SettleApiController;
import za.co.wethinkcode.weshare.settle.SettlementController;
import static io.javalin.apibuilder.ApiBuilder.*;

public class ClaimSettleServer {

    private static final int DEFAULT_PORT = 5050;
    private static Javalin app;

    public ClaimSettleServer() {
        app = Javalin.create(config -> {
            config.defaultContentType = "application/json";
            config.enableCorsForAllOrigins();
        });

        setupRoutes(app);
    }

    public static void main(String[] args) {
        ClaimSettleServer server = new ClaimSettleServer();
        server.start(DEFAULT_PORT);
    }

    private void setupRoutes(Javalin server) {
        server.routes(() -> {
            claimRoutes();
            settlementRoutes();
        });
    }

    private static void claimRoutes() {
        get("/claim/{id}", ClaimController::findClaim);
        post("/claim", ClaimsApiController::create);
        put("/claim", ClaimsApiController::updateClaim);
        get("/claims/by/{email}", ClaimController::findClaimsBy);
        get("/claims/from/{email}", ClaimController::findClaimsFrom);
    }

    private static void settlementRoutes() {
        get("/totalclaimvalue/from/{email}", SettlementController::totalClaimBy);
        get("/totalclaimvalue/by/{email}", SettlementController::totalClaimFrom);
        post("/settlement", SettleApiController::settleClaim);
    }

    public void start(int port) {
        app.start(port);
    }

    public void close() {
        app.close();
    }
}
