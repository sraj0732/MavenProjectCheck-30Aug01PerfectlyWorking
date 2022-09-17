package com.actitime.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.google.common.io.Files;

public class BaseClass implements ITestListener {

	public static WebDriver driver;
	public static Map<String, String> locatorMap = new HashMap<String, String>();
	public static Map<String, String> testDataMap = new HashMap<String, String>();
	public static Logger logger = (Logger)LogManager.getLogger(BaseClass.class);
	public static ExtentReports extent = new ExtentReports();
	public static ExtentSparkReporter reporter;
	public static ExtentTest elogger;
	public ITestResult result;
	public static BaseClass BaseClassObj;// = new BaseClass();

	// iTest Listener interface default methods... these will help us capture the
	// running status of the tests
	public void onStart(ITestContext context) { // similar to before suite
		try {
			BaseClass.getLocatorDataFromExcelIntoMap();
			BaseClass.getTestDataFromExcelIntoMap();

			String time = new SimpleDateFormat("ddMMyyHHmm").format(new Date().getTime());
			File results = new File("./src/test/results/results.txt");
			FileWriter resultFile = new FileWriter(results);
			resultFile.write(time + "\n");
			resultFile.flush();
			resultFile.close();

			File logs = new File("./src/test/results/logs.txt");
			FileWriter logFile = new FileWriter(logs);
			logFile.write(time + "\n");
			logFile.flush();
			logFile.close();
			writeLog("Listener onStart called");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

//		@Parameters({"url"}) 
	@BeforeMethod(alwaysRun = true)
	public static void launchApplication(@Optional("https://demo.actitime.com") String x) throws IOException {
		writeLog("Annotation Before method called");
		String browser = getConfigData("browser");
		switch (browser) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", "./src/test/utilities/chromedriver.exe");
//				ChromeOptions options = new ChromeOptions();
//				options.addArguments("--headless");
//				driver = new ChromeDriver(options);
			driver = new ChromeDriver();
			break;
		case "firefox":
			System.setProperty("webdriver.firefox.driver", "./src/test/utilities/geckodriver.exe");
			driver = new FirefoxDriver();
			break;
		default:
			System.setProperty("webdriver.chrome.driver", "./src/test/utilities/chromedriver.exe");
			driver = new ChromeDriver();
			break;
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

		driver.manage().window().maximize();
//			int y=x.length;
//			System.out.println(y);
//			if (x.length !=0) {
//				if (x[0] != null)
//					driver.get(x[0]);
//			} else
//				driver.get(getConfigData("url"));
		driver.get(x);
	}

	@AfterMethod(alwaysRun = true)
	public static void closeBrowser() throws IOException {
		writeLog("Annotation After method called");
		driver.quit();
	}
	
	public void onFinish(ITestContext context) { // similar to after suite
		try {
			writeLog("Listener onFinish called");
			extent.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void onTestStart(ITestResult result) {
		try {
			writeLog("Listener onTestStart called for " + result.getName());
			File eloggerFile = new File("./src/test/results/eresults6.html");
			reporter = new ExtentSparkReporter(eloggerFile);
			extent.attachReporter(reporter);
			elogger = extent.createTest(result.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // gives the name of the test case.
	}

	public void onTestSuccess(ITestResult result) {
		try {
			writeResult("Listener onTestSuccess called for ", result.getName());
			elogger.log(Status.PASS, result.getName()+" is pass");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onTestFailure(ITestResult result) {
		try {
			writeResult("Listener onTestFailure called for ", result.getName());
			elogger.log(Status.FAIL, result.getName()+" is fail");
			captureScreenshotOnFailure(result.getName());
			writeErrorLogs(result.getThrowable());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getConfigData(String key) throws IOException {
		File f = new File("./src/test/data/config.properties");
		FileInputStream fis = new FileInputStream(f);
		Properties p = new Properties();

		p.load(fis);

		return p.getProperty(key);
	}

	public static void updateConfigData(String key, String value) throws IOException {
		File file = new File("./src/test/data/config.properties");
		FileInputStream in = new FileInputStream(file);
		Properties prop = new Properties();
		prop.load(in);
		in.close();

		FileOutputStream out = new FileOutputStream(file);
		prop.setProperty(key, value);
		prop.store(out, null);
		out.close();

	}

	public static void captureScreenshotOnFailure(String fileName) throws IOException {
		String time = new SimpleDateFormat("ddMMyyHHmm").format(new Date().getTime());
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File dest = new File("./src/test/results/screenshots/" + fileName + time + ".png");
		Files.copy(src, dest);
	}

	public static String getLocatorDataFromExcelFile(String pageName, String elementName) throws IOException {
		String locator = "";

		File f = new File("./src/test/data/locatordata.xlsx");

		FileInputStream fis = new FileInputStream(f);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet ws = wb.getSheet("Sheet1");
		int rows = ws.getLastRowNum();

		for (int x = 1; x <= rows; x++) {
			String page = ws.getRow(x).getCell(0).getStringCellValue();
			String element = ws.getRow(x).getCell(1).getStringCellValue();

			if ((pageName.equalsIgnoreCase(page)) && (elementName.equalsIgnoreCase(element))) {
				locator = ws.getRow(x).getCell(2).getStringCellValue();
				break;
			}
		}

		wb.close();
		return locator;
	}

	public static String getTestDataFromExcel(String pageName, String elementName) throws IOException {
		String data = "";

		File f = new File("./src/test/data/testdata.xlsx");

		FileInputStream fis = new FileInputStream(f);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet ws = wb.getSheet("Sheet1");
		int rows = ws.getLastRowNum();

		for (int x = 1; x <= rows; x++) {
			String page = ws.getRow(x).getCell(0).getStringCellValue();
			String element = ws.getRow(x).getCell(1).getStringCellValue();

			if ((pageName.equalsIgnoreCase(page)) && (elementName.equalsIgnoreCase(element))) {
				data = ws.getRow(x).getCell(2).getStringCellValue();
				break;
			}
		}

		wb.close();
		return data;
	}

	public static void writeResult(String testCaseName, String testCaseStatus) throws IOException {
		File f = new File("./src/test/results/results.txt");
		FileWriter fw = new FileWriter(f, true);
		fw.write(testCaseName + "-----" + testCaseStatus + "\n");
		fw.flush();
		fw.close();
	}

	public static void writeLog(String msg) throws IOException {
//		elogger.log(Status.INFO, msg);
		File f = new File("./src/test/results/logs.txt");
		FileWriter fw = new FileWriter(f, true);

		fw.write(msg + "\n");
//		System.out.println(msg);
		fw.flush();
		fw.close();
	}


//	@BeforeSuite(alwaysRun=true)
//	public static void clearFiles() throws IOException {
//		File results = new File("./src/test/results/results.txt");
//		FileWriter resultFile = new FileWriter(results);
//		Writer.nullWriter();
//		resultFile.flush();
//		resultFile.close();
//
//		File logs = new File("./src/test/results/logs.txt");
//		FileWriter logFile = new FileWriter(logs);
//		Writer.nullWriter();
//		logFile.flush();
//		logFile.close();
//		writeLog("Before suite called");
//	}

//	@AfterSuite(alwaysRun=true)
	public static void AfterSuite() throws IOException {
		writeLog("After suite called");
	}

//	@BeforeTest(alwaysRun=true)
	public static void beforeTest() throws IOException {
		writeLog("Before test called");
	}

//	@AfterTest(alwaysRun=true)
	public static void afterTest() throws IOException {
		writeLog("After test called");
	}

//	@BeforeMethod(alwaysRun=true)
	public static void beforeMethod() throws IOException {
		writeLog("Before method called");
	}

//	@AfterMethod(alwaysRun=true)
	public static void afterMethod() throws IOException {
		writeLog("After method called");
	}

//	@BeforeClass(alwaysRun=true)
	public static void beforeClass() throws IOException {
		writeLog("Before class method called");
	}

//	@AfterClass(alwaysRun=true)
	public static void afterClass() throws IOException {
		writeLog("After class method called");
	}

	public static void getLocatorDataFromExcelIntoMap() throws IOException {
		String page = "";
		String element = "";
		String xpath = "";

		File file = new File("./src/test/data/locatordata.xlsx");
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet ws = wb.getSheet("Sheet1");

		int rows = ws.getLastRowNum();

		for (int x = 1; x <= rows; x++) {
			page = ws.getRow(x).getCell(0).getStringCellValue();
			element = ws.getRow(x).getCell(1).getStringCellValue();
			xpath = ws.getRow(x).getCell(2).getStringCellValue();
			locatorMap.put(page + "#" + element, xpath);
		}

//		System.out.println("Retrieving data from map");
//		System.out.println(locatorMap);
		wb.close();
	}

	public static String getLocatorDataFromMap(String page, String element) {
		String locator = "";

		locator = locatorMap.get(page + "#" + element);

		return locator;
	}

	public static void getTestDataFromExcelIntoMap() throws IOException {
		String userName = "";
		String password = "";

		File file = new File("./src/test/data/testdata.xlsx");
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet ws = wb.getSheet("Sheet2");

		int rows = ws.getLastRowNum();

		for (int x = 1; x <= rows; x++) {
			userName = ws.getRow(x).getCell(0).getStringCellValue();
			password = ws.getRow(x).getCell(1).getStringCellValue();
			testDataMap.put("userName", userName);
			testDataMap.put("password", password);
		}

//		System.out.println("Retrieving data from map");
//		System.out.println(testDataMap);
		wb.close();
	}
	
	public static void writeInfoLogs(String msg) {
		logger.info(msg);
		elogger.log(Status.INFO, msg);
	}
	
	public static void writeErrorLogs(Throwable t) {
		String msg = Arrays.toString(t.getStackTrace());
		String properMsg = msg.replaceAll(",", "\n");
//		BaseClassObj = new BaseClass();
		logger.error(properMsg);
		elogger.log(Status.WARNING, "Test case failed. Exception captured is "+properMsg);
//		try {
//			
//			elogger.log(Status.FAIL, "The test case "+BaseClassObj.result.getName()+"is Fail and the exception thrown is "+properMsg);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public static void test() {
		BaseClassObj = new BaseClass();
		
	}
}
