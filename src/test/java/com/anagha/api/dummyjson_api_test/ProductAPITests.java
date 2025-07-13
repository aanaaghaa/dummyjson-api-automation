package com.anagha.api.dummyjson_api_test;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import base.BaseTest;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import requests.NewProductDetails;
import requests.ResponseHolder;
import requests.UpdateDetails;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

/**
 * End to end tests for DummyJSON “product” endpoints.
 *
 * Positive flows: List all products | Get product by valid ID | Add a new product | Update an existing product | Delete a product
 * Negative flow: Query a non existent product ID and expect 404
 * Responses are stored in ResponseHolder so that listeners or
 * other utility classes (e.g. screenshot / log attachments) can access them
 * thread safely after the test finishes
 */
public class ProductAPITests extends BaseTest{
 
	//-------------------------------------------POSITIVE TEST CASE---------------------------------------------
    /**
     * Verifies that the products endpoint returns 
     * HTTP 200 and a response body matching the JSON schema
     */
	@Test
	public void testGetAllProducts()
	{
		Response response=given()
				.contentType("application/json")
				.when()
				.get("/products")
				.then()
				.log().all()
				.statusCode(200)
				.body(matchesJsonSchemaInClasspath("schemas/all-products-schema.json"))
				.extract().response();

ResponseHolder.setResponse(response);
			}

	 /**
     * Verifies that each valid product ID supplied by validProductIds()
     * returns HTTP 200 and a body matching the JSON schema
     */
	@Test(dataProvider="validProductIds")
	public void testGetProductsById(Object productId)
	{
		this.response=given()
				.contentType("application/json")
				.when()
				.get("/product/" + productId)
				.then()
				.log().all()
				.statusCode(200)
				.body(matchesJsonSchemaInClasspath("schemas/product-schema.json"))
				.extract().response();
		ResponseHolder.setResponse(response);
	}
	
	/**
     * Deletes product 2 and stores the raw response.
     * The API currently allows DELETE without a body, the endpoint is
     * expected to return HTTP 204
     */
	@Test
	public void testdeleteProductById()
	{
		this.response = given()
			    .contentType("application/json")
			    .when()
			    .delete("/product/2")
			    .then()
			    .log().all()
			    .extract().response();

		ResponseHolder.setResponse(response);
		
	}
	/**
     * Updates product 3 with the supplied UpdateDetails
     * payload and expects HTTP 200.
     */
	@Test(dataProvider="updatedDetails")
	public void testUpdateProductDetail(UpdateDetails updateDetails)
	{
		
		ValidatableResponse validatableResponse=given()
				 .contentType("application/json")
				.body(updateDetails)
				.when()
				.put("/product/3")
				.then()
				.log().all()
				.statusCode(200);
		Response rawResponse = validatableResponse.extract().response();
		ResponseHolder.setResponse(rawResponse); 
		ResponseHolder.setValidateableResponse(validatableResponse);
		
	}
	
	// Adds a new product and expects HTTP 201.
	@Test(dataProvider="newProdDetails")
	public void testAddProduct(NewProductDetails productDetails)
	{
		ValidatableResponse validatableResponse = given()
			    .contentType("application/json")
			    .body(productDetails)
			    .when()
			    .post("/product/add")
			    .then()
			    .log().all()
			    .statusCode(201);

		Response rawResponse = validatableResponse.extract().response();
		ResponseHolder.setResponse(rawResponse); 
		ResponseHolder.setValidateableResponse(validatableResponse);
	
	}
	
	//---------------------------------NEGATIVE TEST CASE------------------------------------------------------
	
	//Attempts to fetch a non‑existent product and expects HTTP 404.
	@Test(dataProvider="invalidProductIds")
	public void testGetInvalidProduct(Object productId)
	{
		ValidatableResponse validatableResponse=given()
				.contentType("application/json")
				.when()
				.get("/product/" +productId)
				.then()
				.log().all()
				.statusCode(404);
		System.out.println(validatableResponse);
		Response rawResponse = validatableResponse.extract().response();
		System.out.println("Response body: " + rawResponse.asString());
		ResponseHolder.setResponse(rawResponse); 
		ResponseHolder.setValidateableResponse(validatableResponse);
		
	}

	//----------------------------------DATA PROVIDERS---------------------------------------------------------
	
	//Provides a set of valid IDs that should exist in the system. 
	@DataProvider(name="validProductIds")
	public Object[][] dataProviderofValidProductIds()
	{
		return new Object[][] {{1},{3},{5},{10}};
	}
	
	//Provides IDs that are guaranteed not to exist
	@DataProvider(name="invalidProductIds")
	public Object[][] dataProviderforInvalidProductIds()
	{
		return new Object[][] {{1111}, {234}, {987}};
	}
	
	//Data for updating the product details
	@DataProvider(name="updatedDetails")
	public Object[][] dataProviderforupdatedProductDetails()
	{
	 return new Object[][] {{new UpdateDetails(1115, "Powder Canister(New Price)")}};
	}
	
	//Data provider for adding a new product 
	@DataProvider(name="newProdDetails")
	public Object[][] dataProviderfornewProductDetails()
	{
		return new Object[][] {
			{new NewProductDetails(999, "Face Cream", 980, 4, 10, "VA", "Beauty")}
			};
	}

}
