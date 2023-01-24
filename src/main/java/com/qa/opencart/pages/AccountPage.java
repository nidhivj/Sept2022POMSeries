package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

public class AccountPage {
	
	private WebDriver driver; 
	private ElementUtil eleUtil;
	
	private By search = By.name("search");
	private By searchIcon = By.cssSelector("div#search button");
	private By logoutLink = By.linkText("Logout");
	private By accSecHeaders = By.cssSelector("div#content h2");
	
	//constructor of class
	public AccountPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	public String getAccPageTitle() {
		return eleUtil.waitForTitleIs(AppConstants.ACCOUNTS_PAGE_TITLE, TimeUtil.DEFAULT_TIME_OUT);
	}
	
	public String getAccPageURL() {
		return eleUtil.waitForUrlContains(AppConstants.ACC_PAGE_FRACTION_URL, TimeUtil.DEFAULT_TIME_OUT);
	}

	public boolean isSearchExist() {
		return eleUtil.waitForElementVisible(search, TimeUtil.DEFAULT_TIME_OUT).isDisplayed();
	}
	
	public boolean isLogoutExist() {
		return eleUtil.waitForElementVisible(logoutLink, TimeUtil.DEFAULT_TIME_OUT).isDisplayed();
	}
	
	public List<String> getAccountPageSectionsHeaders() {
	//	List<WebElement> secHeadersList =  driver.findElements(accSecHeaders);
		List<WebElement> secHeadersList =  eleUtil.waitForElementsVisibe(accSecHeaders, TimeUtil.DEFAULT_TIME_OUT);
	
		List<String> secHeadersValList = new ArrayList<String>();
		for(WebElement e : secHeadersList) {
			String text = e.getText();
			secHeadersValList.add(text);
		}
		
		return secHeadersValList;
	}

	public ResultsPage performSearch(String productName) {
		System.out.println("product search for : " +productName);
		  if(isSearchExist()) {
			  eleUtil.doSendKeys(search, productName);
			  eleUtil.doClick(searchIcon);
			  return new ResultsPage(driver);  //TDD
		  }
		  return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
