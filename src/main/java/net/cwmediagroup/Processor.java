package net.cwmediagroup;

import net.cwmediagroup.config.Config;
import net.cwmediagroup.connections.OpenMeteoAPI;
import net.cwmediagroup.objects.Location;
import net.cwmediagroup.objects.OpenMeteo.OpenMeteoResponse;
import net.cwmediagroup.services.FileService;
import org.jetbrains.annotations.NotNull;

import java.io.File;


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

            this.handleOutput(data);
        }

    }

    private void handleOutput (@NotNull OpenMeteoResponse data){
        String dataJson = data.toJSONString();
        if (this.configuration.terminalOutput) {
            System.out.println(dataJson);
        }

        if (this.configuration.useFileOutput) {
            String fileName = data.locationName.substring(0,3)+"_"+data.current.getString("time")+".json";
            String filePath = configuration.outputDir + File.separator + fileName;
            FileService fileService = new FileService();
            fileService.writeJsonFile(filePath, dataJson);
        }
    }

    public Config getConfig() {
        return configuration;
    }
}
