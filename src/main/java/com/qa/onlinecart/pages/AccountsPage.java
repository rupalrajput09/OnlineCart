package com.qa.onlinecart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.onlinecart.utils.ElementUtil;
import com.qa.onlinecart.pages.ResultsPage;
import com.qa.onlinecart.utils.AppConstants;

public class AccountsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	// 1. constructor of the page class

	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);
	}

	// 2. private by locators :

	private By logout = By.linkText("Logout");
	private By myAccount = By.linkText("My Account");
	private By accHeaders = By.cssSelector("div#content h2");
	private By search = By.name("search");
	private By searchIcon = By.cssSelector("div#search button");

	// 3. public page actions/ methods :

	public String getAccPageTitle() {
//		String title = driver.getTitle();
//		System.out.println("Account page title : " + title);
//		return title;
		return eleUtil.waitForTitleIsAndCapture(AppConstants.ACCOUNTS_PAGE_TITLE_VALUE, AppConstants.SHORT_DEFAULT_WAIT);
	}

	public boolean isLogoutLinkExist() {
//		return driver.findElement(logout).isDisplayed();
		return eleUtil.checkElementIsDisplayed(logout);
	}

	public boolean isMyAccountLinkExist() {
//		return driver.findElement(myAccount).isDisplayed();
		return eleUtil.checkElementIsDisplayed(myAccount);
	}

	public List<String> getAccountPageHeadersList() {
//		List<WebElement> headersList = driver.findElements(accHeaders);
		List<WebElement> headersList = eleUtil.waitForElementsVisible(accHeaders, AppConstants.MEDIUM_DEFAULT_WAIT);
		List<String> headersValList = new ArrayList<String>();
		for (WebElement e : headersList) {
			String text = e.getText();
			headersValList.add(text);
		}
		return headersValList;
	}

	public ResultsPage doSearch(String searchTerm) {
//		driver.findElement(search).sendKeys(searchTerm);
//		driver.findElement(searchIcon).click(); // after this we are landing on new page (name it as ResultsPage here)
//		return new ResultsPage(driver);

//		eleUtil.waitForElementVisible(search, 10).sendKeys(searchTerm);
		eleUtil.waitForElementVisible(search, AppConstants.MEDIUM_DEFAULT_WAIT);
		eleUtil.doSendKeys(search, searchTerm);
		eleUtil.doClick(searchIcon);
		return new ResultsPage(driver);

	}
}
