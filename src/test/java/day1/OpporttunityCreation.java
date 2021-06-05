package day1;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OpporttunityCreation {

	public static void main(String[] args) {

		System.setProperty("webdriver.chrome.driver", ".//drivers/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");

		WebDriver driver = new ChromeDriver(options);

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, 15);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		driver.manage().window().maximize();

		driver.get("https://login.salesforce.com/");
		driver.findElement(By.name("username")).sendKeys("mercury.bootcamp@testleaf.com");
		driver.findElement(By.id("password")).sendKeys("Bootcamp@123");
		driver.findElement(By.id("Login")).click();

		driver.findElement(By.xpath("//div[@class='slds-icon-waffle']")).click();
		wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//lightning-button[@class='slds-button slds-p-horizontal--small']")));

		driver.findElement(By.xpath("//lightning-button[@class='slds-button slds-p-horizontal--small']")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-name='Sales']")));
		driver.findElement(By.xpath("//div[@data-name='Sales']")).click();

		driver.findElement(By.linkText("View All Key Deals")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Updated')]")));

		driver.findElement(
				By.xpath("(//lightning-icon[@class='slds-icon-utility-chevrondown slds-icon_container'])[1]")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='All Opportunities']")));
		js.executeScript(
				"document.evaluate(\"//span[text()='My Opportunities']\",document,null,XPathResult.FIRST_ORDERED_NODE_TYPE,null).singleNodeValue.click()");

		driver.findElement(By.linkText("New")).click();
		wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//label[text()='Opportunity Name']/following-sibling::div[1]")));

		driver.findElement(By.xpath("//label[text()='Opportunity Name']/following-sibling::div[1]")).click();
		driver.findElement(By.xpath("//label[text()='Opportunity Name']/following-sibling::div[1]"))
				.sendKeys("SRM Steels");

		driver.findElement(By.xpath("//label[text()='Type']/following-sibling::div[1]")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@title='New Customer']")));
		driver.findElement(By.xpath("//span[@title='New Customer']")).click();

		driver.findElement(By.xpath("//label[text()='Lead Source']/following-sibling::div[1]")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//lightning-base-combobox-item[@data-value='Partner Referral']")));
		driver.findElement(By.xpath("//lightning-base-combobox-item[@data-value='Partner Referral']")).click();

		driver.findElement(By.xpath("//label[text()='Amount']/following-sibling::div[1]")).click();
		driver.findElement(By.xpath("//label[text()='Amount']/following-sibling::div[1]")).sendKeys("75000");

		driver.findElement(By.xpath("//label[text()='Close Date']/following-sibling::div[1]")).click();
		driver.findElement(By.xpath("//label[text()='Close Date']/following-sibling::div[1]")).sendKeys("06/20/2021");

		driver.findElement(By.xpath("//label[text()='Stage']/following-sibling::div[1]")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@title='Needs Analysis']")));
		driver.findElement(By.xpath("//span[@title='Needs Analysis']")).click();

		driver.findElement(By.xpath("//label[text()='Primary Campaign Source']/following-sibling::div[1]")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h3[@title='Recent Campaigns']/ancestor::li/following-sibling::li")));
		driver.findElement(By.xpath("//h3[@title='Recent Campaigns']/ancestor::li/following-sibling::li")).click();

		driver.findElement(By.xpath("//runtime_platform_actions-action-renderer[@title='Save']")).click();

		// verifying final message
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Opportunity']")));
		Assert.assertTrue("SRM Steels", driver.findElement(By.xpath("//div[@title='\"SRM Steels\"']")).isDisplayed());
		Assert.assertTrue(" was created.",driver.findElement(By.xpath("//span[text()=\" was created.\"]")).isDisplayed());

		driver.quit();

	}

}
