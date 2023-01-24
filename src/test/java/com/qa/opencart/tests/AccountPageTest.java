package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.AppErrors;

public class AccountPageTest extends BaseTest{
	
	//we are starting from login page then landing on accounts page
	
	//base test have beforeTest and this class have beforClass so first beforeTest execute then BeforClass
	@BeforeClass
	public void accSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
	}

	@Test
	public void accPageTitleTest() {
		String actTitle = accPage.getAccPageTitle();
		System.out.println("acc page Title :" +actTitle);
		Assert.assertEquals(actTitle, AppConstants.ACCOUNTS_PAGE_TITLE);
	}
	
	@Test
	public void accPageURLTest() {
		String actURL = accPage.getAccPageURL();
		System.out.println("acc page url : "+actURL);
		Assert.assertTrue(actURL.contains(AppConstants.ACC_PAGE_FRACTION_URL), AppErrors.NO_URL_MATCHED);
	}
	
	@Test
	public void searchExitTest() {
		Assert.assertTrue(accPage.isSearchExist());
	}
	
	@Test
	public void logoutExistTest() {
		Assert.assertTrue(accPage.isLogoutExist());
	}
	
	@Test
	public void accPageHeadersTest() {
		List<String> actHeadersList = accPage.getAccountPageSectionsHeaders();
		Assert.assertEquals(actHeadersList, AppConstants.EXPECTED_ACC_HEADERS_LIST);
	}
	
	@DataProvider
	public Object[][] getProductName() {
		return new Object[][] {
			{"Macbook"},
			{"iMac"},
			{"Samsung"}
			
		};
	}
	
    //TDD testDrivineDevlopment it means you know the test already but you didn't create any method for that
	//we are not creating first in page method we are doing in reverse first we creating test then method from this
	@Test(dataProvider = "getProductName")
	public void productSearchTest(String productName) {
		resultsPage = accPage.performSearch(productName);
		String actTitle = resultsPage.getSearchPageTitle(productName);
		System.out.println("search page title : " +actTitle);
		//Assert.assertEquals(actTitle, AppConstants.SEARCH_PAGE_TITLE+" "+productName);  //before productName extra space we need here bcz in title we having space
		//the above assertion is a soft assertion either you use it or convert into soft assertion
		softAssert.assertEquals(actTitle, AppConstants.SEARCH_PAGE_TITLE+" "+productName);  
		Assert.assertTrue(resultsPage.getSearchProductCount()>0);    //this assertion is hard assertion it gives count
	}
	







}
