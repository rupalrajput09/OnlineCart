package com.qa.onlinecart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.qa.onlinecart.factory.DriverFactory;
import com.qa.onlinecart.pages.AccountsPage;
import com.qa.onlinecart.pages.LoginPage;
import com.qa.onlinecart.pages.ProductInfoPage;
import com.qa.onlinecart.pages.RegisterPage;
import com.qa.onlinecart.pages.ResultsPage;

public class BaseTest {
	
//	make them protected, so that can be accessed in LoginPageTest class
	WebDriver driver;
	protected LoginPage loginPage;
	protected AccountsPage accPage;
	protected ResultsPage resultsPage;
	protected ProductInfoPage productInfoPage;
	protected RegisterPage registerPage;

	protected DriverFactory df;
	protected Properties prop;
	
	protected SoftAssert softAssert;
	
	@Parameters({"browser"}) //this browser value is coming from xml
	@BeforeTest
//	supplying (String browserName) as a holding parameter for @Parameters({"browser"})
//	public void setUp(@Optional("chrome") String browserName) 
	public void setUp(String browserName)
	{
		/*
		 * remove this code as we have written it in initDriver() method in DriverFactory class
		 * driver = new ChromeDriver(); driver.manage().window().maximize();
		 * driver.manage().deleteAllCookies(); driver.get(
		 * "https://naveenautomationlabs.com/opencart/index.php?route=account/login");
		 */
		df = new DriverFactory(); 
		prop = df.initProp(); //store it in Properties reference bcoz it will give you complete properties object
		driver = df.initDriver(prop); // it will give you driver
		// to get the browser data from xml file put a check
		// and if you are not supplying browser value in testNG.xml file then it will not come here 
		if(browserName !=null) {
			prop.setProperty("browser", browserName); // here "browser" is coming from config file
		}
		
		
//		here this driver is important bcoz same driver can be given to login page, bcoz login page constructor is
//		waiting for the driver.
		loginPage = new LoginPage(driver);
		softAssert = new SoftAssert();
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
