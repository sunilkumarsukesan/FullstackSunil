package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SeleniumMethods extends BaseClass{
	
	
	public void loadAddress(String URL) {
		driver.get(URL);
	}
	
	public WebElement locateElement(String locateBy, String locator) {
		switch (locateBy.toLowerCase()) {
		case "xpath":
			return driver.findElement(By.xpath(locator));
		case "id":
			return driver.findElement(By.id(locator));
		case "name":
			return driver.findElement(By.name(locator));
		case "linktext":
			return driver.findElement(By.linkText(locator));
		case "partiallinktext":
			return driver.findElement(By.partialLinkText(locator));
		case "classname":
			return driver.findElement(By.className(locator));
		case "tagname":
			return driver.findElement(By.tagName(locator));
		case "css":
			return driver.findElement(By.cssSelector(locator));
		default:
			throw new RuntimeException("Invalid locateby provided, here is the list of locateby xpath\nid\nname\nlinktext\npartiallinktext\nclassname\ntagname\ncss");
		}
		
	}
		
	public void click(String locateBy, String locator) {
		locateElement(locateBy,locator).click();
	}
	
	public void clickUsingJavaScriptExecutor(String locateBy, String locator) {
		javaScriptExecutor.executeScript("arguments[0].click();", locateElement(locateBy,locator));
	}
	
	public void type(String locateBy, String locator,String text) {
		locateElement(locateBy,locator).sendKeys(text);
	}
	
	public void clearTheTextBox(String locateBy, String locator) {
		locateElement(locateBy,locator).clear();
	}
	
	public void keyboardActions(String locateBy, String locator,Keys key) {
		locateElement(locateBy,locator).sendKeys(key);
	}
	
	public void scrollPageToFind(String locateBy, String locator) {
		javaScriptExecutor.executeScript("arguments[0].scrollIntoView(true);", locateElement(locateBy,locator));
	}
	
	public void waitForTheElementToBeVisible(String locateBy, String locator) {
		wait.until(ExpectedConditions.visibilityOf(locateElement(locateBy,locator)));
	}
	
	public void waitForTheElementToBeClickable(String locateBy, String locator) {
		wait.until(ExpectedConditions.elementToBeClickable(locateElement(locateBy,locator)));
	}
	
	public String getText(String locateBy, String locator) {
		return locateElement(locateBy,locator).getText();
	}
	
	public boolean isElementDisplayed(String locateBy, String locator) {
		return locateElement(locateBy,locator).isDisplayed();
	}

}
