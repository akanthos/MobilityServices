package de.visiom.carpc.services.weather.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

//import com.eclipsesource.json.JsonObject;

/**
 * Helper class that sends requests to openweathermap.org
 * 
 */
public class WhoElseClient {
	private final WebTarget webTarget = ClientBuilder.newClient().target("http://localhost:6000");
	private static final Logger LOG = LoggerFactory.getLogger(WhoElseClient.class);
	
	private String userToken;
		
	public WhoElseClient() {
	}
	
	public void setUserToken(String userToken) {
		this.userToken = userToken;
    }

    public String getMatchings() {
    	String output;
    	try {
    		output = webTarget.path("api/matching").queryParam("token", userToken).request().get(String.class);
    	}
    	catch (ResponseProcessingException e) {
    		Response response = e.getResponse();
    		LOG.info("ResponseProcessingException returned Code: {}", Integer.toString(response.getStatus()));
    		output = "{}";
    	}
        return output;//getMatchingForJSONResponse(output);
    }
    
    public void pushRoute(String pushRoute) {
    	
    	try {
    		LOG.info("Trying to push by HTTP POST the route: {}", pushRoute);
    		webTarget.path("api/route").queryParam("token", userToken).request().post(Entity.text(pushRoute));
    	}
    	catch (ResponseProcessingException e) {
    		LOG.info("ResponseProcessingException returned Code: {}", Integer.toString(e.getResponse().getStatus()));
    	}
        //return output;//getMatchingForJSONResponse(output);
    }

    /*private String getMatchingForJSONResponse(String jsonString) {
        JsonObject jsonObject = JsonObject.readFrom(jsonString);
        String matching = jsonObject.asString();
        return matching;
    }*/

}
