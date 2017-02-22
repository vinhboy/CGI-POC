package com.ChildWelfareAutomationSuite.Testcases;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ChildWelfareAutomationSuite.BaseClass.BaseClass;
import com.ChildWelfareAutomationSuite.PageObjects.LoginPage;
import com.ChildWelfareAutomationSuite.Utilities.GenericFunctions;
import com.ChildWelfareAutomationSuite.factory.BrowserFactory;
import com.ChildWelfareAutomationSuite.factory.DataProviderFactory;
import com.relevantcodes.extentreports.LogStatus;

public class TC01_LoginFlow extends BaseClass {
	@Test(description="Verify login success message with Admin user")

	public void TC01_AllowLogin_2() throws InterruptedException {
		// Start Create New HTML report for the Testcase
		logger = report.startTest("TC01_VerifyAdminUserLandingPage");
		logger.log(LogStatus.INFO, "Application is up and running on Chrome Browser");
		//Inherits the Login Page to Main Testcase
		LoginPage login = PageFactory.initElements(driver, LoginPage.class);
		// Fetch the data from Excel Sheet
		login.myAdtLoginEnterDetails(DataProviderFactory.getExcel().getDatawithSheetIndex(0, 1, 0),DataProviderFactory.getExcel().getDatawithSheetIndex(0, 1, 1));
		//Wait
		BrowserFactory.waitFor();
		logger.log(LogStatus.INFO, "Valid User Login Details Entered");
		//Capture the Text of Login Message of Hitting Login Button
		String verifyMesg = login.VerifyLoginMesg();
		//Verify Success Message of Login
		Assert.assertTrue(verifyMesg.contains("Manage Profile"));
		logger.log(LogStatus.PASS, "Sucessfully Landing page is displayed");
		//Capture the Screenshot
		logger.log(LogStatus.INFO, logger.addScreenCapture(GenericFunctions.CaptureScreenshot(driver, "TC_01_AllowLogin_2")));
		
		
	}
	
	
	
	@Test(description="Verify login success message with Registered user")

	public void TC01_AllowLogin_1() throws InterruptedException {
		// Start Create New HTML report for the Testcase
		logger = report.startTest("TC01_VerifyUserLandingPage");
		logger.log(LogStatus.INFO, "Application is up and running on Chrome Browser");
		//Inherits the Login Page to Main Testcase
		LoginPage login = PageFactory.initElements(driver, LoginPage.class);
		// Fetch the data from Excel Sheet
		login.myAdtLoginEnterDetails(DataProviderFactory.getExcel().getDatawithSheetIndex(0, 1, 2),DataProviderFactory.getExcel().getDatawithSheetIndex(0, 1, 3));
		//Wait
		BrowserFactory.waitFor();
		logger.log(LogStatus.INFO, "Valid User Login Details Entered");
		//Capture the Text of Login Message of Hitting Login Button
		String verifyMesg = login.VerifyLoginInvalidMesg();
		System.out.println("Verify Error Mesage"+verifyMesg);
		//Verify Error Message of Login
		Assert.assertTrue(verifyMesg.contains("Incorrect username/password combination"));
		logger.log(LogStatus.PASS, "Error Message is displayed as "+verifyMesg);
		//Capture the Screenshot
		logger.log(LogStatus.INFO, logger.addScreenCapture(GenericFunctions.CaptureScreenshot(driver, "TC_01_AllowLogin_1")));
		
	}
	
}