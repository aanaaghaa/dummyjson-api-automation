package com.anagha.api.dummyjson_api_test.reporting;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * Utility class to manage ExtentReports lifecycle and test logging
 * This class ensures thread-safe logging using {@link ThreadLocal}
 * and provides centralized methods to initialize, flush, and manage test instances.
 */
public class ExtentReportManager {
	
	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> test=new ThreadLocal<>();
	
	/*Initializes the ExtentReports object only once for the test run.
	 * Sets the report file path, theme, report name, and environment info.
	 */
	public static void initReports() throws IOException
	{
			//Helps create the report for the first time 
		  if (extent == null) {
			    String reportFolder = System.getProperty("user.dir") 
			        + File.separator + "test-output" 
			        + File.separator + "ExtentReports";
			    

			   String reportPath = reportFolder + File.separator + "ExtentReport.html";
			    ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
			    

					
				    // Configurations
				    reporter.config().setDocumentTitle("Dummy JSON's API Automation Report");
				    reporter.config().setReportName("Dummy JSON API Test Report");
				    reporter.config().setTheme(Theme.DARK);
				    reporter.config().setTimeStampFormat("MM-DD-YYYY HH:mm:ss");

				   extent = new ExtentReports();
				    extent.attachReporter(reporter);
				    
				    // Environment Info
				    extent.setSystemInfo("Tester", "Anagha");
				    extent.setSystemInfo("Environment", "QA");
				    extent.setSystemInfo("API Base URL", "https://dummyjson.com");
				    extent.setSystemInfo("Test Data Version", "v1.0");
				    extent.setSystemInfo("Device", System.getProperty("os.name") + " - " + System.getProperty("os.arch"));
				    extent.setSystemInfo("Java Version", System.getProperty("java.version"));
				}

	}
	
	/**
     * Flushes the Extent report, writing all test information to the HTML file.
     * Should be called once after all tests are executed.
     */
	public static void flushReports()
	{
		//Flushes the report at the end only if there are details in the report
		if(extent!=null)
		{
			extent.flush();
		}
	}

	//Creates a new test in the Extent report and associates it with the current thread.
	public static void createTest(String testName) {
		ExtentTest extentTest=extent.createTest(testName);
		test.set(extentTest);
	}
	
	//Retrieves the current thread’s ExtentTest instance for logging.
	public static ExtentTest getTest()
	{
		return test.get();
	}
	
	//Clears the thread-local ExtentTest instance to prevent memory leaks.
	public static void unload()
	{
		test.remove();
	}
	
	//Returns the singleton ExtentReports instance.
	public static ExtentReports getExtentReports() {
        return extent;
    }
}
