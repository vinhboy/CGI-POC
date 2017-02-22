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
	
	//DisAllow Login with Blank Username
	@Test(description = "Enter Blank Username & Verify the Error Mesg")

	public void TC02_DisallowLogin_2() {

		logger = report.startTest("TC02_LoginWithBlankUsername");
		logger.log(LogStatus.INFO, "Application is up and running");
		LoginPage login = PageFactory.initElements(driver, LoginPage.class);
		login.myAdtLoginEnterDetails("",DataProviderFactory.getExcel().getDatawithSheetIndex(0, 1, 1));
		BrowserFactory.waitFor();
		logger.log(LogStatus.INFO, "Username with Blank Value is Entered");
		String verifyMesg = login.VerifyBlankUsernameErrorText();
		//Verify Error Message of Login
		Assert.assertTrue(verifyMesg.contains("Email is required"));
		logger.log(LogStatus.PASS, "Error Text is displayed with blank username");
		logger.log(LogStatus.INFO,logger.addScreenCapture(GenericFunctions.CaptureScreenshot(driver, "TC02_DisallowLogin_2")));

	}
	

@Test(description = "Enter Blank Password & Verify the Error Mesg")
	public void TC02_DisallowLogin_3() {

		logger = report.startTest("TC02_LoginWithBlankPassword");
		logger.log(LogStatus.INFO, "Application is up and running");
		LoginPage login = PageFactory.initElements(driver, LoginPage.class);
		login.myAdtLoginEnterDetails(DataProviderFactory.getExcel().getDatawithSheetIndex(0, 1, 0),"");
		BrowserFactory.waitFor();
		logger.log(LogStatus.INFO, "Password with Blank Value is Entered");
		String verifyMesg = login.VerifyBlankPasswordErrorText();
		//Verify Error Message of Login
		Assert.assertTrue(verifyMesg.contains("Password is required"));
		logger.log(LogStatus.PASS, "Error Text is displayed with blank username");
		logger.log(LogStatus.INFO,logger.addScreenCapture(GenericFunctions.CaptureScreenshot(driver, "TC02_DisallowLogin_3")));

	}
	
	
	
@Test(description = "Enter Blank Username & Password & Verify the Error Mesg")
public void TC02_DisallowLogin_4() {

	logger = report.startTest("TC02_LoginWithBlankPassword");
	logger.log(LogStatus.INFO, "Application is up and running");
	LoginPage login = PageFactory.initElements(driver, LoginPage.class);
	login.EnterUsername("");
	login.EnterPassword("");
	//login.clickLogin();
	String verifyMesg1 = login.VerifyBlankUsernameErrorText();
	//Verify the Blank Username Text Message
	Assert.assertTrue(verifyMesg1.contains("Email is required"));
	//Verify the Blank Password Text Message
	login.EnterPassword("");
	login.EnterUsername("");
	//login.clickLogin();
	String verifyMesg2 = login.VerifyBlankPasswordErrorText();
	Assert.assertTrue(verifyMesg2.contains("Password is required"));
	BrowserFactory.waitFor();
	logger.log(LogStatus.INFO, "Username & Password with Blank Value is Entered");
	logger.log(LogStatus.PASS, "Error Text is displayed for blank username and blank password");
	logger.log(LogStatus.INFO,logger.addScreenCapture(GenericFunctions.CaptureScreenshot(driver, "TC02_DisallowLogin_4")));

}
	
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
	
	@Test(description = "Verify User not able login on clicking back button after logout")

	public void TC02_NavigationManagementSession_2() {

		logger = report.startTest("TC05_VerifyUserLoginwithBackButton");
		logger.log(LogStatus.INFO, "Application is up and running on Chrome Browser");
		LoginPage login = PageFactory.initElements(driver, LoginPage.class);
		login.myAdtLoginEnterDetails(DataProviderFactory.getExcel().getDatawithSheetIndex(0,1, 0),DataProviderFactory.getExcel().getDatawithSheetIndex(0, 1, 1));
		BrowserFactory.waitFor();
		logger.log(LogStatus.INFO, "User Logged in to landing page");
		//Capture the Text of Login Message of Hitting Login Button
		String verifyMesg = login.VerifyLoginMesg();
		//Verify Success Message of Login
		Assert.assertTrue(verifyMesg.contains("Manage Profile"));
		login.clickLogout();
		BrowserFactory.clickBackButton();
		//Assert.assert(verifyMesg.contains("RETURN TO MAP"));
		logger.log(LogStatus.PASS, "User is Logout from application");
		//Capture the Screenshot
		logger.log(LogStatus.INFO, logger.addScreenCapture(GenericFunctions.CaptureScreenshot(driver, "TC02_NavigationManagementSession_2")));
	}
	
}
