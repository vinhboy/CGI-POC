package com.MyCAlerts.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.MyCAlerts.factory.BrowserFactory;


public class LoginPage {

	WebDriver driver;

	public LoginPage(WebDriver ldriver) {
		this.driver = ldriver;
	}
	
	// Login Page Objects Properties-Object Repository
	@FindBy(id = "email")
	WebElement Username;
	@FindBy(id = "password")
	WebElement password;
	@FindBy(xpath = "//button[starts-with(@class,'usa-button-primary-alt usa-button-login ng-scope')]")
	WebElement LoginButton;
	@FindBy(xpath = "//a[@ui-sref='manageProfile']")
	WebElement LoginMesg;
	@FindBy(xpath = "//p[contains(.,'Incorrect username/password combination')]")
	WebElement InvalidLoginMesg;
	@FindBy(xpath="//span[contains(.,'Email is required')]")
	WebElement userNameErrText;
	@FindBy(xpath="//span[contains(.,'Password is required')]")
	WebElement passwordErrText;
	@FindBy(xpath="//a[contains(.,'Logout')]")
	WebElement logout;
	@FindBy(xpath = "//a[contains(.,'Publish New Notification')]")
	WebElement PubNewNotification;
	
	
	// Enter Login Details
	public void myAdtLoginEnterDetails(String username, String Password) {
		Username.sendKeys(username);
		password.sendKeys(Password);
		LoginButton.click();
		
	}
	
	//Enter Username
	public void EnterUsername(String username)
	{
		Username.sendKeys(username);
	}

	
	//Enter Username
	public void EnterPassword(String Password)
	{
		password.sendKeys(Password);
	}

	//click Submit
	public void clickLogin()
	{
		LoginButton.click();
		
		
	}
	
	//click Logout
	public void clickLogout()
	{
		logout.click();
		
		
	}
	
	// Verify Error message
	public String VerifyLoginMesg()

	{
		String loginMesg = PubNewNotification.getText();
		return loginMesg;

	}
	

	// Verify Error message
	public String VerifyLoginInvalidMesg()

	{
		String loginMesg = InvalidLoginMesg.getText();
		return loginMesg;

	}
	
	
	//Verify the Error message with blan username
	
	public String VerifyBlankUsernameErrorText()

	{
		String sblankUserErrorText = userNameErrText.getText();
		return sblankUserErrorText;

	}
	
	public String VerifyBlankPasswordErrorText()

	{
		String sblankpassErrorText = passwordErrText.getText();
		return sblankpassErrorText;

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