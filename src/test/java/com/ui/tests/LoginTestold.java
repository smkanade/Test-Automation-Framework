package com.ui.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.ui.pages.HomePage;
import com.utility.BrowserUtility;

public class LoginTestold {

	public static void main(String[] args) {
		WebDriver driver = new ChromeDriver();
		
//		HomePage homePage = new HomePage(driver);
//		homePage.goToWebsite("https://automationpractice.techwithjatin.com/");
//		homePage.goToLoginPage();
		
	/*	BrowserUtility browserUtility = new BrowserUtility(driver);
		browserUtility.goToWebsite("https://automationpractice.techwithjatin.com/");
		browserUtility.maximizeWindow();

		By signInLinkLocator = By.xpath("//a[contains(text(),\"Sign in\")]");
		browserUtility.clickOn(signInLinkLocator);
	
		
		By emailTextBoxLocator = By.id("email");
		browserUtility.enterText(emailTextBoxLocator, "watisiv839@dnsclick.com");
		
		By passwordTextBoxLocator = By.id("passwd");
		browserUtility.enterText(passwordTextBoxLocator, "password123");
		
		By submitLoginButtonLocator = By.id("SubmitLogin");
		browserUtility.clickOn(submitLoginButtonLocator);*/
	}

}
