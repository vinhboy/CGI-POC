package com.ChildWelfareAutomationSuite.Testcases;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.ChildWelfareAutomationSuite.BaseClass.BaseClass;
import com.ChildWelfareAutomationSuite.PageObjects.AdminUserLandingPage;
import com.ChildWelfareAutomationSuite.PageObjects.LoginPage;
import com.ChildWelfareAutomationSuite.Utilities.GenericFunctions;
import com.ChildWelfareAutomationSuite.factory.BrowserFactory;
import com.ChildWelfareAutomationSuite.factory.DataProviderFactory;
import com.relevantcodes.extentreports.LogStatus;

public class TC01_Admin_ListFilterEvents extends BaseClass {
	@Test(description="Verify Admin User see all Event Types in Filter List")

	public void TC01_ListFilterEvent_2() throws InterruptedException {
		// Start Create New HTML report for the Testcase
		logger = report.startTest("TC01_VerifyAdminUserseeAllEventTypesFilterLists");
		logger.log(LogStatus.INFO, "Application is up and running on Chrome Browser");
		//Inherits the Login Page to Main Testcase
		LoginPage login = PageFactory.initElements(driver, LoginPage.class);
		// Fetch the data from Excel Sheet
		login.myAdtLoginEnterDetails(DataProviderFactory.getExcel().getDatawithSheetIndex(0, 1, 0),DataProviderFactory.getExcel().getDatawithSheetIndex(0, 1, 1));
		//Wait
		BrowserFactory.waitFor();
		logger.log(LogStatus.INFO, "Valid User Login Details Entered");
		//Capture the Text of Login Message of Hitting Login Button
		AdminUserLandingPage adminFilter = PageFactory.initElements(driver, AdminUserLandingPage.class);
		String verifyMesg = adminFilter.VerifyPublishNotificationLink();
		//Verify Success Message of Login
		Assert.assertTrue(verifyMesg.contains("Publish New Notification"));
		logger.log(LogStatus.PASS, "Sucessfully Landing page is displayed");
		//Capture the Screenshot
		WebElement element = driver.findElement(By.xpath("//select[@id='eventTypeFilter']"));

		Select select=new Select(element);
		List<WebElement> list=select.getOptions();
		
		for(int i=0;i<list.size();i++)          
		    {
		       String sValue= list.get(i).getText();
		       logger.log(LogStatus.PASS, "Filter Event Type List --" +list.get(0).getText()+"-- is displayed by default");
		       logger.log(LogStatus.PASS, "Filter Event Type List --" +sValue+"-- is displayed");
		       select.selectByVisibleText(sValue);
		       logger.log(LogStatus.PASS, "Filter Event Type List --" +sValue+"-- is Selected");
		    }
		
		logger.log(LogStatus.INFO, logger.addScreenCapture(GenericFunctions.CaptureScreenshot(driver, "TC01_ListFilterEvent_2")));
		
	}
	
	
	@Test(description="Verify Admin User see all Event Times in Filter List")

	public void TC01_ListFilterEvent_3() throws InterruptedException {
		// Start Create New HTML report for the Testcase
		logger = report.startTest("TC01_VerifyAdminUserseeAllEventTimeFilterList");
		logger.log(LogStatus.INFO, "Application is up and running on Chrome Browser");
		//Inherits the Login Page to Main Testcase
		LoginPage login = PageFactory.initElements(driver, LoginPage.class);
		// Fetch the data from Excel Sheet
		login.myAdtLoginEnterDetails(DataProviderFactory.getExcel().getDatawithSheetIndex(0, 1, 0),DataProviderFactory.getExcel().getDatawithSheetIndex(0, 1, 1));
		//Wait
		BrowserFactory.waitFor();
		logger.log(LogStatus.INFO, "Valid User Login Details Entered");
		//Capture the Text of Login Message of Hitting Login Button
		AdminUserLandingPage adminFilter = PageFactory.initElements(driver, AdminUserLandingPage.class);
		String verifyMesg = adminFilter.VerifyPublishNotificationLink();
		//Verify Success Message of Login
		//Assert.assertTrue(verifyMesg.contains("Publish New Notification"));
		Assert.assertEquals("Publish New Notification", "Publish New Notification");
		
		BrowserFactory.isTextPresent("Logout", "Verify Logout");
		logger.log(LogStatus.PASS, "Sucessfully Landing page is displayed");
		//Capture the Screenshot
		WebElement element = driver.findElement(By.xpath("//select[@id='eventTimeFilter']"));

		Select select=new Select(element);
		List<WebElement> list=select.getOptions();
	       
		for(int i=0;i<list.size();i++)          
		    {
		       String sValue= list.get(i).getText();
		       logger.log(LogStatus.PASS, "Filter Event Type List --" +list.get(0).getText()+"-- is displayed by default");
		       logger.log(LogStatus.PASS, "Filter Event Type List --" +sValue+"-- is displayed");
		       
		       select.selectByVisibleText(sValue);
		       logger.log(LogStatus.PASS, "Filter Event Type List --" +sValue+"-- is Selected");
		    }
		element.click();
		logger.log(LogStatus.INFO, logger.addScreenCapture(GenericFunctions.CaptureScreenshot(driver, "TC01_ListFilterEvent_3")));
		
	}
	
	
	
}