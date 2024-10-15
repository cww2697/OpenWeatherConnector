package net.cwmediagroup.config;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.*;


public class Config {

    public boolean terminalOutput = false;
    public boolean useFileOutput = false;
    public String outputDir;
    public boolean useMongo = false;
    public String mongoConnection;
    public boolean useMySQL = false;
    public String mySQLConnection;
    public JSONArray locations;

    public void initConfiguration(String configPath, @NotNull Boolean verboseMode) throws RuntimeException {

        try {
            if (verboseMode) {
                System.out.println("Loading configuration from: " + configPath);
            }
            this.loadConfig(configPath);
        } catch (FileNotFoundException e) {
            System.out.println("Configuration file not found: " + configPath);
            System.out.println("Running with default configuration...");
            this.loadDefaultConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (verboseMode) {
            System.out.println("Configuration successfully loaded");
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
        this.locations = new JSONArray("[\"Los Angeles, CA\"]");
    }

    private void mapJsonConfig(JSONObject config) {
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
                this.locations = config.getJSONArray("locations");
            } catch (JSONException e) {
                this.locations = new JSONArray("[\"Los Angeles, CA\"]");
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}
