package com.MyCAlerts.PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Reporter;

import com.MyCAlerts.factory.BrowserFactory;

public class UserProfile {
	WebDriver driver;

	public UserProfile(WebDriver ldriver) {
		this.driver = ldriver;
	}

	@FindBy(xpath = "//a[contains(.,'Create one now')]")
	WebElement clickCreateOneNow;
	@FindBy(xpath = "//h3[contains(.,'Your Information')]")
	WebElement UserProfileHeader;
	@FindBy(xpath = "//input[@name='username']")
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
	@FindBy(xpath = "//button[@translate='REGISTRATION.BUTTON.SAVE']")
	WebElement profileSave;
	@FindBy(xpath = "//button[contains(.,'CANCEL')]")
	WebElement profileCancel;
	public static @FindBy(xpath = "//span[contains(.,'You must enter a valid phone number')]")
	 WebElement invalidPhoneNum;
	@FindBy(xpath = "//span[contains(.,'You must enter a valid Zip code')]")
	WebElement invalidzipCode;
	@FindBy(xpath = "//span[contains(.,'A valid email address is required')]")
	WebElement invalidEmail;
	@FindBy(xpath = "//span[contains(.,'Your passwords must match')]")
	WebElement passwordDonotMactch;
	@FindBy(xpath = "//span[contains(.,'Password must be at least 8 characters and contain 3 of the 4 following requirements: one upper case letter, one lower case letter, one number, and one special character')]")
	WebElement invalidPassword;

	@FindBy(xpath = "//a[contains(.,'Manage Profile')]")
	WebElement manageProfile;
	
	@FindBy(xpath = "//h3[contains(.,'Event Notifications')]")
	WebElement notificationMesg;
	
	public static @FindBy(xpath="//li[contains(.,' A profile already exists for that email address. Please register using a different email. ')]")
	WebElement alreadyMesg;
	
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

	// Enter PhoneNuonMousePhonember
	public void onMousePhone()
	{
		JavascriptExecutor je = (JavascriptExecutor) driver;
		WebElement elementiPhone = driver.findElement(By.xpath("//input[@id='phone']"));
		je.executeScript("arguments[0].scrollIntoView(true);",elementiPhone);
		Actions action = new Actions(driver);
		action.moveToElement(elementiPhone).perform();
		BrowserFactory.waitFor();
		elementiPhone.sendKeys("");
		profileEmail.sendKeys(Keys.TAB);
	}
	
	public void EnterPhonenum(String iPhoneNum)
	{
		
		profilePhone.sendKeys(iPhoneNum);
		profileEmail.sendKeys(Keys.TAB);
		
	}

	// Enter PhoneNumber
	public void EnterEmail(String email) {
		profileEmail.sendKeys(email);
		profileEmail.sendKeys(Keys.TAB);
	}

	// Enter PhoneNumber
	public void EnterPassword(String password) {
		profilePassword.sendKeys(password);
		profilePassword.sendKeys(Keys.TAB);
	}

	// Enter PhoneNumber
	public void EnterConfirmPassword(String password) {
		profilePwdConfirm.sendKeys(password);
		profilePwdConfirm.sendKeys(Keys.TAB);
	}

	
	// Enter PhoneNumber
	public void EnterZip(String zip) {
		
		profileZipcode.sendKeys(zip);
		profileZipcode.sendKeys(Keys.TAB);
	}
	
	// Verify UserProfile Page
	public String VerifyUserProfilePage()

	{
		String sprofileHeader = UserProfileHeader.getText();
		return sprofileHeader;

	}
	
	public String VerifyUserProfilealreadyExistMesg()

	{
		String sprofileExist = alreadyMesg.getText();
		return sprofileExist;

	}
	
	
	// Verify UserProfile Landing Page
	public String VerifyUserLandingPage()

	{
		String slandingPage = manageProfile.getText();
		return slandingPage;

	}
	
	
	// Verify Notifications Message
		public String VerifyNotficationMesg()

		{
			String snotMesg = notificationMesg.getText();
			return snotMesg;

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
		
		JavascriptExecutor je = (JavascriptExecutor) driver;
		//WebElement elementToClick=driver.findElement(By.xpath("//input[@ng-model='profile.emailNotification']"));
		je.executeScript("arguments[0].scrollIntoView(true);",invalidzipCode);
		BrowserFactory.waitFor();
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

	public void enterMandatoryFieldsinUserProfile(String email, String Profilepassword, String ConfirmPassword,String zipCode, String alertType) {
		profileEmail.sendKeys(email);
		profilePassword.sendKeys(Profilepassword);
		profilePwdConfirm.sendKeys(ConfirmPassword);
		profileZipcode.sendKeys(zipCode);
		BrowserFactory.waitFor();
		
		if (alertType == "email") {
			JavascriptExecutor je = (JavascriptExecutor) driver;
			WebElement elementToClick=driver.findElement(By.id("email"));
			//je.executeScript("arguments[0].scrollIntoView(true);",elementToClick);

			//Actions action = new Actions(driver);
			//action.moveToElement(elementToClick).perform();
			//WebElement elementToClick = driver.findElement(By.xpath("//input[contains(@name, 'notification')][contains(@value, '1')]"));
			//WebElement elementToClick=driver.findElement(By.cssSelector("input[id='email'][value='1']"));
			BrowserFactory.waitFor();
			elementToClick.click();
		
		}

	}

	public void checkNotification(String alertType) {
		JavascriptExecutor je = (JavascriptExecutor) driver;
		//WebElement elementToClick = driver.findElement(By.xpath("//input[@ng-model='profile.smsNotification']"));
		//WebElement elementToClick1 = driver.findElement(By.xpath("//input[@ng-model='profile.pushNotification']"));
		WebElement elementToClick = driver.findElement(By.id("sms"));
		WebElement elementToClick1 = driver.findElement(By.id("push"));
		if (alertType == "Push") {
			//je.executeScript("arguments[0].scrollIntoView(true);",elementToClick);
			elementToClick1.click();
			if (elementToClick1.isSelected()) {
				Reporter.log("Push Notification is Checked");
			}

			else {
				Reporter.log("Push Notification is not  Checked");
			}
		}

		if (alertType == "sms") {
			//je.executeScript("arguments[0].scrollIntoView(true);",elementToClick);
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
