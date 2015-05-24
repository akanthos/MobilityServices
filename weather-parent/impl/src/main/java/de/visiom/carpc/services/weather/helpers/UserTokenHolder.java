package de.visiom.carpc.services.weather.helpers;

public class UserTokenHolder {
	private String userToken;
	
	public UserTokenHolder(String userToken) {
		this.userToken = userToken;
	}
	
	public String getUserToken() {
		return userToken;
	}
}
