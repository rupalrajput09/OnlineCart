package com.qa.onlinecart.factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import com.qa.onlinecart.frameworkexception.FrameException;

public class DriverFactory {

	WebDriver driver;

//	public WebDriver initDriver(String browserName)
	public WebDriver initDriver(Properties prop) {
//	How will you take the browser property from prop reference
		String browserName = prop.getProperty("browser").trim();// it will give you String so store it in String

		System.out.println("Browser name is :" + browserName);

		switch (browserName.toLowerCase()) {
		case "chrome":
			driver = new ChromeDriver();
			break;
		case "safari":
			driver = new SafariDriver();
			break;
		case "firefox":
			driver = new FirefoxDriver();
			break;

		default:
			System.out.println("Plz pass the right browser : " + browserName);
			throw new FrameException("NOBROWSERFOUNDEXCEPTION");
//			break; remove the break bcoz we are throwing our own exception, and break is a unreachable code now
		}
//		Driver has initialized, now launch the url
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
//		driver.get("https://naveenautomationlabs.com/opencart/index.php?route=account/login");
//		replace with -
		driver.get(prop.getProperty("url"));
//		now I want to return the driver from this particular function, so return the driver
		return driver;

	}

	public Properties initProp() {
		Properties prop = new Properties();
		FileInputStream ip = null;
		
		// mvn clean install -Denv="qa"
		// mvn clean install (means env name is null)
		String envName = System.getProperty("env");
		System.out.println("Env name is : " + envName);
		
		try {
			
		if(envName == null) {
			System.out.println("No environment is given .. hence running it on QA env..");
				ip = new FileInputStream("./src/main/resources/config/config.properties");
		}
		else {
			System.out.println("Running test cases on env .. " + envName);
			switch (envName.toLowerCase().trim()) {
			case "qa":
					ip = new FileInputStream("./src/main/resources/config/qa.config.properties");
				break;
			case "stage":
					ip = new FileInputStream("./src/main/resources/config/stage.config.properties");
				break;
			case "dev":
					ip = new FileInputStream("./src/main/resources/config/dev.config.properties");
				break;

			default:
				System.out.println("Plz pass the right env name :- " + envName);
				throw new FrameException("NOVALIDENVGIVEN");
				
			}
		}
		
	}
		catch (FileNotFoundException e) {
			e.printStackTrace();

	}
		// load the properties also
		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
}
		
		
		/*
		 * we have to interact with config.properties file, So create an object of
		 * FileInputStream. This FileInputStram will make connection with
		 * config.properties Properties class is specially designed to interact with the
		 * properties file. FileInputStream file might give you FileNotFoundException so
		 * enclosed in try catch block
		 */
//		Properties prop = new Properties();
//		try {
////			FileInputStream ip = new FileInputStream("./src/main/resources/config/config.properties");
//			ip = new FileInputStream("./src/main/resources/config/config.properties");
////			here ./ means whatever current project you are using you have to use it here. 
//			try {
//				prop.load(ip);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		return prop;
//	}


