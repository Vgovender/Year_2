package za.co.wethinkcode;

import java.io.*;
import java.util.*;


import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import za.co.wethinkcode.server.configs.GameState;

public class YAMLHandler {

    public static Map<String, Object> getYamlObject(String filepath) {
        InputStream inputStream;
        inputStream = YAMLHandler.class.getClassLoader().getResourceAsStream(filepath);
        Yaml yaml = new Yaml();
        Map<String, Object> data = yaml.load(inputStream);
        return data;

    }

    public static void writeGameState(GameState gs, File location) {
        PrintWriter writer;
        try {
            writer = new PrintWriter(location);
            DumperOptions options = new DumperOptions();
            options.setIndent(4);
            options.setPrettyFlow(true);
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            Yaml yaml = new Yaml(options);
            yaml.dump(gs, writer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}