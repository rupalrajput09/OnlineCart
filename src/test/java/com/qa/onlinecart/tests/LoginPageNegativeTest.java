package com.qa.onlinecart.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.onlinecart.base.BaseTest;

public class LoginPageNegativeTest extends BaseTest{

	
	@DataProvider
	public Object[][] incorrectLoginTestData() {
		return new Object[][] {
			{"auto123@gmail.com", "123456"},
			{"test123@gmail.com", "147852"},
			{"david@gmail.com", "david123"},
//			{" ", " "}
		};
	}
	
	@Test(dataProvider = "incorrectLoginTestData")
	public void loginWithWrongCredentialsTest(String userName, String password) {
		Assert.assertTrue(loginPage.doLoginWithWrongCredentials(userName, password));
	}
	
}
