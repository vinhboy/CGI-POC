package com.ChildWelfareAutomationSuite.Testcases;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.ChildWelfareAutomationSuite.BaseClass.BaseClass;
import com.ChildWelfareAutomationSuite.PageObjects.UserProfile;
import com.ChildWelfareAutomationSuite.Utilities.GenericFunctions;
import com.ChildWelfareAutomationSuite.factory.BrowserFactory;
import com.ChildWelfareAutomationSuite.factory.DataProviderFactory;
import com.relevantcodes.extentreports.LogStatus;

public class TC01_UserProfile_AllowRegistration extends BaseClass {
	
	public void TC01_AllowRegistration_1() throws InterruptedException {
		// Start Create New HTML report for the Testcase
		logger = report.startTest("TC01_VerifyUserProfilePage");
		logger.log(LogStatus.INFO, "Application is up and running on Chrome Browser");
		//Inherits the Login Page to Main Testcase
		UserProfile userProfile = PageFactory.initElements(driver, UserProfile.class);
		//Click on the Create Profile
		userProfile.clickCreateNow();
		//Get the Text of User Profile in User Profile Page
		String verifyMesg = userProfile.VerifyUserProfilePage();
		//Verify User Profile Header
		Assert.assertTrue(verifyMesg.contains("User Profile"));
		logger.log(LogStatus.INFO, "User Profile Page is Sucessfully displayed");
		logger.log(LogStatus.INFO, logger.addScreenCapture(GenericFunctions.CaptureScreenshot(driver, "TC01_AllowRegistration_1")));
	}
	
	
	
	@Test(description="Verify Registration Sucess with Mandatory Fields with one Notification")

	public void TC01_AllowRegistration_2() throws InterruptedException {
		// Start Create New HTML report for the Testcase
		logger = report.startTest("TC01_AllowRegistrationwithMandatoryFields1Notification");
		logger.log(LogStatus.INFO, "Application is up and running on Chrome Browser");
		//Inherits the Login Page to Main Testcase
		UserProfile userProfile = PageFactory.initElements(driver, UserProfile.class);
		//Click on the Create Profile
		userProfile.clickCreateNow();
		//Get the Text of User Profile in User Profile Page
		String verifyMesg = userProfile.VerifyUserProfilePage();
		//Verify User Profile Header
		Assert.assertTrue(verifyMesg.contains("User Profile"));
		logger.log(LogStatus.INFO, "User Profile Page is Sucessfully displayed");
		// Fetch the data from Excel Sheet
		userProfile.EnterFistName(DataProviderFactory.getExcel().getDatawithSheetIndex(1, 1, 0));
		userProfile.EnterLastName(DataProviderFactory.getExcel().getDatawithSheetIndex(1, 1, 1));
		String semail=DataProviderFactory.getExcel().getDatawithSheetIndex(1, 1, 2);
		String sPassword=DataProviderFactory.getExcel().getDatawithSheetIndex(1, 1,3);
		String sConfirmPassword=DataProviderFactory.getExcel().getDatawithSheetIndex(1, 1, 4);
		String sZipcode =DataProviderFactory.getExcel().getNumericDatawithSheetIndex(1, 1, 5);		
		userProfile.enterMandatoryFieldsinUserProfile(semail,sPassword,sConfirmPassword,sZipcode,"email");
		//Wait
		BrowserFactory.waitFor();
		logger.log(LogStatus.INFO, "Required Fields for Registration are Entered");
		//Capture the Text of Login Message of Hitting Login Button
		//String verifyMesg = login.VerifyLoginMesg();
		//Verify Success Message of Login
		//Assert.assertTrue(verifyMesg.contains("Landing page"));
		//logger.log(LogStatus.PASS, "Sucessfully Landing page is displayed");
		//Capture the Screenshot
		logger.log(LogStatus.INFO, logger.addScreenCapture(GenericFunctions.CaptureScreenshot(driver, "TC_01_AllowLogin_2")));
	
}
	
	

	@Test(description="Verify Registration Sucess with Mandatory Fields with more than 1 Notification")

