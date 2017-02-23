package com.MyCAlerts.Testcases;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.MyCAlerts.BaseClass.BaseClass;
import com.MyCAlerts.PageObjects.LoginPage;
import com.MyCAlerts.Utilities.GenericFunctions;
import com.MyCAlerts.factory.BrowserFactory;
import com.MyCAlerts.factory.DataProviderFactory;
import com.relevantcodes.extentreports.LogStatus;

public class TC03_SessionManagement extends BaseClass {
	@Test(description="Verify login success message with valid user")

	public void TC03_SessionManagement_1() {

		logger = report.startTest("TC03_SessionManagement_1");
		logger.log(LogStatus.INFO, "Application is up and running");
		LoginPage login = PageFactory.initElements(driver, LoginPage.class);
		//login.myAdtLoginEnterDetails(DataProviderFactory.getExcel().getDatawithSheetIndex(0, 1, 0),DataProviderFactory.getExcel().getDatawithSheetIndex(0, 1, 1));
		login.myAdtLoginEnterDetails("sravan.neppalli@cgi.com","ddff444");
		BrowserFactory.impliciteWait(25);
		logger.log(LogStatus.INFO, "Valid Login Details Entered");
		BrowserFactory.isTextPresent("Success Status","Sucess Message is displayed");
		logger.log(LogStatus.PASS, "Sucess Message is displayed");
		//logger.log(LogStatus.INFO, logger.addScreenCapture(GenericFunctions.CaptureScreenshot(driver, "LoginSuccessMessagewithValidCredentials")));
		BrowserFactory.openNewTab();
		
	}
}