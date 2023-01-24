package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

public class ResultsPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By searchProduct = By.cssSelector("div.product-layout");
	
	public ResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	public String getSearchPageTitle(String productName) {
		return eleUtil.waitForTitleContains(productName, TimeUtil.DEFAULT_TIME_OUT);
	}

	public int getSearchProductCount() {
		int productCount = eleUtil.waitForElementsVisibe(searchProduct, TimeUtil.DEFAULT_TIME_OUT).size();
	    System.out.println("product search count : " +productCount);
	    return productCount;
	}
	
	public ProductInfoPage selectProduct(String mainProductName) {   //this mainProductName is not maintain above with private By locators creating instant here means TDD
		System.out.println("main product name : " +mainProductName);
		eleUtil.doClick(By.linkText(mainProductName));
	    return new ProductInfoPage(driver);  //TDD
	}
	
	
}
