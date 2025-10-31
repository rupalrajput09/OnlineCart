package com.qa.onlinecart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.onlinecart.base.BaseTest;

public class SearchTest extends BaseTest{
	
	
	@DataProvider
	public Object[][] getProductSearchKeyData() {
		return new Object[][] {
			{"Macbook"},
			{"iMac"},
			{"Samsung"}
		};
	}
	
	
	// how to reach till search page, first login
	@BeforeTest
	public void searchSetUp() {
//		taking the LoginPage reference from BaseTest class and use properties by using the prop reference and doLogin() method
//		gives the object of AccountsPage, so store it to AccountsPage reference accPage from BaseTest class
//		This will do the login
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	

//	Now we have to perform search on AccountsPage
	
	@Test(dataProvider = "getProductSearchKeyData")
	public void searchProductResultCountTest(String searchKey) {
//		now we are on AccountsPage so use the AccountsPage reference that is accPage
// 		In testNG this is my test data, we will maintain this test data in excel file later, right we are just verifying 
//		my pages are working or not
//		Make sure in your page it should not be hard coded
		resultsPage = accPage.doSearch(searchKey); 

//		now from resultsPage we want to check the count of the results
		Assert.assertTrue(resultsPage.getProductResultsCount( )> 0);
	}
	
	@Test(dataProvider = "getProductSearchKeyData")
	public void searchPageTitleTest(String searchKey) {
		resultsPage = accPage.doSearch(searchKey);
//		String actSearchTitle = resultsPage.getResultsPageTitle("Macbook");
		String actSearchTitle = resultsPage.getResultsPageTitle(searchKey);
		System.out.println("search Page Title :- " + actSearchTitle);
//		Assert.assertEquals(actSearchTitle, "Search -" + "Macbook");
		Assert.assertEquals(actSearchTitle, "Search - " + searchKey);

	}
	
	@DataProvider
	public Object[][] getProductTestData() {
		return new Object[][] {
			{"Macbook", "MacBook Pro"},
			{"iMac", "iMac"},
			{"Samsung", "Samsung SyncMaster 941BW"},
			{"Samsung", "Samsung Galaxy Tab 10.1"}
		};
	}
	
//	For selectProviderTest, we need two data provider and then two holding parameters
	
	
	@Test(dataProvider = "getProductTestData")
	public void selectProductTest(String searchKey, String productName) {
		resultsPage = accPage.doSearch(searchKey);
		productInfoPage = resultsPage.selectProduct(productName); // this line will select the product
		String actProductHeaderName = productInfoPage.getProductHeaderName();
		System.out.println("Actual Product Name :-" + actProductHeaderName );
		Assert.assertEquals(actProductHeaderName, productName);	
	}
//	@Test
//	public void selectProductTest() {
//		resultsPage = accPage.doSearch("Macbook");
//		productInfoPage = resultsPage.selectProduct("Macbook Pro"); // this line will select the product
//		String actProductHeaderName = productInfoPage.getProductHeaderName();
//		System.out.println("Actual Product Name :-" + actProductHeaderName );
//		Assert.assertEquals(actProductHeaderName, "Macbook Pro");	
//	}
	
	
	@DataProvider
	public Object[][] getProductImagesTestData() {
		return new Object[][] {
			{"Macbook", "MacBook Pro", 4},
			{"iMac", "iMac", 3},
			{"Samsung", "Samsung SyncMaster 941BW", 1},
			{"Samsung", "Samsung Galaxy Tab 10.1", 7}
		};
	}
	
	@Test(dataProvider = "getProductImagesTestData")
	public void productImagesTest(String searchKey, String productName, int expImagesCount) {
		resultsPage = accPage.doSearch(searchKey);
		productInfoPage = resultsPage.selectProduct(productName); // this line will select the product
		int actProductImagesCount = productInfoPage.getProductImageCount();
		System.out.println("Actual product images count :- " + actProductImagesCount);
		Assert.assertEquals(actProductImagesCount, expImagesCount);
	}
	
	
//	@Test
//	public void productImagesTest() {
//		resultsPage = accPage.doSearch("Macbook");
//		productInfoPage = resultsPage.selectProduct("Macbook Pro"); // this line will select the product
//		int actProductImagesCount = productInfoPage.getProductImageCount();
//		System.out.println("Actual product images count :- " + actProductImagesCount);
//		Assert.assertEquals(actProductImagesCount, 4);
//	}
}
