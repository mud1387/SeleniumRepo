package com.facebook.testScripts.homePage;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.facebook.pageLibrary.homePage;
import com.facebook.pageLibrary.loginPage;
import com.facebook.testBase.baseClass;
import com.relevantcodes.extentreports.LogStatus;

public class TC001_validloginToApplication extends baseClass {

	homePage hp;
	loginPage lp;
	
	
	@BeforeTest
	public void setUp() {
		
		init();
	}
	
	@DataProvider(name="loginData")
	public String[][] getTestData() {
		
		String[][] testRecords = getData("loginTestData");
		return testRecords;
	}
	
	@Test(dataProvider="loginData")
	public void loginValidCredentials(String user, String pwd, String runMode) throws Exception {
		
		if(runMode.equalsIgnoreCase("n")) {
			 throw new SkipException("User marked this as a no!");
		}
		
		hp=PageFactory.initElements(driver, homePage.class);
		lp=PageFactory.initElements(driver, loginPage.class);
		
		waitFor(5);
		
		try {
			Assert.assertEquals(true, hp.verifyHomePage());
			test.log(LogStatus.INFO, "Navigated to the home page");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			test.log(LogStatus.FAIL, "UnSuccessful in navigating to the home page.");
		}
		
			
		hp.loginApplication(user, pwd);
		getScreenShot("login"+user);
		
		//waitWebDriver(driver, 10, lp.searchBar);
		try {
			Assert.assertTrue(lp.verifyLogin());
			test.log(LogStatus.INFO, "Successfully logged in to the application");
		} catch (Exception e) {
			getScreenShot("errorLogin_"+user);
			test.log(LogStatus.FAIL, "UnSuccessful in logging out of the app");
		}
		
		//lp.notifyBtn();
		
		lp.applicationLogOut();
		try {
			Assert.assertEquals(true, hp.verifyHomePage());
			test.log(LogStatus.PASS, "Successfully logged out of the app");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			test.log(LogStatus.FAIL, "Unable to log out of the app");
		}
		

	}

	
}
