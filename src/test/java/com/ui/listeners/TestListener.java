package com.ui.listeners;

import java.util.Arrays;

import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.ui.tests.TestBase;
import com.utility.BrowserUtility;
import com.utility.ExtentReporterUtility;
import com.utility.LoggerUtility;

public class TestListener implements ITestListener {
	Logger logger = LoggerUtility.getLogger(this.getClass());

	ExtentSparkReporter extentSparkReporter; // Job - functionality to create HTML file and write the test execution
												// information into it
	ExtentReports extentReports; // Job - functionality to create test, assign test details and generate the
									// report. Heavy lifting is done by this class
	ExtentTest extentTest; // Job - functionality to assign the test details like test name, description,
							// category, status, logs, screenshots etc. for each test case

	BrowserUtility browserUtility;
	// ThreadLocal is used to make the ExtentTest instance thread safe. It ensures that each test case running in parallel gets its own instance of ExtentTest and there is no interference between them.
	// This is important because when tests are executed in parallel, multiple threads may try to access and modify the same ExtentTest instance, which can lead to incorrect reporting and data corruption. By using ThreadLocal, we can ensure that each thread has its own instance of ExtentTest, allowing for accurate and reliable reporting even when tests are run concurrently.
	// isolation of test cases when executed in parallel
	public void onTestStart(ITestResult result) {

		logger.info(result.getMethod().getMethodName());
		logger.info(result.getMethod().getDescription());
		logger.info(Arrays.toString(result.getMethod().getGroups()));
		//extentTest = extentReports.createTest(result.getMethod().getMethodName(), result.getMethod().getDescription());
		ExtentReporterUtility.createExtentTest(result.getMethod().getMethodName());
	}

	public void onTestSuccess(ITestResult result) {
		logger.info(result.getMethod().getMethodName() + " " + "is passed");
		//extentTest.log(Status.PASS, result.getMethod().getMethodName() + " " + "is passed");
		ExtentReporterUtility.getTest().log(Status.PASS, result.getMethod().getMethodName() + " " + "is passed");
	}

	public void onTestFailure(ITestResult result) {
		logger.error(result.getMethod().getMethodName() + " " + "is failed");
		logger.error(result.getThrowable());
		//extentTest.log(Status.FAIL, result.getMethod().getMethodName() + " " + "is failed");
		ExtentReporterUtility.getTest().log(Status.FAIL, result.getMethod().getMethodName() + " " + "is failed");
		ExtentReporterUtility.getTest().log(Status.FAIL, result.getThrowable().getMessage());
		
		Object testclass = result.getInstance();
		BrowserUtility browserUtility = ((TestBase)testclass).getInstance();
		logger.info("Capturing the screenshot for the failed test case: " + result.getMethod().getMethodName());
		String screenshotPath = browserUtility.takeScreenShot(result.getMethod().getMethodName());
		logger.info("Screenshot is captured and stored at the location: " + screenshotPath);
		ExtentReporterUtility.getTest().addScreenCaptureFromPath(screenshotPath);
	}

	public void onTestSkipped(ITestResult result) {
		logger.warn(result.getMethod().getMethodName() + " " + "is skipped");
		logger.info(result.getThrowable().getMessage());
		//extentTest.log(Status.SKIP, result.getMethod().getMethodName() + " " + "is skipped");
		ExtentReporterUtility.getTest().log(Status.SKIP, result.getMethod().getMethodName() + " " + "is skipped");
	}

	public void onStart(ITestContext context) {
		logger.info("Test suite is started");
		ExtentReporterUtility.setupSparkReporter("report.html");
	}

	public void onFinish(ITestContext context) {
		logger.info("Test suite is finished");
		ExtentReporterUtility.flushReport();
		}

}
