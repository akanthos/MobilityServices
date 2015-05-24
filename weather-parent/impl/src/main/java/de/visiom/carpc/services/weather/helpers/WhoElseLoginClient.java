package de.visiom.carpc.services.weather.helpers;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import com.eclipsesource.json.JsonObject;

/**
 * Helper class that sends requests to the WhoElse service
 * 
 */
public class WhoElseLoginClient {
	private final WebTarget webTarget = ClientBuilder.newClient().target("http://localhost:666").path("whoelse/login");//.queryParam("units", "metric");
	
	private String username;
	private String password;
	
	public WhoElseLoginClient(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void tryLogin() {
        String output = webTarget.queryParam("username", username).queryParam("password", password).request().get(String.class);
        //return getLoginStatusFromJSONResponse(output);
        getLoginStatusFromJSONResponse(output);
    }

    private void getLoginStatusFromJSONResponse(String jsonString) {
        JsonObject jsonObject = JsonObject.readFrom(jsonString);
        JsonObject main = jsonObject.get("main").asObject();
        
        if (main.get("status").asString() == LoginStatus.LOGIN_OK.name()) {
        	// TODO: throw event to report successful login
        	return ;
        }
    }

}
