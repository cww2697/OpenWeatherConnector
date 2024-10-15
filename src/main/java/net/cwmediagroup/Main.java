package net.cwmediagroup;

import net.cwmediagroup.config.Config;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        boolean verboseMode = false; // Todo: set value by passing -v flag in command line arguements
        try{
            String configPath = "config.json"; // Todo: convert to use commandline arguments to provide config path
            Config configuration = new Config();
            configuration.initConfiguration(configPath, verboseMode);
            boolean test = true;
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}