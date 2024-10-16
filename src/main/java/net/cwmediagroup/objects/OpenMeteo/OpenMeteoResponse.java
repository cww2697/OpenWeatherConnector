package net.cwmediagroup.objects.OpenMeteo;

import net.cwmediagroup.objects.Location;
import org.json.JSONObject;

public class OpenMeteoResponse {
    public String locationName;
    public double elevation;
    public double generationTime;
    public String timezone;
    public double latitude;
    public double longitude;
    public JSONObject current;
    public JSONObject currentUnits;

    public OpenMeteoResponse(
            String locationName,
            double elevation,
            double generationTime,
            String timezone,
            double latitude,
            double longitude,
            JSONObject current,
            JSONObject currentUnits
    ) {
        this.locationName = locationName;
        this.elevation = elevation;
        this.generationTime = generationTime;
        this.timezone = timezone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.current = current;
        this.currentUnits = currentUnits;
    }

    public String toJSONString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("locationName", this.locationName);
        jsonObject.put("elevation", elevation);
        jsonObject.put("generationTime", generationTime);
        jsonObject.put("timezone", timezone);
        jsonObject.put("latitude", latitude);
        jsonObject.put("longitude", longitude);
        jsonObject.put("current", current);
        jsonObject.put("currentUnits", currentUnits);
        return jsonObject.toString();
    }

}
