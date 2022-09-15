package com.actitime.utils;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.actitime.base.BaseClass;

public class CommonUtils extends BaseClass {	
	
	
	public static boolean loginToActiTimeApplication() throws IOException {
		writeLog("loginToActiTimeApplication called");
		boolean isDisplayed=false;
//		String[] username = getTestDataFromExcel("LoginPage", "UserNameEditBox").split("#");
//		String[] password = getTestDataFromExcel("LoginPage", "PasswordEditBox").split("#");
//		driver.findElement(By.xpath(getLocatorDataFromExcelFile("LoginPage", "UserNameEditBox"))).sendKeys(username[0]);
//		driver.findElement(By.xpath(getLocatorDataFromExcelFile("LoginPage", "PasswordEditBox"))).sendKeys(password[0]);
//		driver.findElement(By.xpath(getLocatorDataFromExcelFile("LoginPage", "LoginButton"))).click();
		
//		String username = getTestDataFromExcel("LoginPage", "UserNameEditBox");
//		String password = getTestDataFromExcel("LoginPage", "PasswordEditBox");
		String username = testDataMap.get("userName");
		String password = testDataMap.get("password");
//		driver.findElement(By.xpath(getLocatorDataFromExcelFile("LoginPage", "UserNameEditBox"))).sendKeys(username[0]);
//		driver.findElement(By.xpath(getLocatorDataFromExcelFile("LoginPage", "PasswordEditBox"))).sendKeys(password[0]);
//		driver.findElement(By.xpath(getLocatorDataFromExcelFile("LoginPage", "LoginButton"))).click();
		driver.findElement(By.xpath(getLocatorDataFromMap("LoginPage", "UserNameEditBox"))).sendKeys(username);
		driver.findElement(By.xpath(getLocatorDataFromMap("LoginPage", "PasswordEditBox"))).sendKeys(password);
		driver.findElement(By.xpath(getLocatorDataFromMap("LoginPage", "LoginButton"))).click();
//		driver.findElement(By.xpath(locatorMap.get("LoginPage#UserNameEditBox"))).sendKeys(userNameTD);
//		driver.findElement(By.xpath(locatorMap.get("LoginPage#PasswordEditBox"))).sendKeys(passwordTD);
//		driver.findElement(By.xpath(locatorMap.get("LoginPage#LoginButton"))).click();
		
		try {
			isDisplayed = driver.findElement(By.xpath(getLocatorDataFromMap("HomePage","LogoutLink"))).isDisplayed();
		} catch(Exception e) {
			e.printStackTrace();
			captureScreenshotOnFailure("Login");
		}
		
		return isDisplayed;
	}

	public static boolean loginToActiTimeApplication(String username, String password) throws IOException {
		driver.findElement(By.xpath(getLocatorDataFromExcelFile("LoginPage", "UserNameEditBox"))).sendKeys(username);
		driver.findElement(By.xpath(getLocatorDataFromExcelFile("LoginPage", "PasswordEditBox"))).sendKeys(password);
		driver.findElement(By.xpath(getLocatorDataFromExcelFile("LoginPage", "LoginButton"))).click();
		boolean isDisplayed=false;
		try {
			isDisplayed = driver.findElement(By.xpath(getLocatorDataFromExcelFile("HomePage","LogoutLink"))).isDisplayed();
		} catch(Exception e) {
			e.printStackTrace();
			captureScreenshotOnFailure("Login");
		}
		
		return isDisplayed;
	}
	
	
}
