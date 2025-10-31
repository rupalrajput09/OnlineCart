package com.qa.onlinecart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.onlinecart.base.BaseTest;
import com.qa.onlinecart.utils.AppConstants;

public class RegisterPageTest extends BaseTest{
	
//	For registration, we have to click on the Register link, So create the By locator in LoginPage class
	
	@BeforeClass
	public void regSetup() {
		registerPage = loginPage.navigateToRegisterPage();
	}
	
	public String getRandomEmailID() {
		return "testautomation"+System.currentTimeMillis()+"@gmail.com";
		//return "testautomation" + UUID.randomUUID()+"@gmail.com";
		
	}

//	@Test
//	public void userRegisterTest() {
//	
//		String actRegSuccMessg = registerPage.registerUser("navi","anand","navi@gmail.com","123456789","abhi@123", "yes"); 
//		Assert.assertEquals(actRegSuccMessg, AppConstants.USER_RESG_SUCCESS_MESSG);
//	}
	
//	@DataProvider(name = "regData")
//	public Object[][] getUserRegTestData() {
//		return new Object[][] {
//			{"abhi", "anand", "9876545678", "abhi@123", "yes"},
//			{"robinson", "matinez", "9876545600", "robin@123", "no"},
//			{"amber", "automation", "9876545998", "amber@123", "yes"},
//		};
//	}
	
	@Test(dataProvider = "regExcelData")
	public void userRegisterTest(String firstName, String lastName, String telephone, String password, String subscribe) {
		String actRegSuccMessg = 
				registerPage.registerUser(firstName, lastName, getRandomEmailID(), telephone, password, subscribe);
		Assert.assertEquals(actRegSuccMessg, AppConstants.USER_RESG_SUCCESS_MESSG);
	}
}


