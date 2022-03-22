package za.co.wethinkcode.client;


/**
 * WaitingClient is the Thread for stopping the client for some time
 */
public class WaitingClient implements Runnable {
    private int seconds;

    public WaitingClient(int seconds){
        this.seconds = seconds;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(seconds * 1000);
            Client.wait = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }



    
}
