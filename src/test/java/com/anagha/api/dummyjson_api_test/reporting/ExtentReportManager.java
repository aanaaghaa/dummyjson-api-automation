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
	public static ExtentReports initReports() throws IOException
	{
			//Helps create the report for the first time 
		  if (extent == null) {
			  System.out.println("Project Dir in Jenkins: " + System.getProperty("user.dir"));

			    String reportFolder = System.getProperty("user.dir") 
			        + File.separator +"target" 
			    	+ File.separator + "test-output";

			    new File(reportFolder).mkdirs();
			    String reportPath = reportFolder+ File.separator + "ExtentReport.html";
			    Path reportFilePath = Paths.get(reportPath);
			    if (Files.exists(reportFilePath) && Files.isDirectory(reportFilePath)) {
			        // Delete the directory and its contents
			        Files.walk(reportFilePath)
			             .map(Path::toFile)
			             .sorted((a, b) -> -a.compareTo(b)) // Delete children before parent
			             .forEach(File::delete);
			    }
			    ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
			    
			    extent = new ExtentReports();
			    extent.attachReporter(reporter);

				    // Configurations
				    reporter.config().setDocumentTitle("Dummy JSON's API Automation Report");
				    reporter.config().setReportName("Dummy JSON API Test Report");
				    reporter.config().setTheme(Theme.DARK);
				    reporter.config().setTimeStampFormat("MM-DD-YYYY HH:mm:ss");

				   
				    
				    // Environment Info
				    extent.setSystemInfo("Tester", "Anagha");
				    extent.setSystemInfo("Environment", "QA");
				    extent.setSystemInfo("API Base URL", "https://dummyjson.com");
				    extent.setSystemInfo("Test Data Version", "v1.0");
				    extent.setSystemInfo("Device", System.getProperty("os.name") + " - " + System.getProperty("os.arch"));
				    extent.setSystemInfo("Java Version", System.getProperty("java.version"));
				}
		return extent;

	}
	
	/**
     * Flushes the Extent report, writing all test information to the HTML file.
     * Should be called once after all tests are executed.
	 * @throws InterruptedException 
     */
	public static void flushReports() throws InterruptedException
	{
		//Flushes the report at the end only if there are details in the report
		if(extent!=null)
		{
			//Thread.sleep(1000);
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
