package com.facebook.testBase;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import com.facebook.config.configReader;
import com.facebook.excelReader.excelReader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class baseClass {

	public WebDriver driver;
	public static ExtentReports report;
	public static ExtentTest test;
	
	configReader con =new configReader();
	excelReader excel;
	
	public void init() {
		
		selectBrowser();
		report= new ExtentReports(System.getProperty("user.dir") +"/test-output/FinalExtentReport.html", true);
		driver.get(selectURL());
	}
	
	
	public WebDriver selectBrowser() {
		
		if(con.getBrowser().equalsIgnoreCase("chrome")) {
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("profile.default_content_setting_values.notifications", 2);
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", prefs);
			System.setProperty("webdriver.chrome.driver", con.getChrome());
			driver= new ChromeDriver(options);
		}
		else if(con.getBrowser().equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", con.getFireFox());
			driver= new FirefoxDriver();
			
		}
		else if(con.getBrowser().equalsIgnoreCase("ie")) {
			System.setProperty("webdriver.ie.driver", con.getIE());
			driver= new InternetExplorerDriver();
			
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		return driver;
	}
	
	public String selectURL() {
		
		return con.getURL();
		
	}
	
	public void waitWebDriver(WebDriver driver, int sec, WebElement ele) {
		
		WebDriverWait wait=new WebDriverWait(driver,sec);
		wait.until(ExpectedConditions.visibilityOf(ele));
		
	}
	
	public void waitFor(int sec) throws Exception {
		
		Thread.sleep(sec*1000);
	}
	
	public String[][] getData(String sheetName) {
		
		excel=new excelReader("./src/main/resources/"+con.getExcel());
		String [][] data= excel.getDataFromSheet(sheetName,con.getExcel());
		return data;
	}
	
	public void getScreenShot(String name) {

		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try {
			String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath() + "/src/main/java/com/facebook/result/screenhot/";
			File destFile = new File((String) reportDirectory + name + "_" + formater.format(calendar.getTime()) + ".png");
			FileUtils.copyFile(scrFile, destFile);
			// This will help us to link the screen shot in testNG report
			Reporter.log("<a href='" + destFile.getAbsolutePath() + "'> <img src='" + destFile.getAbsolutePath() + "' height='100' width='100'/> </a>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String captureScreen(String fileName) {
		if (fileName == "") {
			fileName = "blank";
		}
		File destFile = null;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try {
			String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath() + "/src/main/java/com/facebook/result/screenshot/failed/";
			destFile = new File((String) reportDirectory + fileName + "_" + formater.format(calendar.getTime()) + ".png");
			FileUtils.copyFile(scrFile, destFile);
			// This will help us to link the screen shot in testNG report
			Reporter.log("<a href='" + destFile.getAbsolutePath() + "'> <img src='" + destFile.getAbsolutePath() + "' height='100' width='100'/> </a>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destFile.toString();
	}

	
	public void getresult(ITestResult result) {
		if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(LogStatus.PASS, result.getName() + " test is pass");
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP, result.getName() + " test is skipped and skip reason is:-" + result.getThrowable());
		} else if (result.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.ERROR, result.getName() + " test is failed" + result.getThrowable());
			String screen = captureScreen("");
			test.log(LogStatus.FAIL, test.addScreenCapture(screen));
		} else if (result.getStatus() == ITestResult.STARTED) {
			test.log(LogStatus.INFO, result.getName() + " test is started");
		}
	}
	

	@BeforeMethod()
	public void beforeMethod(Method result) {
		test = report.startTest(result.getName());
		test.log(LogStatus.INFO, result.getName() + " test Started");
	}
	
	@AfterMethod()
	public void afterMethod(ITestResult result) {
		getresult(result);
	}
	
	@AfterClass(alwaysRun = true)
	public void endTest() {
		closeBrowser();
	}
	
	public void closeBrowser() {
		driver.quit();
		report.endTest(test);
		report.flush();
	}

	
	
}
