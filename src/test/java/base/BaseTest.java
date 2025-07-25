package base;

import java.io.IOException;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.anagha.api.dummyjson_api_test.reporting.EmailReportSender;
import com.anagha.api.dummyjson_api_test.reporting.ExtentReportManager;
import com.aventstack.extentreports.ExtentReports;

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
	
	ExtentReports extent;

	@BeforeSuite
	public void setUp() throws IOException {
	    extent = ExtentReportManager.initReports();
	}
	
	 /**
     * Executed once after the entire test suite finishes.
     * Sends the generated Extent HTML report via email.
     */
	@AfterSuite
	public void tearDown() {
		 try {
		        ExtentReportManager.flushReports(); // This is **critical**
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		    }

		    String reportPath = System.getProperty("user.dir") + "/target/test-output/ExtentReport.html";
		    EmailReportSender.sendReport(reportPath);
	}


}
