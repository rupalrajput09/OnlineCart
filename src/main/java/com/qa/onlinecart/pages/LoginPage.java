package com.qa.onlinecart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.onlinecart.utils.AppConstants;
import com.qa.onlinecart.utils.ElementUtil;

public class LoginPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

//  1.constructor of the page class
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);
//		here we used ElementUtil(this.driver), so that WebDriver driver can be used here with same session id
	}

	// 2. private By locators:
	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwdlink = By.linkText("Forgotten Password");
	private By footerLinks = By.xpath("//footer//a");
//	private By loginErrorMessg = By.cssSelector("div.alert.alert-danger.alert-dismissible");
	private By loginErrorMessg = By.xpath("//div[@class='alert alert-danger alert-dismissible']");
	
//	This register link is for RegisterPage
	private By registerLink = By.linkText("Register");

	// 3. public page actions/methods:
	public String getLoginPageTitle() {
//		String title = driver.getTitle();
//		System.out.println("Title is " + title);
//		return title;
//		return eleUtil.waitForFullTitleAndCapture("Account Login", 5); 
//		changing hard coded values like "Account Login" by using the AppConstants class, It is static in nature
//		so can be used with class name
		return eleUtil.waitForFullTitleAndCapture(AppConstants.LOGIN_PAGE_TITLE_VALUE, AppConstants.SHORT_DEFAULT_WAIT);

	}

	public String getLoginPageURL() {
//		String url = driver.getCurrentUrl();
//		System.out.println("URL is " + url);
//		return url;
		return eleUtil.waitForURLContainsAndCapture(AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE, AppConstants.SHORT_DEFAULT_WAIT);
	}

	public boolean isForgotPwdLinkExist() {
//		return driver.findElement(forgotPwdlink).isDisplayed();
		return eleUtil.checkElementIsDisplayed(forgotPwdlink);
	}

	public List<String> getFooterLinksList() {
//		List<WebElement> footerLinksList = driver.findElements(footerLinks);
		List<WebElement> footerLinksList = eleUtil.waitForElementsVisible(footerLinks, AppConstants.MEDIUM_DEFAULT_WAIT);

		List<String> footerTextList = new ArrayList<String>();
		for (WebElement e : footerLinksList) {
			String text = e.getText();
			footerTextList.add(text);
		}
		return footerTextList;
	}

	public AccountsPage doLogin(String userName, String pwd) {
		System.out.println("Correct credential are :- " + userName + ":" + pwd);
//		driver.findElement(emailId).sendKeys(userName);
//		driver.findElement(password).sendKeys(pwd);
//		driver.findElement(loginBtn).click();
		eleUtil.waitForElementVisible(emailId, AppConstants.MEDIUM_DEFAULT_WAIT);// waiting for emailId thats why used it
		eleUtil.doSendKeys(emailId, userName);
		// this method will give you WebElement so directly write sendKeys()method
		eleUtil.doSendKeys(password, pwd); // no need to wait , so used doSendKeys()
		eleUtil.doClick(loginBtn);
		
		// return the next landing page - AccountPage -- page chaining model
		return new AccountsPage(driver);
//		Can we return this keyword here, No this keyword means current class object that we return. And current class object 
//				means it will return the LoginPage class object.

	}
	
	public boolean doLoginWithWrongCredentials(String userName, String pwd) {
		System.out.println("Wrong credential are :- " + userName + ":" + pwd);
		eleUtil.waitForElementVisible(emailId, AppConstants.MEDIUM_DEFAULT_WAIT);
		eleUtil.doSendKeys(emailId, userName);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
//	here we will not return the next landing page class object.
		String errorMessg= eleUtil.doGetElementText(loginErrorMessg);
		System.out.println("Error Messg :- " + errorMessg);
		if(errorMessg.contains(AppConstants.LOGIN_ERROR_MESSAGE)) {
			return true;
		}
		return false;
	}
	
	public RegisterPage navigateToRegisterPage() {
		eleUtil.doClick(registerLink);
		return new RegisterPage(driver);
	}

}
