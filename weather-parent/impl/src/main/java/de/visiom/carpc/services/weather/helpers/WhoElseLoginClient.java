package de.visiom.carpc.services.weather.helpers;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import com.eclipsesource.json.JsonObject;

/**
 * Helper class that sends requests to the WhoElse service
 * 
 */
public class WhoElseLoginClient {
	private final WebTarget webTarget = ClientBuilder.newClient()
			.target("http://localhost:666").path("whoelse/login");// .queryParam("units","metric");

	private String jsonLoginRequest;

	public WhoElseLoginClient(String jsonLoginRequest) {
		this.jsonLoginRequest = jsonLoginRequest;
	}

	public void tryLogin() {
		String output = webTarget.queryParam("loginRequest", jsonLoginRequest)
				.request().get(String.class);
		// return getLoginStatusFromJSONResponse(output);
		sendLoginStatusFromJSONResponse(output);
	}

	private void sendLoginStatusFromJSONResponse(String jsonString) {
		JsonObject jsonObject = JsonObject.readFrom(jsonString);
		JsonObject main = jsonObject.get("main").asObject();
		
		
		//return main.get("temp").asDouble();
		// TODO: throw event to report successful login
		/*ValueObject valueObject = StringValueObject.valueOf(    jsonString     );
		ValueChangeEvent valueChangeEvent = ValueChangeEvent
				.createValueChangeEvent(remoteTemperatureParameter, valueObject);
		eventPublisher.publishValueChange(valueChangeEvent);*/

	}

}
