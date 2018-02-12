package com.facebook.pageLibrary;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;


public class homePage {
	
	WebDriver driver;
	
	@FindBy(how=How.NAME,using="email") 
	public WebElement emailBtn;
	
	@FindBy(how=How.NAME, using="pass")
	WebElement password ;
	
	@FindBy(how=How.XPATH, using="//input[@value='Log In']")
	WebElement loginBtn; 
	
	@FindBy(how=How.XPATH, using="//a[text()='Forgot account?']")
	WebElement forgotPwdLink;
	
	@FindBy(how=How.XPATH, using="//a[text()='Create a Page']")
	WebElement createPage;
	
	public homePage(WebDriver driver) {
		
		this.driver=driver;
	}
	

	public void clickLogin() {
		
		//Assert.assertTrue(myAccountbtn.isDisplayed(),"My account button is not visible");
		loginBtn.click();
	
	}
	
	public void enterEmail(String email) {
		
		emailBtn.sendKeys(email);
	}
	
	public void enterPwd(String pwd) {
		
		password.sendKeys(pwd);
	}
	
	public void clickFgtPwd() {
		
		forgotPwdLink.click();
	}
	
	public void loginApplication(String email, String pwd) {
		
		emailClear();
		enterEmail( email);
		enterPwd(pwd);
		clickLogin();
	}
	
	public void emailClear() {
		
		emailBtn.clear();
	}
	
	public boolean verifyHomePage() {
		
		if ((emailBtn.isDisplayed())&&(driver.getTitle().contains("Facebook"))) {
		return true;
		}
		
		else {
			return false;
		}
	}
}

