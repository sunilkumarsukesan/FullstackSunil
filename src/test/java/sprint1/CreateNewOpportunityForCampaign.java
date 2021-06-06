package sprint1;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.Tools;

public class CreateNewOpportunityForCampaign {

	public static void main(String[] args) throws Exception {
		String name = "Opportunity from "+  RandomStringUtils.randomAlphabetic(5);
		System.setProperty("webdriver.chrome.driver", ".//drivers//chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		WebDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		Tools tools = new Tools(driver);

		// Launch the app, Click Login and Login with the credentials
		driver.get("https://login.salesforce.com/");
		driver.findElement(By.id("username")).sendKeys("mercury.bootcamp@testleaf.com");
		driver.findElement(By.id("password")).sendKeys("Bootcamp@123");
		driver.findElement(By.id("Login")).click();

		// Click on the App Launcher Icon left to Setup
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='slds-icon-waffle']")));
		driver.findElement(By.xpath("//div[@class='slds-icon-waffle']")).click();
		
		// Click on View All
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='View All']")));
		driver.findElement(By.xpath("//button[text()='View All']")).click();
		
		// Click Sales from App Launcher
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-name='Sales']")));
		driver.findElement(By.xpath("//div[@data-name='Sales']")).click();
		
		// Click on  Campaigns tab 
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Campaigns']")));
		executor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[text()='Campaigns']")));
				
		// Click Bootcamp link
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Bootcamp']")));
		driver.findElement(By.xpath("//a[text()='Bootcamp']")).click();
		
		// Enter Opportunity name as 'Opportunity from Your Name'
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Opportunities']/ancestor::article//div[text()='New']")));
		driver.findElement(By.xpath("//span[text()='Opportunities']/ancestor::article//div[text()='New']")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='Opportunity Name']")));
		driver.findElement(By.xpath("//input[@name='Name']")).click();
		driver.findElement(By.xpath("//input[@name='Name']")).sendKeys(name);
		
		// Choose close date as Tomorrow
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='CloseDate']")));
		driver.findElement(By.xpath("//input[@name='CloseDate']")).click();
		tools.setDate(1,"data-value");
		
		// Select 'Stage' as Need Analysis
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='Stage']/parent::lightning-combobox//input")));
		driver.findElement(By.xpath("//label[text()='Stage']/parent::lightning-combobox//input")).click();
		driver.findElement(By.xpath("//span[@title='Needs Analysis']")).click();
		
		// click Save
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Save']")));
		driver.findElement(By.xpath("//button[text()='Save']")).click();
		
		// Verify new Oppurtunity in Campaign
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[contains(@class,'toastMessage')]"))));
		assertEquals("Opportunity \""+ name + "\" was created.", driver.findElement(By.xpath("//span[contains(@class,'toastMessage')]")).getText());
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Opportunities']/parent::span")));
		driver.findElement(By.xpath("//span[text()='Opportunities']/parent::span")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='" + name + "']")));
		assertTrue(driver.findElement(By.xpath("//a[@title='" + name + "']")).isDisplayed());
		

		// Click on Opportunities Tab
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Opportunities']")));
		executor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[text()='Opportunities']")));
		
		// Verify the newly created Opportunity (Oppotunity from your name) "
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='" + name+"']")));
		assertTrue(driver.findElement(By.xpath("//a[text()='" + name+"']")).isDisplayed());
		
	}

}
