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

public class TC02_disAllowLogin extends BaseClass {
	@Test(description = "Verify login error message with nonregister user")

	public void TC02_DisallowLogin_1() {

		logger = report.startTest("TC01_VerifyErrorMessgNonRegisterUser");
		logger.log(LogStatus.INFO, "Application is up and running on Chrome Browser");
		LoginPage login = PageFactory.initElements(driver, LoginPage.class);
		login.myAdtLoginEnterDetails(DataProviderFactory.getExcel().getDatawithSheetIndex(0,1, 4),DataProviderFactory.getExcel().getDatawithSheetIndex(0, 1, 5));
		BrowserFactory.waitFor();
		logger.log(LogStatus.INFO, "Non Register Member Details are Enter");
		//Capture the Text of Login Message of Hitting Login Button
		String verifyMesg = login.VerifyLoginInvalidMesg();
		//Verify Error Message of Login
		Assert.assertTrue(verifyMesg.contains("Incorrect username/password combination"));
		logger.log(LogStatus.PASS, "Sucess Message is displayed");
		//Capture the Screenshot
		logger.log(LogStatus.INFO, logger.addScreenCapture(GenericFunctions.CaptureScreenshot(driver, "TC_02_DisAllowLogin_1")));
	}
	
	
	
//	//DisAllow Login with Blank Username
//	@Test(description = "Enter Blank Username & Verify the Error Mesg")
//
//	public void TC02_AllowLogin_2() {
//
//		logger = report.startTest("TC02_LoginWithBlankUsername");
//		logger.log(LogStatus.INFO, "Application is up and running");
//		LoginPage login = PageFactory.initElements(driver, LoginPage.class);
//		// login.myAdtLoginEnterDetails(DataProviderFactory.getExcel().getDatawithSheetIndex(0,
//		// 1, 0),DataProviderFactory.getExcel().getDatawithSheetIndex(0, 1, 1));
//		login.myAdtLoginEnterDetails("", "adminpw");
//		BrowserFactory.waitFor();
//		logger.log(LogStatus.INFO, "Username with Blank Value is Entered");
//		BrowserFactory.isTextNotPresent("Error Status", "Error Message is displayed");
//		logger.log(LogStatus.PASS, "Error Message is displayed");
//		logger.log(LogStatus.INFO,logger.addScreenCapture(GenericFunctions.CaptureScreenshot(driver, "LoginErrorMesgwithBlanUser")));
//
//	}
//	
//	@Test(description = "Enter Blank Password & Verify the Error Mesg")
//
//	public void TC02_AllowLogin_3() {
//
//		logger = report.startTest("TC03_LoginWithBlankPassword");
//		logger.log(LogStatus.INFO, "Application is up and running");
//		LoginPage login = PageFactory.initElements(driver, LoginPage.class);
//		// login.myAdtLoginEnterDetails(DataProviderFactory.getExcel().getDatawithSheetIndex(0,
//		// 1, 0),DataProviderFactory.getExcel().getDatawithSheetIndex(0, 1, 1));
//		login.myAdtLoginEnterDetails("admin@cgi.com", "");
//		BrowserFactory.waitFor();
//		logger.log(LogStatus.INFO, "Password with Blank Value is Entered");
//		BrowserFactory.isTextNotPresent("Error Status", "Error Message is displayed");
//		logger.log(LogStatus.PASS, "Error Message is displayed");
//		logger.log(LogStatus.INFO,logger.addScreenCapture(GenericFunctions.CaptureScreenshot(driver, "LoginErrorMesgwithBlanPassword")));
//
//	}
//	
//	@Test(description = "Enter Blank Username password & Verify the Error Mesg")
//
//	public void TC02_AllowLogin_4() {
//
//		logger = report.startTest("TC04_LoginWithBlankUsername");
//		logger.log(LogStatus.INFO, "Application is up and running");
//		LoginPage login = PageFactory.initElements(driver, LoginPage.class);
//		// login.myAdtLoginEnterDetails(DataProviderFactory.getExcel().getDatawithSheetIndex(0,
//		// 1, 0),DataProviderFactory.getExcel().getDatawithSheetIndex(0, 1, 1));
//		login.myAdtLoginEnterDetails("", "");
//		BrowserFactory.waitFor();
//		logger.log(LogStatus.INFO, "Username and password with Blank Values are Entered");
//		BrowserFactory.isTextNotPresent("Error Status", "Error Message is displayed");
//		logger.log(LogStatus.PASS, "Error Message is displayed");
//		logger.log(LogStatus.INFO,logger.addScreenCapture(GenericFunctions.CaptureScreenshot(driver, "LoginErrorMesgwithBlankUsernamePassword")));
//
//	}

	@Test(description = "Verify Error Text Invalid Password")

	public void TC02_DisallowLogin_5() {

		logger = report.startTest("TC05_VerifyInvalidPassword");
		logger.log(LogStatus.INFO, "Application is up and running on Chrome Browser");
		LoginPage login = PageFactory.initElements(driver, LoginPage.class);
		login.myAdtLoginEnterDetails(DataProviderFactory.getExcel().getDatawithSheetIndex(0,1, 0),DataProviderFactory.getExcel().getDatawithSheetIndex(0, 1, 5));
		BrowserFactory.waitFor();
		logger.log(LogStatus.INFO, "Invalid Password details are Enter");
		//Capture the Text of Login Message of Hitting Login Button
		String verifyMesg = login.VerifyLoginInvalidMesg();
		//Verify Error Message of Login
		Assert.assertTrue(verifyMesg.contains("Incorrect username/password combination"));
		logger.log(LogStatus.PASS, "Error Message is displayed");
		//Capture the Screenshot
		logger.log(LogStatus.INFO, logger.addScreenCapture(GenericFunctions.CaptureScreenshot(driver, "TC02_DisallowLogin_5")));
	}
	
}
