package com.ChildWelfareAutomationSuite.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Reporter;

import com.ChildWelfareAutomationSuite.factory.BrowserFactory;

public class UserProfile {
	WebDriver driver;

	public UserProfile(WebDriver ldriver) {
		this.driver = ldriver;
	}

	@FindBy(xpath = "//a[contains(.,'Create one now')]")
	WebElement clickCreateOneNow;
	@FindBy(xpath = "//h3[contains(.,'User Profile')]")
	WebElement UserProfileHeader;
	@FindBy(xpath = "//input[@name='email']")
	WebElement profileEmail;
	@FindBy(xpath = "//input[@id='firstName']")
	WebElement profileFirstName;
	@FindBy(xpath = "//input[@name='lastName']")
	WebElement profileLastName;
	@FindBy(xpath = "//input[@id='phone']")
	WebElement profilePhone;
	@FindBy(xpath = "//input[@id='password']")
	WebElement profilePassword;
	@FindBy(xpath = "//input[@id='passwordConfirmation']")
	WebElement profilePwdConfirm;
	@FindBy(xpath = "//input[@id='zipCode']")
	WebElement profileZipcode;
	@FindBy(id = "email")
	WebElement profileEmailNotification;
	@FindBy(xpath = "//input[@ng-model='profile.pushNotification']")
	WebElement pushNotification;
	@FindBy(xpath = "//input[@ng-model='profile.smsNotification']")
	WebElement smsNotification;
	@FindBy(xpath = "//button[contains(.,'SAVE')]")
	WebElement profileSave;
	@FindBy(xpath = "//button[contains(.,'CANCEL')]")
	WebElement profileCancel;
	@FindBy(xpath = "//span[contains(.,'You must enter a valid phone number')]")
	WebElement invalidPhoneNum;
	@FindBy(xpath = "//span[contains(.,'You must enter a valid Zip code')]")
	WebElement invalidzipCode;
	@FindBy(xpath = "//span[contains(.,'A valid email address is required')]")
	WebElement invalidEmail;
	@FindBy(xpath = "//span[contains(.,'Your passwords must match')]")
	WebElement passwordDonotMactch;
	@FindBy(xpath = "//span[contains(.,'Password must be at least 8 characters and contain 3 of the 4 following requirements: one upper case letter, one lower case letter, one number, and one special character')]")
	WebElement invalidPassword;

	// Click on the CreateNowLink
	public void clickCreateNow() {
		clickCreateOneNow.click();
	}

	// Enter FirstName
	public void EnterFistName(String sfirstName) {
		profileFirstName.sendKeys(sfirstName);
	}

	// Enter LastName
	public void EnterLastName(String slastName) {
		profileLastName.sendKeys(slastName);
	}

	// Enter PhoneNumber
	public void EnterPhonenum(String iPhoneNum) {
		profilePhone.sendKeys(iPhoneNum);
	}

	// Enter PhoneNumber
	public void EnterEmail(String email) {
		profileEmail.sendKeys(email);
	}

	// Enter PhoneNumber
	public void EnterPassword(String password) {
		profilePassword.sendKeys(password);
	}

	// Enter PhoneNumber
	public void EnterConfirmPassword(String password) {
		profilePwdConfirm.sendKeys(password);
	}

	
	// Enter PhoneNumber
	public void EnterZip(String zip) {
		invalidzipCode.sendKeys(zip);
	}
	
	// Verify UserProfile Page
	public String VerifyUserProfilePage()

	{
		String sprofileHeader = UserProfileHeader.getText();
		return sprofileHeader;

	}

	// Verify Error Message Invalid Phone in UserProfile Page
	public String VerifyinvalidPhNumErrorMesgProfilePage()

	{
		String sinvalidPhNum = invalidPhoneNum.getText();
		return sinvalidPhNum;

	}

	// Verify UserProfile Page
	public String VerifyinvalidEmailErrorMesgProfilePage()

	{
		String sinvalidEmail = invalidEmail.getText();
		return sinvalidEmail;

	}

	// Verify UserProfile Page
	public String VerifyinvalidZipCodeErrorMesgProfilePage()

	{
		String iinvalidZip = invalidzipCode.getText();
		return iinvalidZip;

	}

	// Verify UserProfile Page
	public String VerifyinvalidMesgConfirmPasswordProfilePage()

	{
		String spassDontMatch = passwordDonotMactch.getText();
		return spassDontMatch;

	}

	// Verify UserProfile Page
	public String VerifyinvalidPasswordlesscharacterProfilePage()

	{
		String sinvalidPas = invalidPassword.getText();
		return sinvalidPas;

	}

	// Verify UserProfile Page
	public String VerifyinvalidPasswordMorecharacterProfilePage()

	{
		String sprofileHeader = UserProfileHeader.getText();
		return sprofileHeader;

	}
	// Enter the Mandatory Fields in Registration with One Notification

	public void enterMandatoryFieldsinUserProfile(String email, String Profilepassword, String ConfirmPassword,
			String zipCode, String alertType) {
		profileEmail.sendKeys(email);
		profilePassword.sendKeys(Profilepassword);
		profilePwdConfirm.sendKeys(ConfirmPassword);
		profileZipcode.sendKeys(zipCode);
		BrowserFactory.waitFor();
		WebElement elementToClick = driver
				.findElement(By.xpath("//input[contains(@id, 'email')][contains(@value, '1')]"));
		elementToClick.click();
		if (alertType == "email") {

			// driver.findElement(By.xpath("//input[contains(@ng-model,'profile.emailNotification')]")).click();

			if (elementToClick.isSelected()) {
				System.out.println("Email Checbox is clicked");
				Reporter.log("Email is Checked");
			}

			else {
				Reporter.log("Email is not  Checked");
			}
		}

	}

	public void checkNotification(String alertType) {
		WebElement elementToClick = driver
				.findElement(By.xpath("//input[contains(@id, 'sms')][contains(@value, '2')]"));
		WebElement elementToClick1 = driver
				.findElement(By.xpath("//input[contains(@id, 'push')][contains(@value, '3')]"));
		if (alertType == "Push") {
			elementToClick1.click();
			if (elementToClick1.isSelected()) {
				Reporter.log("Push Notification is Checked");
			}

			else {
				Reporter.log("Push Notification is not  Checked");
			}
		}

		if (alertType == "sms") {
			elementToClick.click();
			if (elementToClick.isSelected()) {
				Reporter.log("smsNotification is Checked");
			}

			else {
				Reporter.log("smsNotification is not  Checked");
			}
		}
	}

	// Click on the SaveProfileRegistration
	public void clickSaveProfile() {
		profileSave.click();
	}

	// Click on the CancelProfileRegistration
	public void clickCancelProfile() {
		profileCancel.click();
	}

}
