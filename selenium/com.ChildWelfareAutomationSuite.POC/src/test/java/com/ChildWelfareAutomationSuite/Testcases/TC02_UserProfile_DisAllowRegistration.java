package com.ChildWelfareAutomationSuite.Testcases;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.ChildWelfareAutomationSuite.BaseClass.BaseClass;
import com.ChildWelfareAutomationSuite.PageObjects.UserProfile;
import com.ChildWelfareAutomationSuite.Utilities.ApplicationSpecific;
import com.ChildWelfareAutomationSuite.Utilities.GenericFunctions;
import com.ChildWelfareAutomationSuite.factory.BrowserFactory;
import com.ChildWelfareAutomationSuite.factory.DataProviderFactory;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TC02_UserProfile_DisAllowRegistration extends BaseClass {

		@Test(description="Verify Registration not allowed with Invalid Phone Number")

		public void TC02_DisAllowRegistration_2() throws InterruptedException {
			// Start Create New HTML report for the Testcase
			logger = report.startTest("TC01_VerifyRegistrationDisallopwithInvalidPhone");
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
			String iinValidPhoneNum =DataProviderFactory.getExcel().getNumericDatawithSheetIndex(1, 1, 7);
			userProfile.EnterPhonenum(iinValidPhoneNum);
			logger.log(LogStatus.INFO, "InValid Phone Number is Entered having less than 10 digits");
			String verInvPhNum = userProfile.VerifyinvalidPhNumErrorMesgProfilePage();
			//Verify Invalid Phone Error Mesg in Profile Page
			Assert.assertTrue(verInvPhNum.contains("You must enter a valid phone number"));
			//Wait
			logger.log(LogStatus.PASS, "Error Message is display for Invalid Phone Number"+verInvPhNum);
			//Capture the Screenshot
			logger.log(LogStatus.INFO, logger.addScreenCapture(GenericFunctions.CaptureScreenshot(driver, "TC02_DisAllowRegistration_2")));
		
	}
	
	
		
		
}