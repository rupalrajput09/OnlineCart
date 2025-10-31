package com.qa.onlinecart.pojo;

public class Product {
	
	private String searchKey; 
	private String productName;
	private int productImages;
	
// Generate constructor	
	public Product(String searchKey, String productName, int productImages) {
		this.searchKey = searchKey;
		this.productName = productName;
		this.productImages = productImages;
	}

	// Generate getter and setter methods	
	public String getSearchKey() {
		return searchKey;
	}


	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public int getProductImages() {
		return productImages;
	}


	public void setProductImages(int productImages) {
		this.productImages = productImages;
	}

// Generate toString()
	@Override
	public String toString() {
		return "Product [searchKey=" + searchKey + ", productName=" + productName + ", productImages=" + productImages
				+ "]";
	}
	
		
//	Purpose of toString method is to print the Object value on the console. If we don't use this it will print the memory address 
//	of the Object

	
}
