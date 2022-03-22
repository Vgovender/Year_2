package za.co.wethinkcode.linklists;

public class WebServer {
    private static final int LISTEN_PORT = 8000;
    private static LinkListsApp app;

    public static void main(String[] args){
        String motd = "~~=[Welcome to the LinkLists Service!]=~~";
        app = new LinkListsApp(motd);
        app.start(LISTEN_PORT);
    }
}
