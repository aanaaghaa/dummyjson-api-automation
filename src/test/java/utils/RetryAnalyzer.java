package utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * RetryAnalyzer class used to automatically retry failed TestNG test cases.
 * This implementation retries a failed test up to a defined number of times
 */
public class RetryAnalyzer implements IRetryAnalyzer{
private int retryCount=0;
private static final int maxRetryCount=2;

	//This method decides whether the failed test needs to be retried.
	@Override
	public boolean retry(ITestResult result) {
		if(retryCount<maxRetryCount)
		{
			retryCount++;
			return true;
		}
		return false;
	}

}
