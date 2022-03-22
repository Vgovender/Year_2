package za.co.wethinkcode.server;

 public class MineRobot extends Robots{
     public MineRobot(String serverThread, String name, int shields, int shots, int... positionArray) {
         super(serverThread, name, shields, 0, positionArray);
     }
 }