package net.cwmediagroup.connections;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class OpenMeteoAPI {

    public final String baseUrl = "https://api.open-meteo.com/v1/forecast?";
    public final String separator = "&";
    //public ArrayList<String> parameters;

    public JSONObject getLocationData() throws RuntimeException {

        DefaultHttpClient httpClient = null;
        try {
            httpClient = new DefaultHttpClient();
            HttpGet request = new HttpGet(
                    buildUrl("35.4676", "-97.5164")
            );
            request.addHeader("accept", "application/json");

            HttpResponse response = httpClient.execute(request);

            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
            String output = br.readLine();

            JSONObject data;

            if (output != null) {
                data = createJsonObject(output);
            } else {
                throw new RuntimeException("Could not get data from Open Meteo API");
            }

            httpClient.getConnectionManager().shutdown();

            return data;


        } catch (IOException e) {
            httpClient.getConnectionManager().shutdown();
            throw new RuntimeException(e);
        }
    }

    private String buildUrl(String locationLat, String locationLong) {
        String Url = "";

        Url = Url.concat(this.baseUrl)
            .concat("latitude=")
            .concat(locationLat)
            .concat(separator)
            .concat("longitude=")
            .concat(locationLong)
            .concat(separator)
            .concat("current=")
            .concat("temperature_2m,relative_humidity_2m,apparent_temperature,is_day,precipitation,rain,showers,snowfall,weather_code,cloud_cover,pressure_msl,surface_pressure,wind_speed_10m,wind_direction_10m,wind_gusts_10m");

//        for (String parameter : this.parameters) {
//            Url.concat(parameter).concat(",");
//        }

        return Url;
    }

    private JSONObject createJsonObject(String inputString) {
        return new JSONObject(inputString);
    }
}
