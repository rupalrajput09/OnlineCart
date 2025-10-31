package com.qa.onlinecart.tests;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.onlinecart.base.BaseTest;
import com.qa.onlinecart.utils.AppConstants;

public class AccountPageTest extends BaseTest {

//	move to BaseTest class 
	/*
	 * WebDriver driver; LoginPage loginPage; AccountsPage accPage; // create at
	 * class level so that it can be used in any method..
	 * 
	 * // Precondition for AccountsPageTest.java is LaunchBrowser, LaunchUrl and
	 * User should be logged in.
	 * 
	 * @BeforeTest public void setUp() { driver = new ChromeDriver();
	 * driver.manage().window().maximize(); driver.manage().deleteAllCookies();
	 * driver.get(
	 * "https://naveenautomationlabs.com/opencart/index.php?route=account/login");
	 * loginPage = new LoginPage(driver); // Here we want to call the doLogin()
	 * method from LoginPage class, and doLogin() method returns the AccountsPage
	 * class object.
	 * 
	 * // doLogin() method returns the AccountsPage class object, So store it in a
	 * AccountsPage class reference variable accPage =
	 * loginPage.doLogin("Oct@gmail.com", "TestOct@123");
	 * 
	 * }
	 */

	// This pre-condition is specific to the AccountsPageTest class 
	@BeforeClass
	public void accPageSetup() {
//		accPage = loginPage.doLogin("Oct@gmail.com", "TestOct@123");
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));

	}

	@Test
	public void accPageTitleTest() {
		String actTitle = accPage.getAccPageTitle();
		Assert.assertEquals(actTitle, AppConstants.ACCOUNTS_PAGE_TITLE_VALUE);
	}

	@Test
	public void isLogoutLinkExistTest() {
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}

	@Test
	public void isMyAccLinkExistTest() {
		Assert.assertTrue(accPage.isMyAccountLinkExist());
	}

	@Test
	public void accPageHeadersCountTest() {
		List<String> actAccHeadersList = accPage.getAccountPageHeadersList();
		Assert.assertEquals(actAccHeadersList.size(), 4);
	}
	
	@Test
	public void accPageHeadersTest() {
		List<String> actAccHeadersList = accPage.getAccountPageHeadersList();
//		Assert.assertTrue(actAccHeadersList.contains("My Account")); In this case we have to assert all the options one by one
//		So create a list and then test , creating an array list
//		List<String> expAccHeadersList = Arrays.asList("My Account", "My Orders", "My Affiliate Account", "Newsletter");
		// changing with AppConstants
//		Now compare actAccHeadersList with expAccHeadersList
		Assert.assertEquals(actAccHeadersList, AppConstants.EXP_ACCOUNTS_HEADER_LIST);
		// one problem is here, what if they add two more options here then what will you do. 
//		It is the change in requirement, you have to refactor the data accordingly
//		What if order is changed, then in that case first sort both the lists and then compare
		
	}

//	move to BaseTest class
	/*
	 * @AfterTest public void tearDown() { driver.quit(); }
	 */

}
