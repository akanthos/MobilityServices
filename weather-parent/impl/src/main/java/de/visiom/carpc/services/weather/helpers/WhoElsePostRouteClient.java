package de.visiom.carpc.services.weather.helpers;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import com.eclipsesource.json.JsonObject;

/**
 * Helper class that sends requests to the WhoElse service
 * 
 */
public class WhoElsePostRouteClient {
	private final WebTarget webTarget = ClientBuilder.newClient().target("http://localhost:666").path("whoelse/newRoute");//.queryParam("units", "metric");
	
	private String username;
	private String startLat;
	private String startLong;
	private String endLat;
	private String endLong;
	private String datetime;
	
	public WhoElsePostRouteClient(String username, String startLat, String startLong,
			String endLat, String endLong,
			String datetime) {
		this.username = username;
		this.startLat = startLat;
		this.startLong = startLong;
		this.endLat = endLat;
		this.endLong = endLong;
		this.datetime = datetime;
	}

    public void postRoute() {
        String output = webTarget.queryParam("username", username)
        		.queryParam("startLat", startLat).queryParam("startLong", startLong)
        		.queryParam("endLat", endLat).queryParam("endLong", endLong)
        		.queryParam("datetime", datetime)
        		.request().get(String.class);
        //return getLoginStatusFromJSONResponse(output);
        getLoginStatusFromJSONResponse(output);
    }

    private void getLoginStatusFromJSONResponse(String jsonString) {
        JsonObject jsonObject = JsonObject.readFrom(jsonString);
        JsonObject main = jsonObject.get("main").asObject();
        
        // TODO: Send the response somehow to tablet (throw event?? for what parameter??)
        	return ;
        
    }

}
