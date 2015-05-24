package de.visiom.carpc.services.weather.helpers;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import com.eclipsesource.json.JsonObject;

/**
 * Helper class that sends requests to the WhoElse service
 * 
 */
public class WhoElseSignupClient {
	private final WebTarget webTarget = ClientBuilder.newClient().target("http://localhost:666").path("whoelse/signup");//.queryParam("units", "metric");
	
	private String username;
	private String password;
	private String name;
	private String surname;
	private String email;
	private String phone;
	private String age;
	
	public WhoElseSignupClient(String username, String password, 
			String name, String surname, 
			String email, String phone, String age) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.phone = phone;
		this.age = age;
	}

    public void trySignup() {
        String output = webTarget.queryParam("username", username)
        		.queryParam("password", password)
        		.queryParam("name", name)
        		.queryParam("surname", surname)
        		.queryParam("email", email)
        		.queryParam("phone", phone)
        		.queryParam("age", age)
        		.request().get(String.class);
        //return getLoginStatusFromJSONResponse(output);
        getSignupStatusFromJSONResponse(output);
    }

    private void getSignupStatusFromJSONResponse(String jsonString) {
        JsonObject jsonObject = JsonObject.readFrom(jsonString);
        JsonObject main = jsonObject.get("main").asObject();
        
        if (main.get("status").asString() == SignupStatus.SIGNUP_OK.name()) {
        	// TODO: throw event to report successful login
        	return ;
        }
    }

}

