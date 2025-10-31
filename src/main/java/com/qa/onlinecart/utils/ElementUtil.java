package com.qa.onlinecart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.onlinecart.frameworkexception.FrameException;

public class ElementUtil {

	private WebDriver driver;
	private final int DEFAULT_TIME_OUT = 5;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
	}

	// with specific wait
	public WebElement getElement(By locator, int timeOut) {
		return waitForElementVisible(locator, timeOut);
	}

//	public WebElement getElement(By locator) {
//		return driver.findElement(locator);
//	}
	// With more checks getElement() method
	// with standard timeout time
	public WebElement getElement(By locator) {
		WebElement element = null;
		try {
			element = driver.findElement(locator);
			System.out.println("Element is found with locator: " + locator);
		} catch (NoSuchElementException e) {
			System.out.println("Element is not found using this locator: " + locator);
			element = waitForElementVisible(locator, DEFAULT_TIME_OUT);
		}
		return element;
	}

	public void doSendKeys(By locator, String value) {
		if (value == null) {
			System.out.println("null values are not allowed");
			throw new FrameException("VALUECANNOTBENULL");
		}
		doClear(locator);
		getElement(locator).sendKeys(value);
	}

	public void doClear(By locator) {
		getElement(locator).clear();
	}

	public void doClick(By locator) {
		getElement(locator).click();
	}

	public void doClick(By locator, int timeOut) {
		checkElementClickable(locator, timeOut).click();
	}

	public String doGetElementText(By locator) {
		return getElement(locator).getText();
	}

	public boolean checkElementIsDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}

	public String doGetAttributeValue(By locator, String attrName) {
//		return getElement(locator).getAttribute(attrName); // deprecated in 4.27
//		return getElement(locator).getDomAttribute(attrName);

		return getElement(locator).getDomProperty(attrName);

	}

	public int getElementsCount(By locator) {
		return getElements(locator).size();
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public List<String> getElementsTextList(By locator) {
		List<WebElement> elementsLinksList = getElements(locator);
		List<String> elesTextList = new ArrayList<String>();

		for (WebElement e : elementsLinksList) {
			String text = e.getText();
			elesTextList.add(text);
		}
		return elesTextList;
	}

	public List<String> getElementsAttributeValue(By locator, String attrName) {
		List<WebElement> eleList = getElements(locator);
		List<String> eleAttrList = new ArrayList<String>();// 0

		for (WebElement e : eleList) {
			String attrValue = e.getAttribute(attrName);
//			System.out.println(attrValue);
			eleAttrList.add(attrValue); // adding value in ArrayList
		}
		return eleAttrList;
	}

	public void clickElementFromPageSection(By locator, String eleText) {
		List<WebElement> eleList = getElements(locator);
		for (WebElement e : eleList) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(eleText)) {
				e.click();
				break;
			}
		}

	}

	public void search(String searchKey, By searchLocator, String suggName, By suggestions)
			throws InterruptedException {

//		driver.findElement(searchLocator).sendKeys(searchKey);
		doSendKeys(searchLocator, searchKey); // here using doSendKeys method
		Thread.sleep(3000);

//		List<WebElement> suggList = driver.findElements(suggestions);
		List<WebElement> suggList = getElements(suggestions);
		System.out.println("Total Suggestions: " + suggList.size());

		if (suggList.size() > 0) {
			for (WebElement e : suggList) {
				String text = e.getText();
				if (text.length() > 0) {
					System.out.println(text);
					if (text.contains(suggName)) {
						e.click();
						break;
					}
				} else {
					System.out.println("blank values -- no suggestion");
					break;
				}
			}
		} else {
			System.out.println("No search suggesstion found");
		}
	}

	// Element is displayed on the page or not
	public boolean isElementDisplayed(By locator) {
		List<WebElement> eleList = getElements(locator);
		if (eleList.size() > 0) {
			System.out.println(locator + "Element is present on the page");
			return true;
		} else {
			return false;
		}
	}

	// ************************** Drop Down
	// Utils*************************************

	public void doSelectDropDownByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	public void doSelectDropDownByVisibleText(By locator, String text) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(text);
	}

	public void doSelectDropDownByValueAttribute(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}

	public int getDropDownValue(By locator) {
		return getAllDropDownOptions(locator).size();
	}

	public List<String> getAllDropDownOptions(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> OptionsLists = select.getOptions();
		List<String> optionsValueList = new ArrayList<String>();
		System.out.println("Total countries :- " + OptionsLists.size());

		for (WebElement e : OptionsLists) {
			String text = e.getText();
			System.out.println(text);
			optionsValueList.add(text);
		}
		return optionsValueList;
	}

	public boolean doSelectDropDownValue(By locator, String dropDownValue) {
		boolean flag = false;
		Select select = new Select(getElement(locator));
		List<WebElement> OptionsLists = select.getOptions();
		System.out.println("Total countries :- " + OptionsLists.size());

		for (WebElement e : OptionsLists) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(dropDownValue)) {
				flag = true;
				e.click();
				break;
			}
		}
		if (flag == false) {
			System.out.println(dropDownValue + "is not present in the drop down " + locator);
		}
		return flag;
	}

	public boolean doSelectValueFromDropDownWithoutSelect(By locator, String value) {
		boolean flag = false;
		List<WebElement> optionsList = getElements(locator);
		for (WebElement e : optionsList) {
			String text = e.getText();
			if (text.equals(value)) {
				flag = true;
				e.click();
				break;
			}
		}
		if (flag == false) {
			System.out.println(value + "is not present in the drop down " + locator);
		}
		return flag;
	}

	// ************************ ActionsClassUtils********************************

	// For Context click
	public void doContextClick(By locator) {
		Actions act = new Actions(driver);
		act.contextClick(getElement(locator)).build().perform();
	}

	// For mouse movement
	public void doMoveToElement(By locator) {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(locator)).build().perform();
	}

	// For drag and drop
	public void doDragAndDrop(By sourceLocator, By targetLocator) {
		Actions act = new Actions(driver);
		act.dragAndDrop(getElement(sourceLocator), getElement(targetLocator));
	}

	// two level
	public void handleTwoLevelMenu(By parentMenu, By childMenu) throws InterruptedException {
//		WebElement parentLevelMenu = getElement(parentMenu); // now no need as we have used doMoveToElement()
//		Actions act = new Actions(driver); // now no need as we have used doMoveToElement()
//		act.moveToElement(parentLevelMenu).build().perform(); // now no need as we have used doMoveToElement()
		doMoveToElement(parentMenu);
		Thread.sleep(2000);
		doClick(childMenu);
	}

	public void handleTwoLevelMenu(By parentMenu, String childMenuLinkText) throws InterruptedException {
//		WebElement ContentMenu = getElement(parentMenu); // now no need as we have used doMoveToElement()
//		Actions act = new Actions(driver); // now no need as we have used doMoveToElement()
//		act.moveToElement(ContentMenu).build().perform(); // below is the replacement
		doMoveToElement(parentMenu);
		Thread.sleep(2000);
		doClick(By.linkText(childMenuLinkText));
	}

	// For multilevel
	public void multiLevelMenuChildMenuHandle(By parentMenulocator, String level2LinkText, String level3LinkText,
			String level4LinkText) throws InterruptedException {

		WebElement level1 = getElement(parentMenulocator);
		Actions act = new Actions(driver);
		act.moveToElement(level1).build().perform();
		Thread.sleep(1000);

		WebElement level2 = getElement(By.linkText(level2LinkText));
		act.moveToElement(level2).build().perform();
		Thread.sleep(1000);

		WebElement level3 = getElement(By.linkText(level3LinkText));
		act.moveToElement(level3).build().perform();
		Thread.sleep(1000);

		doClick(By.linkText(level4LinkText));
	}

	public void doActionsSendKeys(By locator, String value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).build().perform();
	}

	public void doActionsClick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).build().perform();
	}

	public void doActionsClick(By locator, int timeOut) {
		Actions act = new Actions(driver);
//		act.click(getElement(locator)).build().perform();
		act.click(checkElementClickable(locator, timeOut)).build().perform();
	}

	// ********************Wait Utils******************************//
	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does not necessarily mean that the element is visible.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public WebElement waitForElementPresence(By locator, int timeoOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoOut));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible on the page. Visibility means that the element is not only
	 * displayed but also has a height and width that isgreater than 0. default
	 * timeout = 500ms
	 * 
	 * @param locator
	 * @param timeoOut
	 * @return
	 */
	public WebElement waitForElementVisible(By locator, int timeoOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoOut));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	// default timeout = intervelTime
	public WebElement waitForElementVisible(By locator, int timeoOut, int intervelTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoOut), Duration.ofSeconds(intervelTime));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	/**
	 * An expectation for checking that all elements present on the web page that
	 * match the locatorare visible. Visibility means that the elements are not only
	 * displayed but also have a heightand width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */

	public List<WebElement> waitForElementsVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	/**
	 * An expectation for checking that there is at least one element present on a
	 * web page.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public List<WebElement> waitForElementsPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	/**
	 * An expectation for checking an element is visible and enabled such that you
	 * can click it.
	 * 
	 * @param locator
	 * @param timeOut
	 */
	public void clickElementWhenReady(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}

	/**
	 * An expectation for checking an element is visible and enabled such that you
	 * can click it.
	 * 
	 * @param locator
	 * @param timeoOut
	 * @return
	 */
	public WebElement checkElementClickable(By locator, int timeoOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoOut));
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	// ********************** AlertWaitUtils****************************//

	public Alert waitForAlertJsPopUpWithFluentWait(int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.ignoring(NoAlertPresentException.class).pollingEvery(Duration.ofSeconds(pollingTime))
				.withMessage("------time out is done...Alert is not found.....");

		return wait.until(ExpectedConditions.alertIsPresent());

	}

	public Alert waitForAlertJsPopup(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public String alertJsGetText(int timeOut) {
		return waitForAlertJsPopup(timeOut).getText();
	}

	public void alertAccept(int timeOut) {
		waitForAlertJsPopup(timeOut).accept();
	}

	public void alertDismiss(int timeOut) {
		waitForAlertJsPopup(timeOut).dismiss();
	}

	public void enterAlertValue(int timeOut, String value) {
		waitForAlertJsPopup(timeOut).sendKeys(value);
	}

	// ************************ TitleURL WaitUtils *******************//

	public String waitForTitleIsAndCapture(String titleFraction, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if (wait.until(ExpectedConditions.titleContains(titleFraction))) {
			String title = driver.getTitle();
			return title;
		} else {
			System.out.println("title is not present within the given timeout : " + timeOut);
			return null;
		}
	}

	public String waitForFullTitleAndCapture(String titleVal, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if (wait.until(ExpectedConditions.titleIs(titleVal))) {
			String title = driver.getTitle();
			return title;
		} else {
			System.out.println("title is not present within the given timeout : " + timeOut);
			return null;
		}
	}

	public String waitForURLContainsAndCapture(String urlFraction, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if (wait.until(ExpectedConditions.urlContains(urlFraction))) {
			String url = driver.getCurrentUrl();
			return url;
		} else {
			System.out.println("url is not present within the given timeout : " + timeOut);
			return null;
		}
	}

	public String waitForURLAndCapture(String urlValue, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if (wait.until(ExpectedConditions.urlToBe(urlValue))) {
			String url = driver.getCurrentUrl();
			return url;
		} else {
			System.out.println("url is not present within the given timeout : " + timeOut);
			return null;
		}
	}

	// ********* WaitUtil For Window *******************//
	public boolean waitForTotalWindows(int totalWindowsToBe, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.numberOfWindowsToBe(totalWindowsToBe));
	}

	// **************** frames with wait: ************************************* //

	public void waitForFrameAndSwitchToItWithFluentWait(int timeOut, int pollingTime, String idOrName) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.ignoring(NoSuchFrameException.class).pollingEvery(Duration.ofSeconds(pollingTime))
				.withMessage("------time out is done...frame is not found.....");
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(idOrName));
	}

	public void waitForFrameAndSwitchToItByIDOrName(int timeOut, String idOrName) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(idOrName));
	}

	public void waitForFrameAndSwitchToItByIndex(int timeOut, int frameIndex) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}

	public void waitForFrameAndSwitchToItByFrameElement(int timeOut, WebElement frameElement) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}

	public void waitForFrameAndSwitchToItByFrameLoctor(int timeOut, By frameLocator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}

	/**
	 * Fluent Wait
	 * 
	 * @param locator
	 * @param timeOut
	 * @param pollingTime
	 * @return
	 */

	public WebElement waitForElementVisibleWithFluentWait(By locator, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class)
				.ignoring(ElementNotInteractableException.class).pollingEvery(Duration.ofSeconds(pollingTime))
				.withMessage("------time out is done...element is not found.....");

		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

	}

	public WebElement waitForElementPresenceWithFluentWait(By locator, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class)
				.ignoring(ElementNotInteractableException.class).pollingEvery(Duration.ofSeconds(pollingTime))
				.withMessage("------time out is done...element is not found.....");

		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));

	}

	// **************** custom methods for wait *************************//

	public WebElement retryingElement(By locator, int timeOut) {

		WebElement element = null;
		int attempts = 0;

		while (attempts < timeOut) {
			try {
				element = getElement(locator);
				System.out.println("element is found ..." + locator + "in attempt : " + attempts);
				break;
			} catch (NoSuchElementException e) {
				System.out.println("element is found ..." + locator + "in attempt : " + attempts);

				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			attempts++;
		}
		if (element == null) {
			System.out.println("Element is not found ... tried for " + timeOut + " secs"
					+ "with the interval of 500 milliseconds");

		}
		return element;

	}

	public WebElement retryingElement(By locator, int timeOut, int pollingTime) {

		WebElement element = null;
		int attempts = 0;

		while (attempts < timeOut) {
			try {
				element = getElement(locator);
				System.out.println("element is found ..." + locator + "in attempt : " + attempts);
				break;
			} catch (NoSuchElementException e) {
				System.out.println("element is found ..." + locator + "in attempt : " + attempts);

				try {
					Thread.sleep(pollingTime);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			attempts++;
		}
		if (element == null) {
			System.out.println("Element is not found ... tried for " + timeOut + " secs"
					+ "with the interval of 500 milliseconds");

		}
		return element;

	}

	// ******************* Page Load Util ***********************//

	public Boolean isPageLoaded() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		String flag = wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == \'complete\'"))
				.toString(); // "true"
		return Boolean.parseBoolean(flag);// true
	}

	public void waitForPageLoad(int timeOut) {

		long endTime = System.currentTimeMillis() + timeOut;
		while (System.currentTimeMillis() < endTime) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String pageState = js.executeScript("return document.readyState").toString();
			if (pageState.equals("complete")) {
				System.out.println("PAGE DOM is fully loaded now....");
				break;
			} else {
				System.out.println("PAGE IS not loaded...." + pageState);
			}
		}
	}

}
