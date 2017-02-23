package com.MyCAAlerts.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.MyCAAlerts.BaseClass.BaseClass;
import com.MyCAAlerts.factory.BrowserFactory;
import com.MyCAAlerts.factory.DataProviderFactory;


public class GenericFunctions {
			public static String CaptureScreenshot(WebDriver driver, String Screenshotname) {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File src = ts.getScreenshotAs(OutputType.FILE);
			System.out.println("Source Path" + src);
			String destination = "../Screenshots/" + Screenshotname + System.currentTimeMillis()+".PNG";

			try {
				FileUtils.copyFile(src, new File("./Screenshots/" + Screenshotname + System.currentTimeMillis()+".PNG"));
				System.out.println("Screenshot copied to destination Screenshots folder Sucessfully"+destination);
			} catch (IOException e) {
				System.out.println("Failed to take Screenshot" + e.getMessage());

			}
			return destination;
		}
	
	
	@SuppressWarnings("resource")
	public static String CaptureScreenshot12(WebDriver driver, String Screenshotname) throws IOException {
	    File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	    //FileUtils.copyFile(scrFile, new File("./Screenshots/" + Screenshotname + ".PNG"));
	    
	    String encodedBase64 = null;
	    FileInputStream fileInputStreamReader = null;
	    try {
	        fileInputStreamReader = new FileInputStream(scrFile);
	        byte[] bytes = new byte[(int)scrFile.length()];
	        fileInputStreamReader.read(bytes);
	        encodedBase64 = new String(Base64.encodeBase64(bytes));
	        //encodedBase64 = new String(Base64.getEncoder().encode(bytes));
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    //String destination = "../Screenshots/" + Screenshotname + "data:image/png;base64,"+encodedBase64;;
	    return "data:image/png;base64,"+encodedBase64;
	   
	}
	
	
	
	
		
	}

