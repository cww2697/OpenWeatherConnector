package net.cwmediagroup.config;

import org.json.JSONArray;
import org.json.JSONObject;


import java.io.*;
import java.nio.charset.StandardCharsets;


public class Config {

    public boolean useMongo;
    public String mongoConnection;
    public boolean useMySQL;
    public String mySQLConnection;
    public JSONArray locations;

    public void loadConfig(String configPath) throws IOException {
        File file = new File(configPath);
        StringBuilder JSON = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            for(String line; (line = br.readLine()) != null; ) {
                JSON.append(line).append("\n");
            }
        }

        String jsonString = JSON.toString();
        JSONObject config = new JSONObject(jsonString);

        this.useMongo = config.getBoolean("useMongo");
        if (this.useMongo) {
            this.mongoConnection = config.getString("mongoConnection");
        }

        this.useMySQL = config.getBoolean("useMySQL");
        if (this.useMySQL) {
            this.mySQLConnection = config.getString("mySQLConnection");
        }

        this.locations = config.getJSONArray("locations");

    }

    public void saveConfig(String configPath) throws RuntimeException {

        JSONObject config = new JSONObject();
        config.put("useMongo", this.useMongo);
        if (this.useMongo) {
            config.put("mongoConnection", this.mongoConnection);
        }
        config.put("useMySQL", this.useMySQL);
        if (this.useMySQL) {
            config.put("mySQLConnection", this.mySQLConnection);
        }
        config.put("locations", this.locations);

        try(Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(configPath), StandardCharsets.UTF_8
        ))){
            config.write(writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
