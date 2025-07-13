package requests;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the login data payload for authentication requests.
 * This class is used to serialize login credentials into JSON format
 * and map them to API request bodies.
 */
public class LoginData {

	@JsonProperty("username")
	private String userName;
	
	@JsonProperty("password")
	private String password;

	public LoginData(String userName, String password) {
		this.userName=userName;
		this.password=password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

}
