package za.co.wethinkcode.server.configs;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.stream.JsonReader;

import za.co.wethinkcode.*;
import za.co.wethinkcode.server.Db;

public abstract class ServerCommands{

    public abstract boolean runCommand();
    
    
    public Db getDataBase() {
        try {
            JsonReader reader = new JsonReader(new FileReader("src/main/java/za/co/wethinkcode/server/DB.JSON"));
            return JSONHandler.deserializeDb(reader);
        } catch (Exception e) {
            return null;
        }

    }


    public void putDatabase(Db db) {
        try {
            FileWriter writer = new FileWriter(new File("src/main/java/za/co/wethinkcode/server/DB.JSON"));
            JSONHandler.serializeJavaObjectDb(db, writer);
        } catch (Exception e) {
        }

    }
}
