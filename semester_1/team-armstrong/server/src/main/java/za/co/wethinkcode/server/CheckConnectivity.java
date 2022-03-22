package za.co.wethinkcode.server;

import java.io.PrintStream;

import za.co.wethinkcode.communication.Response;
import za.co.wethinkcode.server.commands.Command;

public class CheckConnectivity extends Command implements Runnable{
    private PrintStream out;
    private String name;

    public CheckConnectivity(PrintStream out,String name){
        this.out = out;
        this.name = name;
    }

    @Override
    public void run() {
        
    // while(true){
    //     // try {
    //         // Thread.sleep(1000);
    //         out.println("1");;
    //     // } catch (Exception e) {
    //         // Db db = getDataBase();
    //         // for (int i  = 0; i< db.getRobotList().size(); i++){
    //         //     if(db.getRobotList().get(i).getServerThreadName().equals(name));{
    //         //         db.KillRobot(db.getRobotList().get(i));
    //         //     }
    //         // }
    //         // System.out.println("killing client " + name);
    //     //     e.printStackTrace();
    //     // }
    // }
    }

    @Override
    public Response execute() throws RequestErrorException {
        // TODO Auto-generated method stub
        return null;
    }
    
}