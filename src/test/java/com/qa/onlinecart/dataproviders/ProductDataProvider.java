package com.qa.onlinecart.dataproviders;

import org.testng.annotations.DataProvider;

import com.qa.onlinecart.pojo.Product;

public class ProductDataProvider {

	
	@DataProvider(name = "productData")
	public Object[][] getProductTestData() {
		return new Object[][] {
//			create the constructor of Product class
			{new Product("Macbook", "MacBook Pro", 4)},
			{new Product("iMac", "iMac", 3)},
			{new Product("Samsung", "Samsung SyncMaster 941BW", 1)},
			{new Product("Samsung", "Samsung Galaxy Tab 10.1", 7)}
		};
	}
	
}
