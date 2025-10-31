package com.qa.onlinecart.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.onlinecart.utils.AppConstants;
import com.qa.onlinecart.utils.ElementUtil;

public class ProductInfoPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	private By productHeader = By.cssSelector("div#content h1");
	private By productImages = By.cssSelector("ul.thumbnails img");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=1]/li");
	private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=2]/li");
	private By quantity = By.id("input-quantity");
	private By addToCartBtn = By.id("button-cart");
	
	private Map<String, String> productInfoMap;
	

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

//	checking the header
	public String getProductHeaderName() {
		return eleUtil.doGetElementText(productHeader);
	}
	
//	getting the image count
	public int getProductImageCount() {
		return eleUtil.waitForElementsVisible(productImages, AppConstants.MEDIUM_DEFAULT_WAIT).size();
	}
	
//	this method will you the complete product info, calling both the methods and return the productInfoMap and instead of void 
//	write the Map
	public Map<String,String> getProductInfo() {
//		productInfoMap = new HashMap<String, String>(); not maintaing order
		productInfoMap = new LinkedHashMap<String, String>(); // maintain the order
		productInfoMap = new TreeMap<String, String>();// it will store data on the basis of sorted order

		getProductMetaData();
		getProductPriceData();
//		get the header also
		productInfoMap.put("productname", getProductHeaderName());
		return productInfoMap;
	}
	
//	We have to get the data in below format (Key value pair)
//	Brand: Apple
//	Product Code: Product 18
//	Reward Points: 800
//	Availability: Out Of Stock
	
	
//	Make these two methods private bcoz they are not returning anything and we are calling them in getProductInfo() method
//	public void getProductMetaData()
	private void getProductMetaData(){
		List<WebElement> metaList = eleUtil.getElements(productMetaData);
		for(WebElement e : metaList) {
			String metaText = e.getText();
//			productInfoMap = new HashMap<String, String>(); remove it from here and paste it in getProductInfo() method
//			now split the data on the basis on : and use the split method, split method returns String array
			String metaInfo[] = metaText.split(":");
			String key = metaInfo[0].trim();
			String value = metaInfo[1].trim();
//			which key structure we have to use to store data, use HashMap bcoz it stores data in the form of key and value pair
//			create a private HashMap at class level and do topcasting with Map 
//			start filling the value in productInfoMap
			productInfoMap.put(key, value);
			
		}
		}
	
//	$2,000.00 only key (index 0 --> get(0))
//	Ex Tax: $2,000.00 key value both
//		public void getProductPriceData()
		private void getProductPriceData() {
			List<WebElement> priceList = eleUtil.getElements(productPriceData);
//			priceList.get(0); // List is order based, so to get only price using this with index 0
			String priceValue = priceList.get(0).getText(); //$2,000.00
			String exTaxPrice = priceList.get(1).getText();
			String exTaxPriceValue = exTaxPrice.split(":")[1].trim();//$2,000.00
			
//			If you don't have any key, you can add your own custom key here "productprice".
			productInfoMap.put("productprice", priceValue);
			productInfoMap.put("extaxprice", exTaxPriceValue);
			
			
			}
		
	// To test this class, create ProductPageInfoTest
	
		
		
//		difference between HashMap and LinkedHashMap, both are the classes and child of Map interface
}
