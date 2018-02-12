package com.facebook.testScripts.homePage;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import org.testng.SkipException;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.facebook.pageLibrary.errorLoginPage;
import com.facebook.pageLibrary.homePage;
import com.facebook.pageLibrary.loginPage;
import com.facebook.testBase.baseClass;
import com.relevantcodes.extentreports.LogStatus;

public class TC002_invalidloginToApplication extends baseClass {

	homePage hp;
	loginPage lp;
	errorLoginPage ep;
	
	@BeforeTest
	public void setUp() {
		
		init();
	}
	
	@DataProvider(name="invalidloginData")
	public String[][] getTestData() {
		
		String[][] testRecords = getData("invalidLoginTestData");
		return testRecords;
	}
	
	@Test(dataProvider="invalidloginData")
	public void loginInvalidCredentials(String user, String pwd, String runMode) throws Exception {
		
		if(runMode.equalsIgnoreCase("n")) {
			 throw new SkipException("User marked this a no!");
		}
		
		hp=PageFactory.initElements(driver, homePage.class);
		lp=PageFactory.initElements(driver, loginPage.class);
		ep=PageFactory.initElements(driver, errorLoginPage.class);
	
		waitFor(5);
		
		try {
			Assert.assertEquals(true, hp.verifyHomePage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		hp.loginApplication(user, pwd);
		
		//waitFor(6);
		try {
			Assert.assertTrue(lp.verifyLogin());
			getScreenShot("Login");
			test.log(LogStatus.FAIL, "Unable to Navigate to the sign up page_");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			test.log(LogStatus.INFO, "Navigated to the sign up page_");
		}
		
		try {
			Assert.assertTrue(ep.verifySignupBtn());
			test.log(LogStatus.PASS, "Navigated to the sign up page");
		} catch (Exception e) {
			e.printStackTrace();
			test.log(LogStatus.FAIL, "Unable to navigate to the sign up page");
		}
		

	}
	
	
}
