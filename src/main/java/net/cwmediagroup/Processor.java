package net.cwmediagroup;

import net.cwmediagroup.config.Config;
import net.cwmediagroup.connections.OpenMeteoAPI;
import net.cwmediagroup.objects.Location;
import net.cwmediagroup.objects.OpenMeteo.OpenMeteoResponse;


public class Processor {

    private final Config configuration;

    public Processor(Config configuration) {
        this.configuration = configuration;
    }

    public void processWeatherRequest(){
        for (int i=0;i<this.configuration.locations.size();i++){
            OpenMeteoAPI openMeteoAPI = new OpenMeteoAPI();
            Location location = this.configuration.locations.get(i);

            OpenMeteoResponse data = openMeteoAPI.getLocationData(
                    location.getName(),
                    location.getLatitude(),
                    location.getLongitude()
            );

            if (this.configuration.terminalOutput) {
                System.out.println(data.toJSONString());
            }
        }

    }

    public Config getConfig() {
        return configuration;
    }
}
