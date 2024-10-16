package net.cwmediagroup;

import net.cwmediagroup.config.Config;
import net.cwmediagroup.connections.OpenMeteoAPI;
import net.cwmediagroup.objects.Location;
import net.cwmediagroup.objects.OpenMeteo.OpenMeteoResponse;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        try{
            String configPath = "config.json"; // Todo: convert to use commandline arguments to provide config path
            Config configuration = new Config();
            configuration.initConfiguration(configPath);
            Processor processor = new Processor(configuration);
            processor.processWeatherRequest();

        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}