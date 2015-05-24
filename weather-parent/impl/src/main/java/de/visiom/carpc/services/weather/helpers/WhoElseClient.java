package de.visiom.carpc.services.weather.helpers;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import com.eclipsesource.json.JsonObject;

/**
 * Helper class that sends requests to openweathermap.org
 * 
 */
public class WhoElseClient {
	private final WebTarget webTarget = ClientBuilder.newClient().target("http://localhost:666").path("whoelse");
	
	private String userToken;
		
	public WhoElseClient(String userToken) {
		this.userToken = userToken;
	}
	
	public void setUserToken(String userToken) {
		this.userToken = userToken;
    }

    public String getMatchings() {
        String output = webTarget.queryParam("user", userToken).request().get(String.class);
        return getMatchingForJSONResponse(output);
    }

    private String getMatchingForJSONResponse(String jsonString) {
    	// Ingo, what do you send us for a matching???
        JsonObject jsonObject = JsonObject.readFrom(jsonString);
        String matching = jsonObject.get("matching").asString();
        return matching;
    }

}
