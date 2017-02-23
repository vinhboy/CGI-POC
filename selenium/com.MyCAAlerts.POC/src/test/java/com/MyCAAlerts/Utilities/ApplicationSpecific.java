package com.MyCAAlerts.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.MyCAAlerts.Utilities.GenericFunctions;
public class ApplicationSpecific {
	static WebDriver driver;
	 //Wait Method used to wait for the page to load until the
		// processing image appears

		public static void Processing() {

			WebDriverWait wait = new WebDriverWait(driver, 300);

			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("processing")));
		}

		// Method to get the Text from the Object
		public String getText(String path) {

			String value = driver.findElement(By.xpath(path)).getText();

			return value;

		}

		// CLick on the Link
		public void clickElement(ExtentTest test, String clkObject, String Elementname) {

			try {

				driver.findElement(By.xpath(clkObject)).click();

				test.log(LogStatus.PASS, "Element should be clicked	sucessfully",
						Elementname + " - having xpath '" + clkObject + "' clicked	sucessfully");

			} catch (Exception e) {

				System.out.println("Element '" + clkObject + "' click failed " + e);

				test.log(LogStatus.WARNING, "Element should be clicked sucessfully",
						Elementname + " - having xpath '" + clkObject + "' could not be clicked because -" + e);

				Reporter.log(Elementname + " - having xpath '" + clkObject + "' click failed -	" + e);

			}

		}

		// Select the Element from List Box
		public static void selectElementByNameMethod(WebElement element, String Name) {
			Select selectitem = new Select(element);
			selectitem.selectByVisibleText(Name);
		}

		// Verify the actual and Expected value
		public static void VerifyValue(ExtentTest test, String strObjName, String ExpectedVal, String ActualVal) {
			try {
				Assert.assertEquals(ExpectedVal, ActualVal);
				test.log(LogStatus.PASS, "Value is Verified in the field " + strObjName + " is " + ActualVal);
			} catch (Exception e) {
				test.log(LogStatus.PASS, "Value is not Verified in the field " + strObjName + " is " + ActualVal);

			}
		}

		// Check the Title of the page
		public static void checkTitle(ExtentTest test, WebDriver driver, String expectedTitle) {

			if (driver.getTitle().equals(expectedTitle)) {
				test.log(LogStatus.PASS, "Check page title", "Page title is " + expectedTitle);
			} else {
				test.log(LogStatus.FAIL, "Check page title", "Incorrect login page title " + driver.getTitle());
			}
		}

		public static void checkErrorMessage(ExtentTest test, WebDriver driver, String expectedMessage,
				String Screenshotname) {

			String errorMessage = driver.findElement(By.className("error")).getText();

			if (errorMessage.equals(expectedMessage)) {

				test.log(LogStatus.PASS, "Check error message", "Error message is " + expectedMessage);
			} else {
				test.log(LogStatus.FAIL, "Check error message",
						"View details below:" + GenericFunctions.CaptureScreenshot(driver, Screenshotname));
			}
		}

		public static void highLightElement(WebDriver driver, WebElement element) {
			JavascriptExecutor js = (JavascriptExecutor) driver;

			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

				System.out.println(e.getMessage());
			}

			js.executeScript("arguments[0].setAttribute('style','border: solid 2px white');", element);

		}
		
		public static void waitForPageToLoad(long timeOutInSeconds) {
			  ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			   public Boolean apply(WebDriver driver) {
			    return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			   }
			  };
			  try {
			   System.out.println("Waiting for page to load...");
			   WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
			   wait.until(expectation);
			  } catch (Throwable error) {
			   System.out.println("Timeout waiting for Page Load Request to complete after " + timeOutInSeconds + " seconds");
			   Assert.assertTrue(false, "Timeout waiting for Page Load Request to complete.");

			  }
			 }

		
}
