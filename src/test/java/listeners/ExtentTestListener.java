package listeners;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.anagha.api.dummyjson_api_test.reporting.ExtentReportManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import requests.ResponseHolder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * TestNG listener implementation for managing ExtentReport lifecycle and logging test execution details.
 * Integrates with RestAssured response logging and supports test-specific details such as base URI,
 * parameters, request/response content, and failure messages.
 */
public class ExtentTestListener implements ITestListener{
	 
	    private static final Logger logger = LogManager.getLogger(ExtentTestListener.class);

	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		
		/**
	     * Invoked before the test suite starts.
	     * Initializes the ExtentReport instance.
	     */
	  @Override
	    public void onStart(ITestContext context) {
	        try {
				ExtentReportManager.initReports();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        logger.debug("ExtentReports initialized for test suite: " + context.getName());
	    }

	  /**
	     * Invoked after the test suite finishes.
	     * Flushes all data to the report.
	     */
	    @Override
	    public void onFinish(ITestContext context) {
	        try {
				ExtentReportManager.flushReports();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        logger.debug("ExtentReports flushed and test suite completed: " + context.getName());

	    }

	    /**
	     * Called when each test starts.
	     * Logs test method name and parameters to report and log file.
	     */
	    @Override
	    public void onTestStart(ITestResult result) {
	        ExtentReportManager.createTest(result.getMethod().getMethodName());
	        ExtentTest extentTest = ExtentReportManager.getTest();
	    	extentTest.log(Status.INFO, "Base URI: " + RestAssured.baseURI);
	        extentTest.log(Status.INFO, "Test Started: " + result.getMethod().getMethodName());
	        logger.debug("Test method [" + result.getMethod().getMethodName() + "] is starting with parameters: " + Arrays.toString(result.getParameters()));
	        extentTest.log(Status.INFO, "Test Start Time: " + dtf.format(now));
	    }

	    /**
	     * Called when a test passes successfully.
	     * Logs the response, product details (if available), and marks the test as passed.
	     */
	    @Override
	    public void onTestSuccess(ITestResult result) {
	      ExtentTest extentTest = ExtentReportManager.getTest();
	      
	      Response response = ResponseHolder.getResponse();
	      String responseBody = response.getBody().asPrettyString();
	      if (response != null) {
	    	    if (response.jsonPath().get("products") != null) {
	    	        List<Map<String, Object>> products = response.jsonPath().getList("products");
	    	        for (Map<String, Object> product : products) {
	    	            //extentTest.log(Status.INFO, "Product Title: " + product.get("title"));
	    	        	extentTest.log(Status.INFO, "Product Details::<br>ID: "+product.get("id")+" | Title: "+product.get("title")+" | Price: " + product.get("price")+" | Status Code: " + response.statusCode());
	    	        }
	    	    } else if (responseBody.contains("message")) {
	                extentTest.log(Status.INFO, "Response Message: " + response.jsonPath().getString("message"));
	                extentTest.log(Status.INFO, "Status Code: " + response.statusCode());
	                logger.debug("Response Body:\n" + response.getBody().asPrettyString());

	            } else {
	            	
	                extentTest.log(Status.INFO, "Product Details::<br>ID: "+response.jsonPath().get("id")+" | Title: "+response.jsonPath().getString("title")+" | Price: " + response.jsonPath().getString("price")+" | Status Code: " + response.statusCode());}
	      ExtentReportManager.getTest().pass("Test passed: "+ result.getMethod().getMethodName());
	      logger.debug("Response Body:\n" + response.getBody().asPrettyString());
	            }
	        ResponseHolder.clear(); 
	    }


	    /**
	     * Called when a test fails.
	     * Logs the failure reason, JSON parsing, and a sample JIRA link for traceability.
	     */
	    @Override
	    public void onTestFailure(ITestResult result) {
   	        ExtentTest extentTest = ExtentReportManager.getTest();
	        Response response = ResponseHolder.getResponse();
	        if (response != null && response.getContentType() != null && response.getContentType().contains("application/json")) {
	        	logger.debug("Test [" + result.getMethod().getMethodName() + "] failed with throwable: " + result.getThrowable());
	        	logger.debug("Checking response content type: " + (response != null ? response.getContentType() : "null"));
	            try {
	                if (response.jsonPath().get("products") != null) {
	                    List<Map<String, Object>> products = response.jsonPath().getList("products");
	                    for (Map<String, Object> product : products) {
	                        extentTest.log(Status.INFO, "Product Title: " + product.get("title"));
	                    }
	                } else if (response.jsonPath().get("message") != null) {
	                    extentTest.log(Status.INFO, "Response Message: " + response.jsonPath().getString("message"));
	                }
	            } catch (Exception e) {
	                extentTest.log(Status.WARNING, "Response is not in valid JSON format or JSON key missing.");
	                logger.debug("Exception occurred while parsing failed response: ", e);
	            }
	            extentTest.log(Status.INFO, "Status Code: " + response.statusCode());
	            logger.debug("Response Body:\n" + response.getBody().asPrettyString());
	        } else {
	            extentTest.log(Status.WARNING, "No response body or unsupported content type.");
	        }

	        ExtentReportManager.getTest().fail( "Test Failed: "+result.getThrowable());
	        ExtentReportManager.getTest().fail("Status Code: "+result.getStatus());
	        ExtentReportManager.getTest().fail("<a href='https://jira.example.com/browse/DEMO-123' target='_blank'>Click here to view issue</a>");
	        ResponseHolder.clear();
	    }

	    //Called when a test is skipped.
	    @Override
	    public void onTestSkipped(ITestResult result) {
	        ExtentReportManager.getTest().skip("Test skipped after retrying: "+ result.getMethod().getMethodName());
	        logger.debug("Test [" + result.getMethod().getMethodName() + "] skipped. Possibly due to retry or condition.");
	    }
}
