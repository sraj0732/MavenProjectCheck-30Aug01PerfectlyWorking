package com.actitime.tests;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.actitime.base.BaseClass;
import com.actitime.utils.CommonUtils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class Login extends BaseClass {

	//	@Test(enabled = false) commented and added
	//	public static void login_003() throws IOException, InterruptedException{
	//		try {
	//			writeResult("login_003", "pass");
	//			writeLog("Login_003");
	//		} catch(ArithmeticException e) {
	//			writeResult("login_003", "fail");
	//		}
	//	}
	
//	@Parameters({ "username","password" })
//	@Test(priority=2, groups = { "smoke" }, dataProvider="dataforlogin", dataProviderClass=com.actitime.dataproviders.DataProviders.class)
//	@Test(priority=2, groups = { "smoke" }, dataProvider="excelLoginData", dataProviderClass=com.actitime.dataproviders.DataProviders.class)	
	@Test
	public static void login_001() throws IOException, InterruptedException {
			writeInfoLogs("Test login_001 called");
//			boolean results = CommonUtils.loginToActiTimeApplication(username, password);
			boolean results = CommonUtils.loginToActiTimeApplication();
//			Assert.assertTrue(results,"Could not login to actitime");
			writeInfoLogs("Logged in successfully");
//			int expected = 30;
//			int actual = 30;
//			SoftAssert softassert = new SoftAssert();
//			softassert.assertEquals(expected, actual,"The actual count is not equal");
//			Assert.assertEquals(expected, actual,"The actual count is not matching expected count");
			System.out.println("The next line is printed");
//			softassert.assertAll();
			
		}
	
//	@Test(priority=1, groups = { "smoke" })
	public static void login_002() throws IOException, InterruptedException {
			boolean results = CommonUtils.loginToActiTimeApplication();
			Assert.assertFalse(results,"The logout link is displayed");
		

	}
	
//	@Test
//	public static void main(String args[]) throws IOException{
//
//		ExtentReports extent = new ExtentReports();
//		
//		File file = new File("./src/test/results/eresults5.html");
//		
//		ExtentSparkReporter reporter = new ExtentSparkReporter(file);
//		
//		extent.attachReporter(reporter);
//		System.out.println("The run is invoked");
//		ExtentTest elogger = extent.createTest("Invoking logs");
//		
//		elogger.log(Status.INFO, "First test");
//		elogger.log(Status.PASS, "Test is pass");
//		
//		extent.flush();
//		
//		
//	}
	
//	public static void extentReport() throws IOException {
//		//Create object of extent reports
//				ExtentReports extent = new ExtentReports();
//				
//				//Creating the object of file class where we need to write the extent report
//				File file = new File("./src/test/results/eresults.html");
//				
//				//Create the object of Extent Spart Reporter to write the results to the report file
//				ExtentSparkReporter reporter = new ExtentSparkReporter(file);
//				
//				//Attaching the reporter to write the data
//				extent.attachReporter(reporter);
//				
//				ExtentTest elogger = extent.createTest("Sample Extent Teport Test");
//				
//				elogger.log(Status.INFO, "Starting the sample test case");
//				elogger.log(Status.INFO, "Trying to launch the browser");
//				elogger.log(Status.INFO, "The first test case is completed");
//				elogger.log(Status.PASS, "The first test case sample test case has passed");
//				
//				extent.flush();
//	}

	

	
	
}
