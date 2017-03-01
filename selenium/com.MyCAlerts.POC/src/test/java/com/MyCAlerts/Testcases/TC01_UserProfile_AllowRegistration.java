package com.MyCAlerts.Testcases;

import java.util.UUID;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.MyCAlerts.BaseClass.BaseClass;
import com.MyCAlerts.PageObjects.UserProfile;
import com.MyCAlerts.Utilities.GenericFunctions;
import com.MyCAlerts.factory.BrowserFactory;
import com.MyCAlerts.factory.DataProviderFactory;
import com.relevantcodes.extentreports.LogStatus;

public class TC01_UserProfile_AllowRegistration extends BaseClass {
	
	@Test(description="Verify User Profile Page is displayed")
	
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
		Assert.assertTrue(verifyMesg.contains("Your Information"));
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
		Assert.assertTrue(verifyMesg.contains("Your Information"));
		logger.log(LogStatus.INFO, "User Profile Page is Sucessfully displayed");
		// Fetch the data from Excel Sheet
		userProfile.EnterFistName(DataProviderFactory.getExcel().getDatawithSheetIndex(1, 1, 0));
		userProfile.EnterLastName(DataProviderFactory.getExcel().getDatawithSheetIndex(1, 1, 1));
		String semail=(DataProviderFactory.getExcel().getDatawithSheetIndex(1, 1, 0))+UUID.randomUUID().toString() + "@mailinator.com";
		String sPassword=DataProviderFactory.getExcel().getDatawithSheetIndex(1, 1,3);
		String sConfirmPassword=DataProviderFactory.getExcel().getDatawithSheetIndex(1, 1, 4);
		String sZipcode =DataProviderFactory.getExcel().getNumericDatawithSheetIndex(1, 1, 5);	
		
		userProfile.enterMandatoryFieldsinUserProfile(semail,sPassword,sConfirmPassword,sZipcode,"email");
		//Wait
		BrowserFactory.waitFor();
		logger.log(LogStatus.INFO, "Required Fields for Registration are Entered");
		userProfile.clickSaveProfile();
		BrowserFactory.waitFor();
		
		//Capture the Text of Notification message
		String verifyNotifyMesg = userProfile.VerifyNotficationMesg();
		//Verify Notification text
		Assert.assertEquals(verifyNotifyMesg, "Event Notifications");
		logger.log(LogStatus.PASS, "Sucessfully Landing page is displayed");
		//Capture the Screenshot
		logger.log(LogStatus.INFO, logger.addScreenCapture(GenericFunctions.CaptureScreenshot(driver, "TC01_AllowRegistration_2")));
	
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
		Assert.assertTrue(verifyMesg.contains("Your Information"));
		logger.log(LogStatus.INFO, "User Profile Page is Sucessfully displayed");
		userProfile.EnterFistName(DataProviderFactory.getExcel().getDatawithSheetIndex(1, 1, 0));
		userProfile.EnterLastName(DataProviderFactory.getExcel().getDatawithSheetIndex(1, 1, 1));
		String semail=(DataProviderFactory.getExcel().getDatawithSheetIndex(1, 1, 0))+UUID.randomUUID().toString() + "@mailinator.com";
		String sPassword=DataProviderFactory.getExcel().getDatawithSheetIndex(1, 1,3);
		String sConfirmPassword=DataProviderFactory.getExcel().getDatawithSheetIndex(1, 1, 4);
		String sZipcode =DataProviderFactory.getExcel().getNumericDatawithSheetIndex(1, 1, 5);		
		userProfile.enterMandatoryFieldsinUserProfile(semail,sPassword,sConfirmPassword,sZipcode,"email");
		userProfile.checkNotification("sms");
		//Wait
		BrowserFactory.waitFor();
		logger.log(LogStatus.INFO, "Required Fields are Entered in Registration Page with more than one Notifications");
		userProfile.clickSaveProfile();
		
		userProfile.onMousePhone();
		String verInvPhNum = userProfile.VerifyinvalidPhNumErrorMesgProfilePage();
		//Verify Invalid Phone Error Mesg in Profile Page
		Assert.assertTrue(verInvPhNum.contains("You must enter a valid phone number"));
		//Wait
		logger.log(LogStatus.PASS, "Error Message is display "+verInvPhNum);
		//ENter the Phone Number
		String svalidPhone=DataProviderFactory.getExcel().getNumericDatawithSheetIndex(1, 1, 6);
		userProfile.EnterPhonenum(svalidPhone);
		userProfile.clickSaveProfile();
		BrowserFactory.waitFor();
		//Capture the Text of Notification message
		String verifyNotifyMesg = userProfile.VerifyNotficationMesg();
		//Verify Notification text
		BrowserFactory.waitFor();
		Assert.assertEquals(verifyNotifyMesg, "Event Notifications");
		logger.log(LogStatus.PASS, "Sucessfully Landing page is displayed");
		//Capture the Screenshot
		logger.log(LogStatus.INFO, logger.addScreenCapture(GenericFunctions.CaptureScreenshot(driver, "TC01_AllowRegistration_3")));
	
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
		Assert.assertTrue(verifyMesg.contains("Your Information"));
		logger.log(LogStatus.INFO, "User Profile Page is Sucessfully displayed");
		userProfile.EnterFistName("");
		userProfile.EnterLastName("");
		String semail=(DataProviderFactory.getExcel().getDatawithSheetIndex(1, 1, 0))+UUID.randomUUID().toString() + "@mailinator.com";
		String sPassword=DataProviderFactory.getExcel().getDatawithSheetIndex(1, 1,3);
		String sConfirmPassword=DataProviderFactory.getExcel().getDatawithSheetIndex(1, 1, 4);
		String sZipcode =DataProviderFactory.getExcel().getNumericDatawithSheetIndex(1, 1, 5);		
		
		userProfile.enterMandatoryFieldsinUserProfile(semail,sPassword,sConfirmPassword,sZipcode,"email");
		//userProfile.checkNotification("sms");
		//Wait
		BrowserFactory.waitFor();
		logger.log(LogStatus.INFO, "Required Fields are Entered in Registration Page with Last and First Name Blank");
		userProfile.clickSaveProfile();
		
	/*	userProfile.onMousePhone();
	
		String verInvPhNum = userProfile.VerifyinvalidPhNumErrorMesgProfilePage();
		//Verify Invalid Phone Error Mesg in Profile Page
		Assert.assertTrue(verInvPhNum.contains("You must enter a valid phone number"));
		//Wait
		logger.log(LogStatus.PASS, "Error Message is display "+verInvPhNum);
		//ENter the Phone Number
		String svalidPhone=DataProviderFactory.getExcel().getNumericDatawithSheetIndex(1, 1, 6);
		userProfile.EnterPhonenum(svalidPhone);
		userProfile.clickSaveProfile();
		BrowserFactory.waitFor();
		//Capture the Text of Notification message
		
		//Verify Notification text
*/		BrowserFactory.waitFor();
		String verifyNotifyMesg = userProfile.VerifyNotficationMesg();
		Assert.assertEquals(verifyNotifyMesg, "Event Notifications");
		logger.log(LogStatus.PASS, "Sucessfully Landing page is displayed");
		//Capture the Screenshot
		logger.log(LogStatus.INFO, logger.addScreenCapture(GenericFunctions.CaptureScreenshot(driver, "TC01_AllowRegistration_4")));
	
}

	
}