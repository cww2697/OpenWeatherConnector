package net.cwmediagroup;

import net.cwmediagroup.config.Config;
import net.cwmediagroup.connections.OpenMeteoAPI;
import org.json.JSONObject;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        boolean verboseMode = false; // Todo: set value by passing -v flag in command line arguements
        try{
            String configPath = "config.json"; // Todo: convert to use commandline arguments to provide config path
            Config configuration = new Config();
            configuration.initConfiguration(configPath, verboseMode);
            OpenMeteoAPI openMeteoAPI = new OpenMeteoAPI();
            JSONObject data = openMeteoAPI.getLocationData();
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}