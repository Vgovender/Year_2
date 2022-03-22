package za.co.wethinkcode;

import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import za.co.wethinkcode.communication.*;
import za.co.wethinkcode.server.Db;

public class JSONHandler {
    private static Gson gson = new Gson();
    public static String serializeJavaObjectRequest(Request requestObject){

        return gson.toJson(requestObject);
    }

    public static Response deserializeResponse(String responseString){
        return gson.fromJson(responseString, Response.class);
    }

    public static String serializeJavaObjectResponse(Response responseObject){

        return gson.toJson(responseObject);
    }

    public static Request deserializeRequest(String requestString){
        return gson.fromJson(requestString, Request.class);
    }

    public static void serializeJavaObjectDb(Db requestObject, FileWriter path){
        try {
            gson.toJson(requestObject, path);
            path.close();  
        } catch (Exception e) {
        }
        
    }

    public static Db deserializeDb(JsonReader requestReader){
        return gson.fromJson(requestReader, Db.class);
    }
}
