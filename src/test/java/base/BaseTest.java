package base;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import com.anagha.api.dummyjson_api_test.reporting.EmailReportSender;
import io.restassured.RestAssured;
import io.restassured.response.Response;

/**
 * BaseTest class for all API test classes.
 * Provides reusable setup and teardown logic.
 */
public class BaseTest {
	protected Response response;

	/**
     * Executed before each @Test method.
     * Sets the base URI for the DummyJSON API.
     */
	@BeforeMethod
	public void getURL()
	{
		RestAssured.baseURI="https://dummyjson.com";
	} 
	
	 /**
     * Executed once after the entire test suite finishes.
     * Sends the generated Extent HTML report via email.
     */
	@AfterSuite
	public void tearDown() {
	    String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport.html";
	    EmailReportSender.sendReport(reportPath);
	}


}
