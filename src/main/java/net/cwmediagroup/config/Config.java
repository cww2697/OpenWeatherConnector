package net.cwmediagroup.config;

import net.cwmediagroup.objects.Location;
import net.cwmediagroup.services.FileService;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.*;
import java.util.ArrayList;


public class Config {

    public boolean terminalOutput = false;
    public boolean useFileOutput = false;
    public String outputDir;
    public boolean useMongo = false;
    public String mongoConnection;
    public boolean useMySQL = false;
    public String mySQLConnection;
    public ArrayList<Location> locations = new ArrayList<>();

    private static final String templateConfig = """
            {
              "useMongo": false,
              "mongoConnection": "",
              "useMySQL": false,
              "mySqlConnection": "",
              "useFileOutput": false,
              "outputDir": "",
              "locations": [
                  {
                    "name" : "Los Angeles, CA",
                    "lat": "34.0522",
                    "long": "-118.2437"
                  }
                ]
            }""";

    public void initConfiguration(String configPath) throws RuntimeException {

        try {
            if (!configPath.isEmpty()) {
                if (!new File(configPath).exists()) {
                    throw new FileNotFoundException(configPath);
                }
                this.loadConfig(configPath);
            } else {
                System.out.println("No config file found. Running with default configuration...");
                this.loadDefaultConfig();
            }

        } catch (FileNotFoundException e) {
            System.err.println("Configuration file not found: " + configPath);
            System.out.println("Running with default configuration...");
            this.loadDefaultConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadConfig(String configPath) throws IOException {

        File file = new File(configPath);
        StringBuilder JSON = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            for(String line; (line = br.readLine()) != null; ) {
                JSON.append(line).append("\n");
            }
        }

        String jsonString = JSON.toString();
        JSONObject config = new JSONObject(jsonString);
        this.mapJsonConfig(config);

    }

    private void loadDefaultConfig() {
        this.terminalOutput = true;
        this.locations.add(new Location("Los Angeles, CA", "34.0522", "-118.2437"));
    }

    private void mapJsonConfig(@NotNull JSONObject config) {
        try {
            this.useFileOutput = config.getBoolean("useFileOutput");
        } catch (JSONException e) {
            this.useFileOutput = false;
        }

        try{
            this.useMongo = config.getBoolean("useMongo");
        } catch (JSONException e) {
            this.useMongo = false;
        }

        try {
            this.useMySQL = config.getBoolean("useMySQL");
        } catch (JSONException e) {
            this.useMySQL = false;
        }

        if (!this.useFileOutput || !this.useMongo || !this.useMySQL) {
            this.terminalOutput = true;
        }

        try {
            if (this.useFileOutput) {
                this.outputDir = config.getString("outputDir");
            }
            if (this.useMySQL) {
                this.mySQLConnection = config.getString("mySQLConnection");
            }
            if (this.useMongo) {
                this.mongoConnection = config.getString("mongoConnection");
            }

            try {
                JSONArray locations = config.getJSONArray("locations");
                for (int i = 0; i < locations.length(); i++) {
                    JSONObject jsonLocation = locations.getJSONObject(i);
                    Location location = new Location(jsonLocation.getString("name"), jsonLocation.getString("lat"), jsonLocation.getString("long"));
                    this.locations.add(location);
                }
            } catch (JSONException e) {
                if (this.locations.isEmpty()){
                    this.locations.add(new Location("Los Angeles, CA", "34.0522", "-118.2437"));
                }
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void generateTemplateConfig(String outputPath) {
        FileService fileService = new FileService();
        fileService.writeJsonFile(outputPath, templateConfig);
    }

}
