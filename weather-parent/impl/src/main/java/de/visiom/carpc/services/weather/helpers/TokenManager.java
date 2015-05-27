package de.visiom.carpc.services.weather.helpers;

public class TokenManager {
	private static String userToken = "";
	
	public static void setToken(String userToken) {
		TokenManager.userToken = userToken; 
	}
	
	public static String getToken() {
		return TokenManager.userToken; 
	}
	
	public static void clearToken() {
		TokenManager.userToken = ""; 
	}
	
}
