package com.facebook.pageLibrary;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;


public class loginPage {
	
	WebDriver driver;
	
	
	@FindBy(how=How.XPATH, using="//div[text()='Account Settings']")
	WebElement accountDropdown; 
	
	@FindBy(how=How.XPATH, using="//input[@placeholder='Search' and @data-testid='search_input']")
	public WebElement searchBar;
	
	@FindBy(how=How.XPATH, using="//div[text()='Log Out']")
	public WebElement logOutLink;
	
	@FindBy(how=How.XPATH, using="//a[text()='Not Now']")
	WebElement notification;
	
	public loginPage(WebDriver driver) {
		
		this.driver=driver;
	}
	

	public void clickAccntDD() {
		
		//Assert.assertTrue(myAccountbtn.isDisplayed(),"My account button is not visible");
		accountDropdown.click();
	}
	
	public void clickLogOut() {
		
		logOutLink.click();
	}
	
	public boolean verifyLogin() {
		
		if (searchBar.isDisplayed()) {
		return true;
		}
		
		else {
			return false;
		}
	}
	
	public void applicationLogOut() throws Exception {
		
		accountDropdown.click();
		Thread.sleep(2000);
		logOutLink.click();
	}
	
	public void notifyBtn() {
		
		if(notification.isDisplayed()) {
			
			notification.click();
		}
		
	}
}

