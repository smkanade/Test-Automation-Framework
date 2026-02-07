package com.utility;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.constants.Browser;

public abstract class BrowserUtility {
	Logger logger = LoggerUtility.getLogger(this.getClass());

	private static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

	public WebDriver getDriver() {
		return driver.get();
	}

	public BrowserUtility(WebDriver driver) {
		super();
		this.driver.set(driver);
	}

	public BrowserUtility(String browerName) {
		if (browerName.equalsIgnoreCase("chrome")) {
			driver.set(new ChromeDriver());
		} else if (browerName.equalsIgnoreCase("edge")) {
			driver.set(new EdgeDriver());
		} else if (browerName.equalsIgnoreCase("firefox"))
			driver.set(new FirefoxDriver());
		else {
			System.err.print("Invalid Browser Name ... Please select Chrome and Edge only");
		}
	}

	public BrowserUtility(Browser browerName) {
		logger.info("Initializing the WebDriver for the browser: " + browerName);
		if (browerName == Browser.CHROME) {
			driver.set(new ChromeDriver());
		} else if (browerName == Browser.FIREFOX) {
			driver.set(new FirefoxDriver());
		} else if (browerName == Browser.EDGE) {
			driver.set(new EdgeDriver());

		}
	}

	public BrowserUtility(Browser browerName, boolean isHeadless) {
		logger.info("Initializing the WebDriver for the browser: " + browerName);
		if (browerName == Browser.CHROME) {
			if (isHeadless) {
				logger.info("Running the browser in headless mode");
				ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions.addArguments("--headless");
				chromeOptions.addArguments("--window-size=1920,1080");
				driver.set(new ChromeDriver(chromeOptions));
			} else {
				driver.set(new ChromeDriver());
			}
		} else if (browerName == Browser.FIREFOX) {
			if (isHeadless) {
				logger.info("Running the browser in headless mode");
				org.openqa.selenium.firefox.FirefoxOptions firefoxOptions = new org.openqa.selenium.firefox.FirefoxOptions();
				firefoxOptions.addArguments("--headless");
				firefoxOptions.addArguments("--window-size=1920,1080");
				driver.set(new FirefoxDriver(firefoxOptions));
			} else	
				driver.set(new FirefoxDriver());
		} else if (browerName == Browser.EDGE) {
			if (isHeadless) {
				logger.info("Running the browser in headless mode");
				EdgeOptions edgeOptions = new EdgeOptions();
				edgeOptions.addArguments("--headless");
				edgeOptions.addArguments("--disable-gpu");
				edgeOptions.addArguments("--window-size=1920,1080");
				driver.set(new EdgeDriver(edgeOptions));
			} else {
				driver.set(new EdgeDriver());
			}
		}
	}

	public void goToWebsite(String url) {
		logger.info("Navigating to the URL: " + url);
		driver.get().get(url);
	}

	public void maximizeWindow() {
		logger.info("Maximizing the browser window");
		driver.get().manage().window().maximize();
	}

	public void clickOn(By locator) {
		logger.info("Clicking on the element with locator: " + locator);
		WebElement signInLinkWebElement = driver.get().findElement(locator);
		signInLinkWebElement.click();
	}

	public void enterText(By locator, String textToEnter) {
		logger.info("Entering the text: " + textToEnter + " in the element with locator: " + locator);
		WebElement element = driver.get().findElement(locator);
		element.sendKeys(textToEnter);

	}

	public String getVisibleText(By locator) {
		WebElement element = driver.get().findElement(locator);
		logger.info("Getting the visible text from the element with locator: " + element.getText());
		return element.getText();
	}

	public String takeScreenShot(String name) {
		TakesScreenshot takeScreenshot = (TakesScreenshot) driver.get();
		File screenshotData = takeScreenshot.getScreenshotAs(OutputType.FILE);
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH-mm-ss");
		String timestamp = dateFormat.format(date);

		String path = System.getProperty("user.dir") + "//screenshots//" + name + "_" + timestamp + ".png";
		File screenshotFile = new File(path);
		try {
			FileUtils.copyFile(screenshotData, screenshotFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
}
