package net.cwmediagroup;

import net.cwmediagroup.config.Config;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Config config = new Config();
        try{
            String configPath = "configTest2.json"; // Todo: convert to use commandline arguments to provide config path

            try {
                config.loadConfig(configPath);
            } catch (FileNotFoundException e) {
                System.out.println("Configuration file not found: " + configPath);
            }

        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}