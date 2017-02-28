package com.MyCAlerts.Testcases;

import java.util.UUID;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.MyCAlerts.BaseClass.BaseClass;
import com.MyCAlerts.PageObjects.LoginPage;
import com.MyCAlerts.PageObjects.UserProfile;
import com.MyCAlerts.Utilities.ApplicationSpecific;
import com.MyCAlerts.Utilities.GenericFunctions;
import com.MyCAlerts.factory.BrowserFactory;
import com.MyCAlerts.factory.DataProviderFactory;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TC02_UserProfile_DisAllowRegistration extends BaseClass {

		@Test(description="Verify Registration not allowed with Invalid Phone Number")

		public void TC02_DisAllowRegistration_2() throws InterruptedException {
			// Start Create New HTML report for the Testcase
			logger = report.startTest("TC02_VerifyRegistrationDisallopwithInvalidPhone");
			logger.log(LogStatus.INFO, "Application is up and running on Chrome Browser");
			//Inherits the Login Page to Main Testcase
			UserProfile userProfile = PageFactory.initElements(driver, UserProfile.class);
			//Click on the Create Profile
			userProfile.clickCreateNow();
			BrowserFactory.waitFor();
			//Get the Text of User Profile in User Profile Page
			String verifyMesg = userProfile.VerifyUserProfilePage();
			//Verify User Profile Header
			Assert.assertTrue(verifyMesg.contains("Your Information"));
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
	
		@Test(description="Verify Registration not allowed with Invalid Email")

		public void TC02_DisAllowRegistration_3() throws InterruptedException {
			// Start Create New HTML report for the Testcase
			logger = report.startTest("TC03_VerifyRegistrationDisallopwithInvalidEmail");
			logger.log(LogStatus.INFO, "Application is up and running on Chrome Browser");
			//Inherits the Login Page to Main Testcase
			UserProfile userProfile = PageFactory.initElements(driver, UserProfile.class);
			//Click on the Create Profile
			userProfile.clickCreateNow();
			BrowserFactory.waitFor();
			//Get the Text of User Profile in User Profile Page
			String verifyMesg = userProfile.VerifyUserProfilePage();
			//Verify User Profile Header
			Assert.assertTrue(verifyMesg.contains("Your Information"));
			logger.log(LogStatus.INFO, "User Profile Page is Sucessfully displayed");
			// Fetch the data from Excel Sheet
			
			String semail=DataProviderFactory.getExcel().getDatawithSheetIndex(1, 1, 8);
			userProfile.EnterEmail(semail);
			logger.log(LogStatus.INFO, "InValid Email is enetered");
			String verInvemail = userProfile.VerifyinvalidEmailErrorMesgProfilePage();
			//Verify Invalid Phone Error Mesg in Profile Page
			Assert.assertTrue(verInvemail.contains("A valid email address is required"));
			//Wait
			logger.log(LogStatus.PASS, "Error Message is display for Invalid email"+verInvemail);
			//Capture the Screenshot
			logger.log(LogStatus.INFO, logger.addScreenCapture(GenericFunctions.CaptureScreenshot(driver, "TC02_DisAllowRegistration_3")));
		}
		
		
		
		@Test(description="Verify Registration not allowed with Invalid ZipCode")

		public void TC02_DisAllowRegistration_4() throws InterruptedException {
			// Start Create New HTML report for the Testcase
			logger = report.startTest("TC04_VerifyRegistrationDisallopwithInvalidZip");
			logger.log(LogStatus.INFO, "Application is up and running on Chrome Browser");
			//Inherits the Login Page to Main Testcase
			UserProfile userProfile = PageFactory.initElements(driver, UserProfile.class);
			//Click on the Create Profile
			userProfile.clickCreateNow();
			BrowserFactory.waitFor();
			//Get the Text of User Profile in User Profile Page
			String verifyMesg = userProfile.VerifyUserProfilePage();
			//Verify User Profile Header
			Assert.assertTrue(verifyMesg.contains("Your Information"));
			logger.log(LogStatus.INFO, "User Profile Page is Sucessfully displayed");
			// Fetch the data from Excel Sheet
			
			String sZipcode =DataProviderFactory.getExcel().getNumericDatawithSheetIndex(1, 1, 9);
			userProfile.EnterZip("7645");
			BrowserFactory.waitFor();
			logger.log(LogStatus.INFO, "InValid Zip is enetered");
			String verInvezip = userProfile.VerifyinvalidZipCodeErrorMesgProfilePage();
			//Verify Invalid Phone Error Mesg in Profile Page
			Assert.assertTrue(verInvezip.contains("You must enter a valid Zip code"));
			//Wait
			logger.log(LogStatus.PASS, "Error Message is display for Invalid Zip"+verInvezip);
			//Capture the Screenshot
			logger.log(LogStatus.INFO, logger.addScreenCapture(GenericFunctions.CaptureScreenshot(driver, "TC02_DisAllowRegistration_4")));
		
	}
		
		@Test(description="Verify Registration not allowed with Invalid Confirm Password")

		public void TC02_DisAllowRegistration_5() throws InterruptedException {
			// Start Create New HTML report for the Testcase
			logger = report.startTest("TC05_VerifyRegistrationDisallopwithInvalidConfirmPassword");
			logger.log(LogStatus.INFO, "Application is up and running on Chrome Browser");
			//Inherits the Login Page to Main Testcase
			UserProfile userProfile = PageFactory.initElements(driver, UserProfile.class);
			//Click on the Create Profile
			userProfile.clickCreateNow();
			BrowserFactory.waitFor();
			//Get the Text of User Profile in User Profile Page
			String verifyMesg = userProfile.VerifyUserProfilePage();
			//Verify User Profile Header
			Assert.assertTrue(verifyMesg.contains("Your Information"));
			logger.log(LogStatus.INFO, "User Profile Page is Sucessfully displayed");
			// Fetch the data from Excel Sheet
			String sPassword=DataProviderFactory.getExcel().getDatawithSheetIndex(1, 1, 3);
			String sConfirmPassword=DataProviderFactory.getExcel().getDatawithSheetIndex(1, 1, 10);
			userProfile.EnterPassword(sPassword);
			userProfile.EnterConfirmPassword(sConfirmPassword);
			logger.log(LogStatus.INFO, "InValid Confirm Password is entered");
			String verInvConfrmPas = userProfile.VerifyinvalidMesgConfirmPasswordProfilePage();
			//Verify Invalid Phone Error Mesg in Profile Page
			Assert.assertTrue(verInvConfrmPas.contains("Your passwords must match"));
			//Wait
			logger.log(LogStatus.PASS, "Error Message is display for Invalid Confirm Password"+verInvConfrmPas);
			//Capture the Screenshot
			logger.log(LogStatus.INFO, logger.addScreenCapture(GenericFunctions.CaptureScreenshot(driver, "TC02_DisAllowRegistration_5")));
		
	}
		
		@Test(description="Verify Registration not allowed with Invalid Password Characters")

		public void TC02_DisAllowRegistration_6() throws InterruptedException {
			// Start Create New HTML report for the Testcase
			logger = report.startTest("TC06_VerifyRegistrationDisallopwithInvalidPassword");
			logger.log(LogStatus.INFO, "Application is up and running on Chrome Browser");
			//Inherits the Login Page to Main Testcase
			UserProfile userProfile = PageFactory.initElements(driver, UserProfile.class);
			//Click on the Create Profile
			userProfile.clickCreateNow();
			BrowserFactory.waitFor();
			//Get the Text of User Profile in User Profile Page
			String verifyMesg = userProfile.VerifyUserProfilePage();
			//Verify User Profile Header
			Assert.assertTrue(verifyMesg.contains("Your Information"));
			logger.log(LogStatus.INFO, "User Profile Page is Sucessfully displayed");
			// Fetch the data from Excel Sheet
			String sinvaPass="dd";
			userProfile.EnterPassword(sinvaPass);
			logger.log(LogStatus.INFO, "InValid Zip is enetered");
			String verInvPassword = userProfile.VerifyinvalidPasswordlesscharacterProfilePage();
			//Verify Invalid Phone Error Mesg in Profile Page
			Assert.assertTrue(verInvPassword.contains("Password must be at least 8 characters and contain 3 of the 4 following requirements: one upper case letter, one lower case letter, one number, and one special character"));
			//Wait
			logger.log(LogStatus.PASS, "Error Message is display for Invalid Password"+verInvPassword);
			//Capture the Screenshot
			logger.log(LogStatus.INFO, logger.addScreenCapture(GenericFunctions.CaptureScreenshot(driver, "TC02_DisAllowRegistration_4")));
		
	}
		
		@Test(description="Verify Registration not allowed with already registered user")

		public void TC02_DisAllowRegistration_8() throws InterruptedException {
			// Start Create New HTML report for the Testcase
			logger = report.startTest("TC08_VerifyRegistrationDisallopwithalreadyRegisteredUser");
			logger.log(LogStatus.INFO, "Application is up and running on Chrome Browser");
			//Inherits the Login Page to Main Testcase
			UserProfile userProfile = PageFactory.initElements(driver, UserProfile.class);
			//Click on the Create Profile
			userProfile.clickCreateNow();
			BrowserFactory.waitFor();
			//Get the Text of User Profile in User Profile Page
			String verifyMesg = userProfile.VerifyUserProfilePage();
			//Verify User Profile Header
			Assert.assertTrue(verifyMesg.contains("Your Information"));
			logger.log(LogStatus.INFO, "User Profile Page is Sucessfully displayed");
			// Fetch the data from Excel Sheet
			userProfile.EnterFistName(DataProviderFactory.getExcel().getDatawithSheetIndex(1, 1, 0));
			userProfile.EnterLastName(DataProviderFactory.getExcel().getDatawithSheetIndex(1, 1, 1));
			String semail=DataProviderFactory.getExcel().getDatawithSheetIndex(0, 1, 0);
			String sPassword=DataProviderFactory.getExcel().getDatawithSheetIndex(1, 1,3);
			String sConfirmPassword=DataProviderFactory.getExcel().getDatawithSheetIndex(1, 1, 4);
			String sZipcode =DataProviderFactory.getExcel().getNumericDatawithSheetIndex(1, 1, 5);	
			userProfile.enterMandatoryFieldsinUserProfile(semail,sPassword,sConfirmPassword,sZipcode,"email");
			userProfile.clickSaveProfile();
			BrowserFactory.waitFor();
			logger.log(LogStatus.INFO, "Already regsitered email id is enetered-"+semail);
			String verInvPassword = userProfile.VerifyUserProfilealreadyExistMesg();
			//Verify Invalid Phone Error Mesg in Profile Page
			Assert.assertTrue(verInvPassword.contains("A profile already exists for that email address"));
			//Wait
			logger.log(LogStatus.PASS, "Error Message is display for already registered user --"+semail);
			//Capture the Screenshot
			logger.log(LogStatus.INFO, logger.addScreenCapture(GenericFunctions.CaptureScreenshot(driver, "TC02_DisAllowRegistration_8")));
		
	}
		
		
		@Test(description="Verify Registration not allowed with already registered user")

		public void TC03_CancelRegistration_1() throws InterruptedException {
			// Start Create New HTML report for the Testcase
			logger = report.startTest("TC03_CancelRegistration");
			logger.log(LogStatus.INFO, "Application is up and running on Chrome Browser");
			//Inherits the Login Page to Main Testcase
			UserProfile userProfile = PageFactory.initElements(driver, UserProfile.class);
			//Click on the Create Profile
			userProfile.clickCreateNow();
			BrowserFactory.waitFor();
			//Get the Text of User Profile in User Profile Page
			String verifyMesg = userProfile.VerifyUserProfilePage();
			//Verify User Profile Header
			Assert.assertTrue(verifyMesg.contains("Your Information"));
			logger.log(LogStatus.INFO, "User Profile Page is Sucessfully displayed");
			// Fetch the data from Excel Sheet
			userProfile.clickCancelProfile();
			BrowserFactory.isTextPresent("Please Login or Register", "Verify Login Page");
			BrowserFactory.waitFor();
			
			//Wait
			logger.log(LogStatus.PASS, "Login Page is displayed after clicking Cancel button on User Profile Page");
			//Capture the Screenshot
			logger.log(LogStatus.INFO, logger.addScreenCapture(GenericFunctions.CaptureScreenshot(driver, "TC03_CancelRegistration_1")));
		
	}
}