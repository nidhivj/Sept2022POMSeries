package com.qa.opencart.tests;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

public class RegPageTest extends BaseTest {
	
	@BeforeClass
	public void regPageSetUp() {
		regPage = loginPage.navigateToRegisterPage();
	}
	
	//everytime if we to chng data from sheet and run it again then it ask for different email id so for that we create a different method it takes 
	//automatically a random email id
	public String getRandomEmail() {       
		Random random = new Random();
		String email = "septautomation"+random.nextInt(5000)+"@gmail.com";
		return email;
	}

	@DataProvider
	public Object[][] getRegTestData() {
		Object regData[][] = ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
		return regData;
	}
	
	
	
	
	@Test(dataProvider = "getRegTestData") 
	public void registerUserTest(String firstName, String lastName, String telephone, String password, String subscribe) { //for telephone put ' in excel sheet before every no.
		boolean flag = regPage.registerUser(firstName, lastName, getRandomEmail(), telephone, password, subscribe);//just like m1 calling m2 here we call the getRandomEmail method
		Assert.assertTrue(flag);
	}
}
