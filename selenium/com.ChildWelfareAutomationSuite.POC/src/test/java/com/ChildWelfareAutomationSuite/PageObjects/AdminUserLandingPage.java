package com.ChildWelfareAutomationSuite.PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.ChildWelfareAutomationSuite.factory.BrowserFactory;


public class AdminUserLandingPage {

	WebDriver driver;

	public AdminUserLandingPage(WebDriver ldriver) {
		this.driver = ldriver;
	}
	
	// Login Page Objects Properties-Object Repository
	
	@FindBy(xpath = "//a[contains(.,'Publish New Notification')]")
	WebElement PubNewNotification;
	@FindBy(xpath = "//select[@id='eventTypeFilter']")
	WebElement evenTyFilter;
	@FindBy(xpath = "//select[@id='eventTimeFilter']")
	WebElement evenTimeFilter;
	public static @FindBy(xpath="//a[contains(.,'Logout')]")
	WebElement logout;
	
	
	
	
	// Enter Login Details
	public int verifyListItemsEventType() {
		Select oSelect = new Select(driver.findElement(By.xpath("//select[@id='eventTypeFilter']")));
		List <WebElement> elementCount = oSelect.getOptions();
		int iSize = elementCount.size();
		return iSize;
		
	}
	



	
	//click Logout
	public void clickLogout()
	{
		logout.click();
		
		
		
	}
	
	// Verify Error message
	public String VerifyPublishNotificationLink()

	{
		String PublishNotfiLink = PubNewNotification.getText();
		return PublishNotfiLink;

	}
	


	// Get Application URL
	public String getApplicationTittle() {
		return driver.getTitle();
	}

}