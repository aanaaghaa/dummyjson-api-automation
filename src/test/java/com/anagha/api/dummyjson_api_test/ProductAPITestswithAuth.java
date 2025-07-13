package com.anagha.api.dummyjson_api_test;
import org.testng.annotations.Test;

import base.BaseTest;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import requests.LoginData;
import requests.ResponseHolder;
import utils.AuthUtil;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;

/**
 * Product API tests with Bearer Token Authorization.
 * This class tests protected endpoints that require login using 
 * token-based authentication. The credentials are passed using a 
 * TestNG DataProvider from AuthUtil
 */
public class ProductAPITestswithAuth  extends BaseTest{
	
	/**
     * Test to fetch the list of products using Bearer token-based authentication.
     * Accepts login credentials from AuthUtil class loginCreds()
     * Fetches token via AuthUtil class getAuthToken(LoginData)
     * Sends GET request to /products with Bearer token
     * Validates that: HTTP response is 200 | Product list size is greater than 0
     */
	@Test(dataProvider = "loginCreds", dataProviderClass = AuthUtil.class)
	public static void testGetProductWithAuth(LoginData login)
	{
		String token=AuthUtil.getAuthToken(login);
		ValidatableResponse validatableResponse=given()
				.header("Authorization", "Bearer" +token)
				.contentType("application/json")
				.when()
				//Since BaseTest is the parent class, gets the URL automatically
				.get("/products")
				.then()
				.log().all()
				.statusCode(200)
				.body("products.size()", greaterThan(0));
		
		Response rawResponse = validatableResponse.extract().response();
		ResponseHolder.setResponse(rawResponse); 
		ResponseHolder.setValidateableResponse(validatableResponse);
	}
}
