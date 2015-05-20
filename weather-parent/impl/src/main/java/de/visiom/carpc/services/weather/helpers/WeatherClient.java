package de.visiom.carpc.services.weather.helpers;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import com.eclipsesource.json.JsonObject;

/**
 * Helper class that sends requests to openweathermap.org
 * 
 */
public class WeatherClient {
	private final WebTarget webTarget = ClientBuilder.newClient().target("http://api.openweathermap.org").path("data/2.5/weather").queryParam("units", "metric");
	
	private double latitude;
	private double longitude;
	
	public WeatherClient(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getTemperature() {
        String output = webTarget.queryParam("lat", latitude).queryParam("lon", longitude).request().get(String.class);
        return getTemperatureForJSONResponse(output);
    }

    private Double getTemperatureForJSONResponse(String jsonString) {
        JsonObject jsonObject = JsonObject.readFrom(jsonString);
        JsonObject main = jsonObject.get("main").asObject();
        return main.get("temp").asDouble();
    }

}
