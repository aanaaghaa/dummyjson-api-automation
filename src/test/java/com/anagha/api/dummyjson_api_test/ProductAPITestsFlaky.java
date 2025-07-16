package com.anagha.api.dummyjson_api_test;

import org.testng.annotations.Test;

import base.BaseTest;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import requests.ResponseHolder;
import utils.RetryAnalyzer;

/**
 * This test class verifies the behavior of a flaky or unstable endpoint.
 * The test uses RetryAnalyzer to automatically retry failed executions
 * up to a configured number of times, helping to reduce noise from transient failures.
 */
public class ProductAPITestsFlaky extends BaseTest{
	
	/**
     * Attempts to hit an intentionally misspelled endpoint productss
     * which is expected to fail initially and be retried.
     * The test uses a retry analyzer and expects status code 200,
     * though the endpoint is likely invalid and may return 404.
     * Adjust as per real flaky endpoint behavior.
     */
	@Test(retryAnalyzer=RetryAnalyzer.class)
	public void flakyTest()
	{
		ValidatableResponse validatableResponse=given()
				.when()
				.get("/productss")
				.then()
				.log().all()
				.statusCode(404);
		Response rawResponse = validatableResponse.extract().response();
		ResponseHolder.setResponse(rawResponse); 
		ResponseHolder.setValidateableResponse(validatableResponse);
		
	}

}
