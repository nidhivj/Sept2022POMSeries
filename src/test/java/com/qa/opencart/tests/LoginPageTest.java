package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.AppErrors;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic - 100: Design login page for open cart shopping application")
@Story("Us - 101: Create login page functionality for open cart login page")
public class LoginPageTest extends BaseTest {
	
	//testng doesn't have the driver code like driver.somthing..
	//and likewise page also doesn't have any assertion type something
	//assertion are for validation in our test method or  in testng.. once we are done of every step then we have to write some assertion for validate 
	                                                                                                                      //that are test is correct or not 
	//without assertion test is not complete 
	
	@Description("login page title test")
	@Severity(SeverityLevel.TRIVIAL)
	@Test
	public void loginPageTitleTest() {
		 String actTitle = loginPage.getLoginPageTitle();       //this loginPage method object already created in baseTest class and this LoginPageTest 		 
	     System.out.println("login page title :" +actTitle);    //is inherit the property of baseTest
	     Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE, AppErrors.NO_TITLE_MATCHED );
	
	}

	
	@Description("login page url test")
	@Severity(SeverityLevel.NORMAL)
	@Test
	public void loginPageURLTest() {
		String actURL = loginPage.getLoginPageUrl();
		System.out.println("login page url :" +actURL);
		Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_FRACTION_URL), AppErrors.NO_URL_MATCHED);
	}
	
	@Description("forgot pwd link on login page test")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void forgotPwdLinkExistTest() {
		 Assert.assertTrue(loginPage.isforgotPwdLinkExist());
	}
	
	//so if we don't want to run any test cases thn we have two ways either write enable after @Test(enable = false) or we write in testng.xml write exclude to stop the run test case... and include is use for test run 
	
	@Description("user able to login on login page test")
	@Severity(SeverityLevel.BLOCKER)
	@Test
	public void loginTest() {
		accPage = loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));             //doLogin method gives account page reference so we store in accpage that is coming from 
        Assert.assertTrue(accPage.isLogoutExist(), AppErrors.LOGIN_UNSUCCESSFUL);                                      //baseTest
	}
	
	
}
