package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

import io.qameta.allure.Step;

public class LoginPage {

	//firstly we use private driver
	
	private WebDriver driver ;
	private ElementUtil eleUtil;     //calling element util
	
	//1. private By locators:   calling page objects also
	
	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwdLink = By.linkText("Forgotten Password");
	private By registerLink = By.linkText("Register");
	
	//2. page constructor:
	
	public LoginPage(WebDriver driver) {   //whenever we call that login page this constructor will be call 
		this.driver = driver;              //help to initialize driver
	    eleUtil = new ElementUtil(driver);   // so here i initialize the element util reference.. now we don't need to do driver. things we just call this method
	}
	
	//these are page action
	@Step("getting login page title..")
	public String getLoginPageTitle() {
	//	return driver.getTitle();            //using element util methods instead of this
		return eleUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, TimeUtil.DEFAULT_TIME_OUT);
	}
	
	@Step("getting login page url")
	public String getLoginPageUrl() {
		return eleUtil.waitForUrlContains(AppConstants.LOGIN_PAGE_FRACTION_URL, TimeUtil.DEFAULT_TIME_OUT);
	}
	
	@Step("checking forgot pwd link exist")
	public boolean isforgotPwdLinkExist() {
	//	return driver.findElement(forgotPwdLink).isDisplayed();
	    return eleUtil.doIsDisplayed(forgotPwdLink);
	}
	
	@Step("login with username:{0} and password:{1}")
	public AccountPage doLogin(String un, String pwd) {                    //so private variables use in public methods this is called encapsulation
		System.out.println("creds are :" + un + ":" + pwd);          //so this page class is best example of encapsulation a/c to interview
//		driver.findElement(emailId).sendKeys(un);
//		driver.findElement(password).sendKeys(pwd);
//		driver.findElement(loginBtn).click();

		eleUtil.waitForElementVisible(emailId, TimeUtil.DEFAULT_TIME_OUT).sendKeys(un);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		
		return new AccountPage(driver);         //page chaining concept   after calling this class reference it will return account page reference their we have beforeClass
		                                        //concept in which we are storing doLogin method
		//	return driver.findElement(By.linkText("Logout")).isDisplayed();
		
	}
	
	//navigate to register page
	@Step("navigate to register page")
	public RegPage navigateToRegisterPage() {
		eleUtil.doClick(registerLink);
		return new RegPage(driver);
	}
	
	
	
	
}
