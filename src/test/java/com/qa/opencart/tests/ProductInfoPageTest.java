package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class ProductInfoPageTest extends BaseTest {
	
	@BeforeClass
	public void prodInfoSetUp() {
		accPage = loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
		//login page loged in which give account page
	}
	
	@DataProvider
	public Object[][] getProductTestData(){
		return new Object[][] {
			{"MacBook", "MacBook Pro"},
			{"MacBook", "MacBook Air"},
			{"iMac", "iMac"},
			{"Samsung", "Samsung SyncMaster 941BW"},
			{"Apple", "Apple Cinema 30\""}
			
		};
		
	}

	@Test(dataProvider = "getProductTestData")
	public void productHeaderTest(String searchKey , String mainProductName) {
		resultsPage= accPage.performSearch(searchKey);  //now in account page we performSearch method it will search the product, click on it and go on result page
		prodInfoPage = resultsPage.selectProduct(mainProductName);   //from result page we get selectProduct method that return productInfoPage
		String actHeader = prodInfoPage.getProductHeader();       //productInfoPage give method getProductHeader
		Assert.assertEquals(actHeader, mainProductName);
	}
	
	@DataProvider
	public Object[][] getProductImagesTestData(){
		return new Object[][] {
			{"MacBook", "MacBook Pro", 4 },
			{"MacBook", "MacBook Air", 4},
			{"iMac", "iMac", 3},
			{"Samsung", "Samsung SyncMaster 941BW", 1},
			{"Apple", "Apple Cinema 30\"", 6}
			
		};
		
	}
	
	@Test(dataProvider = "getProductImagesTestData")
	public void productImagesCountTest(String searchKey , String mainProductName, int imageCount) {
		resultsPage= accPage.performSearch(searchKey);  
		prodInfoPage = resultsPage.selectProduct(mainProductName);   
		int actImagesCount = prodInfoPage.getProductImagesCount();       
		Assert.assertEquals(actImagesCount, imageCount);
	}
	
	@Test
	public void productMetaDataTest() {
		resultsPage = accPage.performSearch("Macbook");
		prodInfoPage = resultsPage.selectProduct("MacBook Pro");
		Map<String, String> actProdInfoMap = prodInfoPage.getProductInformation(); //this information is giving map of string
		
		softAssert.assertEquals(actProdInfoMap.get("Brand"), "Apple");               //if any of assertion get failed it didn't stop there it will go all of the assertion
		softAssert.assertEquals(actProdInfoMap.get("Availability"), "In Stock");     // but in hard assertion it stop there if that assertion getting failed so hard assertion only for single assertion
		softAssert.assertEquals(actProdInfoMap.get("actualprice"), "$2,000.00");
		softAssert.assertEquals(actProdInfoMap.get("Reward Points"), "800");
		softAssert.assertAll();   //whenever we use soft assertion we have to use this line too bcz its give the information about which assertion is failed
	}
	
}
