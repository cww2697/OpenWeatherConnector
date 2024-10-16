package net.cwmediagroup.connections;

import net.cwmediagroup.objects.OpenMeteo.OpenMeteoResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SuppressWarnings("deprecation")
public class OpenMeteoAPI {

    public final String baseUrl = "https://api.open-meteo.com/v1/forecast?";
    public final String separator = "&";


    public OpenMeteoResponse getLocationData(String locationName, String latitude, String longitude) throws RuntimeException {

        DefaultHttpClient httpClient = null;
        try {
            httpClient = new DefaultHttpClient();

            HttpGet request = new HttpGet(
                    buildUrl(latitude, longitude)
            );
            request.addHeader("accept", "application/json");
            HttpResponse response = httpClient.execute(request);

            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
            String output = br.readLine();

            JSONObject data;
            OpenMeteoResponse openMeteoResponse;

            if (output != null) {
                data = createJsonObject(output);
                openMeteoResponse = new OpenMeteoResponse(
                        locationName,
                        data.getDouble("elevation"),
                        data.getDouble("generationtime_ms"),
                        data.getString("timezone"),
                        data.getDouble("latitude"),
                        data.getDouble("longitude"),
                        data.getJSONObject("current"),
                        data.getJSONObject("current_units")
                );
            } else {
                throw new RuntimeException("Could not get data from Open Meteo API");
            }

            httpClient.getConnectionManager().shutdown();

            return openMeteoResponse;

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

        return Url;
    }

    private JSONObject createJsonObject(String inputString) {
        return new JSONObject(inputString);
    }
}
