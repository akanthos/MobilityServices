package de.visiom.carpc.services.weather.helpers;

import java.util.HashMap;
import java.util.Map;

public class LocationCoordinatesMapper {
	private Double currentLatitude = 0.0;
	private Double currentLongitude = 0.0;
	
	static Map<String, Double> latitudeData = new HashMap<String, Double>();
	static Map<String, Double> longitudeData = new HashMap<String, Double>();
	
	static {
		latitudeData.put("Garching", 48.2513878);
		longitudeData.put("Garching", 11.6509662);		
		latitudeData.put("Berlin", 52.5170365);
		longitudeData.put("Berlin", 13.3888599);
		latitudeData.put("Bonn", 50.7358511);
		longitudeData.put("Bonn", 7.1006599);
	}
	
	public void initialize(String locationName) {
		if(latitudeData.containsKey(locationName)) {
			currentLatitude = latitudeData.get(locationName);
		}
		else {
			currentLatitude = 0.0;
		}
		if(longitudeData.containsKey(locationName)) {
			currentLongitude = longitudeData.get(locationName);
		}
		else {
			currentLongitude = 0.0;
		}
	}
	
	public Double getLatitude() {
		return currentLatitude;
	}
	
	public Double getLongitude() {
		return currentLongitude;
	}
}
