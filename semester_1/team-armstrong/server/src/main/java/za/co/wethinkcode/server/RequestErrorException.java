package za.co.wethinkcode.server;

import java.util.*;
import java.util.Map;

import za.co.wethinkcode.communication.Response;

public class RequestErrorException extends Exception{
    /**
     *
     */
    private static final long serialVersionUID = 1L;


    // private String message;
    public RequestErrorException(String message){
        super(message);
        // this.message = message;
    }

    
    public Response execute(){
        Map<String, Object> data = new HashMap<>();
        data.put("message", getMessage());
        return new Response("ERROR", data, new HashMap<>());
    }
}