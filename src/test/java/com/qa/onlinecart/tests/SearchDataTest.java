package com.qa.onlinecart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.onlinecart.base.BaseTest;
import com.qa.onlinecart.dataproviders.ProductDataProvider;
import com.qa.onlinecart.pojo.Product;

public class SearchDataTest extends BaseTest {

	@DataProvider
	public Object[][] getProductSearchKeyData() {
		return new Object[][] { { "Macbook" }, { "iMac" }, { "Samsung" } };
	}

	// how to reach till search page, first login
	@BeforeTest
	public void searchSetUp() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
//	Creating a common data provider
	
//	@DataProvider(name = "productData")
//	public Object[][] getProductTestData() {
//		return new Object[][] {
////			create the constructor of Product class
//			{new Product("Macbook", "MacBook Pro", 4)},
//			{new Product("iMac", "iMac", 3)},
//			{new Product("Samsung", "Samsung SyncMaster 941BW", 1)},
//			{new Product("Samsung", "Samsung Galaxy Tab 10.1", 7)}
//		};
//	}

//	Now we have to perform search on AccountsPage
// Here holding data in Product object reference variable and then calling the getter method from the Product class
// Here where we have 2 , 3 .. parameters, don't need to pass 2, 3 parameters, just pass the complete Product object reference
	
	@Test(dataProvider = "productData", dataProviderClass = ProductDataProvider.class)
	public void searchProductResultCountTest(Product product) {
		resultsPage = accPage.doSearch(product.getSearchKey());
		Assert.assertTrue(resultsPage.getProductResultsCount() > 0);
	}

	@Test(dataProvider = "productData", dataProviderClass = ProductDataProvider.class)
	public void searchPageTitleTest(Product product) {
		resultsPage = accPage.doSearch(product.getSearchKey());
		String actSearchTitle = resultsPage.getResultsPageTitle(product.getSearchKey());
		System.out.println("search Page Title :- " + actSearchTitle);
		Assert.assertEquals(actSearchTitle, "Search - " + product.getSearchKey());

	}

	@Test(dataProvider = "productData", dataProviderClass = ProductDataProvider.class)
	public void selectProductTest(Product product, String productName) {
		resultsPage = accPage.doSearch(product.getSearchKey());
		productInfoPage = resultsPage.selectProduct(product.getProductName()); // this line will select the product
		String actProductHeaderName = productInfoPage.getProductHeaderName();
		System.out.println("Actual Product Name :-" + actProductHeaderName);
		Assert.assertEquals(actProductHeaderName, product.getProductName());
	}

	@Test(dataProvider = "productData", dataProviderClass = ProductDataProvider.class)
	public void productImagesTest(Product product) {
		resultsPage = accPage.doSearch(product.getSearchKey());
		productInfoPage = resultsPage.selectProduct(product.getProductName()); // this line will select the product
		int actProductImagesCount = productInfoPage.getProductImageCount();
		System.out.println("Actual product images count :- " + actProductImagesCount);
		Assert.assertEquals(actProductImagesCount, product.getProductImages());
	}

}
