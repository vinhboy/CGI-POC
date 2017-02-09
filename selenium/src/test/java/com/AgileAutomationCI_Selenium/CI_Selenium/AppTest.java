package com.AgileAutomationCI_Selenium.CI_Selenium;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.testng.annotations.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;

public class AppTest {
	@Test
	public void TC01_LoginValidation_HtmlUnit() {
		// Creating a new instance of the HTML unit driver
		WebDriver driver = new HtmlUnitDriver();
		// Navigate to Google
		ValidateGoogleSite(driver);
	}

	@Test
	public void TC01_LoginValidation_Chrome() {
		WebDriver driver = new ChromeDriver();
		ValidateGoogleSite(driver, true);
	}

	private void ValidateGoogleSite(WebDriver driver) {
		ValidateGoogleSite(driver, false);
	}

	private void ValidateGoogleSite(WebDriver driver, boolean takeScreenshot) {
		driver.get("http://www.google.com");

		// Locate the searchbox using its name
		WebElement element = driver.findElement(By.name("q"));

		// Enter a search query
		element.sendKeys("CGI");

		// Submit the query. Webdriver searches for the form using the text input element automatically
		// No need to locate/find the submit button
		element.submit();

		// This code will print the page title
		System.out.println("Page title is: " + driver.getTitle());

		if (takeScreenshot) {
			try {
				System.out.println("Taking snapshot...");
				WebDriver augmentedDriver = new Augmenter().augment(driver);
				File screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(screenshot, new File("screenshot.png"));
			}
			catch (IOException e) {
				System.out.println("Failed taking snapshot...");
			}
		}

		driver.quit();
	}
}
