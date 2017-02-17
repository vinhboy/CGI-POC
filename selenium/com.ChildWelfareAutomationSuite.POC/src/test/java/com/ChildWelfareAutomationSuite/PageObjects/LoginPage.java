package com.ChildWelfareAutomationSuite.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ChildWelfareAutomationSuite.factory.BrowserFactory;


public class LoginPage {

	WebDriver driver;

	public LoginPage(WebDriver ldriver) {
		this.driver = ldriver;
	}
	// Login Page Objects Properties-Object Repository
	@FindBy(id = "username")
	WebElement Username;
	@FindBy(id = "password")
	WebElement password;
	@FindBy(xpath = "//input[@value='LOGIN']")
	WebElement LoginButton;
	@FindBy(xpath = "//h3[contains(.,'Success')]")
	WebElement LoginMesg;
	@FindBy(xpath = "//p[contains(.,'Incorrect username/password combination')]")
	WebElement InvalidLoginMesg;

	
	
	// Enter Login Details
	public void myAdtLoginEnterDetails(String username, String Password) {
		Username.sendKeys(username);
		password.sendKeys(Password);
		LoginButton.click();
		
	}

	// Verify Error message
	public String VerifyLoginMesg()

	{
		String loginMesg = LoginMesg.getText();
		return loginMesg;

	}
	

	// Verify Error message
	public String VerifyLoginInvalidMesg()

	{
		String loginMesg = InvalidLoginMesg.getText();
		return loginMesg;

	}
	
	
	public boolean VerifyPasswordEncrpt()
	{
		WebElement input = driver.findElement(By.name("password"));
		boolean isEncrypted = input.getAttribute("type").equals("password");
		return isEncrypted;
	}

	// Get Application URL
	public String getApplicationTittle() {
		return driver.getTitle();
	}

}