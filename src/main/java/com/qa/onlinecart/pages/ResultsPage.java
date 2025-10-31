package com.qa.onlinecart.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.onlinecart.utils.ElementUtil;

public class ResultsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By resultsProduct = By.cssSelector("product-layout product-grid");
	
	
	public ResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	
	public String getResultsPageTitle(String searchKey) {
		return eleUtil.waitForTitleIsAndCapture(searchKey, 5);
//		Here we are passing the search key, if searchkey is in the title then capture it.
	}
	
//	How to verify that searchkey related products are there
//	Can be verify if the product count > 0
	
	public int getProductResultsCount() {
		int resultCount = eleUtil.waitForElementsVisible(resultsProduct, 10).size();
		System.out.println("Product search results count :- " + resultCount);
		return resultCount;
	}
	
	public ProductInfoPage selectProduct(String productName) {
//		 we are maintaining product name locator here bcoz its a dynamic locator
		By productNameLocator = By.linkText(productName);
		eleUtil.doClick(productNameLocator);
		return new ProductInfoPage(driver);
	}
	
	
}
