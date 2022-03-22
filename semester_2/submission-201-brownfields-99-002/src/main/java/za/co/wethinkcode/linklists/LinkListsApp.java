package za.co.wethinkcode.linklists;

import io.javalin.Javalin;

public class LinkListsApp {
    private static Javalin app;

    // <<<
    // COMPLETE THIS CLASS
    // >>>

    public LinkListsApp(String messageOfTheDay) {
        app = Javalin.create();
        app.get("/motd",context -> ApiHandler.createNew(context,messageOfTheDay));
        app.post("/remix",context ->ApiHandler.PostNew(context));
//        app.get("/motd",context -> context.result(messageOfTheDay));
        // <<<
        // >>>
    }

    public LinkListsApp() {
        app = Javalin.create();
        app.get("/motd",context -> ApiHandler.createNew(context));
        app.post("/remix",context ->ApiHandler.PostNew(context));
//        app.post("/motd",context -> context.result(messageOfTheDay));
    }

    public void start(int port){
        app.start(port);
    }

    public int port(){
        return app.port();
    }
}
