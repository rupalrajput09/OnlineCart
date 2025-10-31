package com.qa.onlinecart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.onlinecart.base.BaseTest;

public class ProductPageInfoTest extends BaseTest{
	
	@BeforeClass
	public void accPageSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test
	public void productInfoTest() {
		resultsPage = accPage.doSearch("Macbook");
		productInfoPage = resultsPage.selectProduct("Macbook Pro");
		Map<String, String> productInfoMap = productInfoPage.getProductInfo(); // this method not returning anything so no point
//		in returning the next landing page class object, giving you Map of String so just store in Map
		System.out.println(productInfoMap);
		
		/*
		 * Assert.assertEquals(productInfoMap.get("Brand"), "Apple");
		 * Assert.assertEquals(productInfoMap.get("Availability"), "In Stock");
		 * Assert.assertEquals(productInfoMap.get("productname"), "Macbook Pro");
		 * Assert.assertEquals(productInfoMap.get("productprice"), "$2,000.00");
		 */
		
		softAssert.assertEquals(productInfoMap.get("Brand"), "Apple");
		softAssert.assertEquals(productInfoMap.get("Availability"), "In Stock");
		softAssert.assertEquals(productInfoMap.get("productname"), "Macbook Pro");
		softAssert.assertEquals(productInfoMap.get("productprice"), "$2,000.00");
		softAssert.assertAll();
	}

}