	public void TC01_AllowRegistration_3() throws InterruptedException {
		// Start Create New HTML report for the Testcase
		logger = report.startTest("TC01_AllowRegistrationwithMandatoryFieldsandMoreNotifications");
		logger.log(LogStatus.INFO, "Application is up and running on Chrome Browser");
		//Inherits the Login Page to Main Testcase
		UserProfile userProfile = PageFactory.initElements(driver, UserProfile.class);
		//Click on the Create Profile
		userProfile.clickCreateNow();
		//Get the Text of User Profile in User Profile Page
		String verifyMesg = userProfile.VerifyUserProfilePage();
		//Verify User Profile Header
		Assert.assertTrue(verifyMesg.contains("User Profile"));
		logger.log(LogStatus.INFO, "User Profile Page is Sucessfully displayed");
		userProfile.EnterFistName(DataProviderFactory.getExcel().getDatawithSheetIndex(1, 1, 0));
		userProfile.EnterLastName(DataProviderFactory.getExcel().getDatawithSheetIndex(1, 1, 1));
		String semail=DataProviderFactory.getExcel().getDatawithSheetIndex(1, 1, 2);
		String sPassword=DataProviderFactory.getExcel().getDatawithSheetIndex(1, 1,3);
		String sConfirmPassword=DataProviderFactory.getExcel().getDatawithSheetIndex(1, 1, 4);
		String sZipcode =DataProviderFactory.getExcel().getNumericDatawithSheetIndex(1, 1, 5);		
		userProfile.enterMandatoryFieldsinUserProfile(semail,sPassword,sConfirmPassword,sZipcode,"email");
		userProfile.checkNotification("sms");
		//Wait
		BrowserFactory.waitFor();
		logger.log(LogStatus.INFO, "Required Fields are Entered in Registration Page with more than one Notifications");
		//Capture the Text of Login Message of Hitting Login Button
		//String verifyMesg = login.VerifyLoginMesg();
		//Verify Success Message of Login
		//Assert.assertTrue(verifyMesg.contains("Landing page"));
		//logger.log(LogStatus.PASS, "Sucessfully Landing page is displayed");
		//Capture the Screenshot
		logger.log(LogStatus.INFO, logger.addScreenCapture(GenericFunctions.CaptureScreenshot(driver, "TC_01_AllowLogin_3")));
	
}

	@Test(description="Verify Registration Sucess with Mandatory Fields with FirstName & last Name Blank")

	public void TC01_AllowRegistration_4() throws InterruptedException {
		// Start Create New HTML report for the Testcase
		logger = report.startTest("TC01_AllowRegistrationwithMandatoryFieldsFirstandLastNameBlank");
		logger.log(LogStatus.INFO, "Application is up and running on Chrome Browser");
		//Inherits the Login Page to Main Testcase
		UserProfile userProfile = PageFactory.initElements(driver, UserProfile.class);
		//Click on the Create Profile
		userProfile.clickCreateNow();
		//Get the Text of User Profile in User Profile Page
		String verifyMesg = userProfile.VerifyUserProfilePage();
		//Verify User Profile Header
		Assert.assertTrue(verifyMesg.contains("User Profile"));
		logger.log(LogStatus.INFO, "User Profile Page is Sucessfully displayed");
		userProfile.EnterFistName("");
		userProfile.EnterLastName("");
		String semail=DataProviderFactory.getExcel().getDatawithSheetIndex(1, 1, 2);
		String sPassword=DataProviderFactory.getExcel().getDatawithSheetIndex(1, 1,3);
		String sConfirmPassword=DataProviderFactory.getExcel().getDatawithSheetIndex(1, 1, 4);
		String sZipcode =DataProviderFactory.getExcel().getNumericDatawithSheetIndex(1, 1, 5);		
		System.out.println("zipcode"+sZipcode);
		userProfile.enterMandatoryFieldsinUserProfile(semail,sPassword,sConfirmPassword,sZipcode,"email");
		userProfile.checkNotification("sms");
		//Wait
		BrowserFactory.waitFor();
		logger.log(LogStatus.INFO, "Required Fields are Entered in Registration Page with Last and First Name Blank");
		//Capture the Text of Login Message of Hitting Login Button
		//String verifyMesg = login.VerifyLoginMesg();
		//Verify Success Message of Login
		//Assert.assertTrue(verifyMesg.contains("Landing page"));
		//logger.log(LogStatus.PASS, "Sucessfully Landing page is displayed");
		//Capture the Screenshot
		logger.log(LogStatus.INFO, logger.addScreenCapture(GenericFunctions.CaptureScreenshot(driver, "TC_01_AllowLogin_4")));
	
}
	
	
}