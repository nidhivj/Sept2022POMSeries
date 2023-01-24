package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

public class RegPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
    private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By passwordCnfrm = By.id("input-confirm");
	
	private By agreeCheckBox = By.name("agree");
	private By continueButton = By.xpath("//input[@type='submit' and @value='Continue']");
	private By subscribeYes = By.xpath("//label[@class='radio-inline']/input[@value='1' and @type='radio']");
	private By subscribeNo = By.xpath("//label[@class='radio-inline']/input[@value='0' and @type='radio']");

	private By registerSuccessMesg = By.cssSelector("div#content h1");
	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");
	
	public RegPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	public boolean registerUser(String firstName, String lastName, String email, String telephone, String password, String subscribe) {
		eleUtil.waitForElementVisible(this.firstName, TimeUtil.DEFAULT_TIME_OUT).sendKeys(firstName);
		eleUtil.doSendKeys(this.lastName, lastName);
		eleUtil.doSendKeys(this.email, email);
		eleUtil.doSendKeys(this.telephone, telephone);
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(this.passwordCnfrm, password);
		
		
		     if(subscribe.equalsIgnoreCase("yes")) {
		    	 eleUtil.doClick(subscribeYes);
		     }
		     else {
		    	 eleUtil.doClick(subscribeNo);
		     }
		     eleUtil.doClick(agreeCheckBox);
		     eleUtil.doClick(continueButton);
		     
		     
		     String succesMesg = eleUtil.waitForElementVisible(registerSuccessMesg, TimeUtil.DEFAULT_TIME_OUT).getText();
		     System.out.println(succesMesg);
		     
		          if (succesMesg.contains(AppConstants.ACCOUNT_REGISTER_SUCCESS_MESSG)) {
		        	 eleUtil.doClick(logoutLink);  //these 2 line has written bcz in case we have to register for another user we need to logout and again get the registration link
		        	 eleUtil.doClick(registerLink);
		        	 return true;
		          }
		          else {
		        	  eleUtil.doClick(registerLink);
		          }
		        return false;  
		
	}
	
	
	
	
	
	

}
