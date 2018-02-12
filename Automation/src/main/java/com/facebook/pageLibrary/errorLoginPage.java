package com.facebook.pageLibrary;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;


public class errorLoginPage {
	
	WebDriver driver;
	
	
	@FindBy(how=How.XPATH, using="//a[text()='Sign Up' and @role='button']")
	public WebElement signUpBtn; 

	
	public errorLoginPage(WebDriver driver) {
		
		this.driver=driver;
	}
	

	public boolean verifySignupBtn() {
		
		if (signUpBtn.isDisplayed()) {
		return true;
		}
		
		else {
			return false;
		}
	}

}

