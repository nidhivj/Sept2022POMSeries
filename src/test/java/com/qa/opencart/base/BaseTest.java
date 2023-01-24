package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegPage;
import com.qa.opencart.pages.ResultsPage;

public class BaseTest {
	
	DriverFactory df;          //call the driver factory class
	WebDriver driver;
	protected LoginPage loginPage;       //maintain login page also
	                                     //protected is used so that only child class of base class can inherit the property
	protected AccountPage accPage;
	protected ResultsPage  resultsPage;
	protected ProductInfoPage prodInfoPage;
	
	protected SoftAssert softAssert;
	protected Properties prop;
	protected RegPage regPage;
	
	@BeforeTest
	public void setup() {             //launching the browser and entering the url but right now that code we have written inside the driver factory
		df = new DriverFactory();         //so we call that method for that we have to create the object of driver factory
		prop = df.initiProp();
		driver = df.initDriver(prop);   //this initDriver returning the threadlocal driver and we naming it here by driver so
		                                //that everywhere driver has written we get tlDriver.. 
		
		loginPage = new LoginPage(driver);    //for page chaining concept we call login page and after launching that page we have return acc page from which we go on
		                                       // account page test 
		softAssert = new SoftAssert();    //so if soft assert getting failed it will go on hard assert 
	
		
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
