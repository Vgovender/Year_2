package za.co.wethinkcode.server.commands;

import za.co.wethinkcode.communication.Request;
import za.co.wethinkcode.communication.Response;
import za.co.wethinkcode.server.Db;
import za.co.wethinkcode.server.Robots;

import java.util.HashMap;
import java.util.Map;

public class ShutDown  extends  Command{
     private Request request;
    public ShutDown(Request request){
        this.request = request;

    }

    @Override
    public Response execute(){
        String[] arguments = request.getArguments();
        Map<String, Object> data = new HashMap<>();

        return new Response("forward", data, data);

    }

}