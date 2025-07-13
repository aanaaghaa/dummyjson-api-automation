package utils;

import base.BaseTest;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import org.testng.annotations.DataProvider;

import requests.*;

/**
 *  Utility class for handling authentication related operations.
* Provides:
* A method to fetch a Bearer token from /auth/login
* A TestNG DataProvider that supplies login credentials
*/
public class AuthUtil extends BaseTest{
	
	//Sends a POST request to /auth/login and returns the Bearer token.
	public static String getAuthToken(LoginData login) {
	
		Response response=given()
				.contentType("application/json")
				.body(login)
				.when()
				.post("/auth/login")
				.then()
				 .log().all() 
				.statusCode(200)
				.extract().response();
		System.out.println(response);
		return response.jsonPath().getString("token");
	}
	
	//Provides login credential objects for tests that require authentication.
	@DataProvider(name="loginCreds")
public Object[][] loginCredentials()
{
	return new Object[][] {
		{new LoginData("emilys", "emilyspass")}
		
};
}
}