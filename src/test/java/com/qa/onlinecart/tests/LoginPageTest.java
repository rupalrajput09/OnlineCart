package com.qa.onlinecart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.onlinecart.base.BaseTest;
import com.qa.onlinecart.utils.AppConstants;

public class LoginPageTest extends BaseTest {
//	Move this code to BaseTest class, and to access below code start extends the BaseTest class

	/*
	 * WebDriver driver; LoginPage loginPage;
	 * 
	 * @BeforeTest public void setUp() { driver = new ChromeDriver();
	 * driver.manage().window().maximize(); driver.manage().deleteAllCookies();
	 * driver.get(
	 * "https://naveenautomationlabs.com/opencart/index.php?route=account/login");
	 * loginPage = new LoginPage(driver); }
	 */
	@Test
	public void loginPageTitleTest() {
		String actTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE_VALUE);
	}

	@Test
	public void loginPageUrlTest() {
		String actURL = loginPage.getLoginPageURL();
		Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE));
	}

	@Test
	public void forgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}

	@Test
	public void loginTest() {
//		accPage = loginPage.doLogin("Oct@gmail.com", "TestOct@123");
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertTrue(accPage.isLogoutLinkExist());
		Assert.assertTrue(accPage.getAccPageTitle().equals("My Account"));
	}

//	Move this code to BaseTest class
	/*
	 * @AfterTest public void tearDown() { driver.quit(); }
	 */
}
