package com.ui.tests;

import static com.constants.Browser.CHROME;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.constants.Browser;
import com.ui.pages.HomePage;
import com.utility.BrowserUtility;
import com.utility.LambdaTestUtility;
import com.utility.LoggerUtility;

public class TestBase {

	protected HomePage homePage;
	Logger logger = LoggerUtility.getLogger(this.getClass());
	private boolean isLambdaTest ; //= false; // Set this flag to true if you want to run tests on LambdaTest, otherwise set
											// it to false for local execution
	@Parameters({"browser", "isLambdaTest", "isHeadless"})
	@BeforeMethod(description = "Load the Home Page of the website")
	public void setUp(@Optional("chrome") String browser, 
					  @Optional("false") boolean isLambdaTest, 
					  @Optional("true") boolean isHeadless, ITestResult result) {
		this.isLambdaTest = isLambdaTest;
		WebDriver lambdaDriver;
		if (isLambdaTest) {
			logger.info("Initializing the LambdaTest session and loading the Home Page of the website");
			lambdaDriver = LambdaTestUtility.initializeLambdaTestSession(browser, result.getMethod().getMethodName());
			homePage = new HomePage(lambdaDriver);	
			} else {
					logger.info("Loading the Home Page of the website");
					homePage = new HomePage(Browser.valueOf(browser.toUpperCase()), isHeadless);
				}
	}

	public BrowserUtility getInstance() {
		return homePage;
	}

	@AfterMethod(description = "Close the browser and quit the WebDriver session")
	public void tearDown() {
		if (isLambdaTest) {
			logger.info("Quitting the LambdaTest session");
			LambdaTestUtility.quitLambdaTestSession();
			return;
		} else {
				logger.info("Closing the browser and quitting the WebDriver session");
				homePage.getDriver().quit();
				}
	}
}
