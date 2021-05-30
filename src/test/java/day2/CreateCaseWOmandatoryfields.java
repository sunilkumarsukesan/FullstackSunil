package day2;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateCaseWOmandatoryfields {

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", ".//drivers/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		WebDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, 15);
		driver.manage().window().maximize();
		JavascriptExecutor js = (JavascriptExecutor) driver;

		// Launch the app and login with the credentials
		driver.get("https://login.salesforce.com/");
		driver.findElement(By.name("username")).sendKeys("mercury.bootcamp@testleaf.com");
		driver.findElement(By.id("password")).sendKeys("Bootcamp$123");
		driver.findElement(By.id("Login")).click();

		// Click on toggle menu button from the left corner
		driver.findElement(By.xpath("//div[@class='slds-icon-waffle']")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='View All']")));
		driver.findElement(By.xpath("//button[text()='View All']")).click();

		// Click view All and click Sales from App Launcher
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-name='Sales']")));
		driver.findElement(By.xpath("//div[@data-name='Sales']")).click();

		// Click on Cases tab visible or select from more.
		js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//a[@title='Cases']")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Updated')]")));
		
		
		//Click on New button
		driver.findElement(By.xpath("//div[text()='New']")).click();
		
		//Choose Contact Name as 'Naveen Elumalai'
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@title='Search Contacts']")));
		driver.findElement(By.xpath("//input[contains(@title,'Search Contacts')]")).click();
		driver.findElement(By.xpath("//input[@title='Search Contacts']")).sendKeys("Naveen Elumalai");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@title='Contact']")));
		driver.findElement(By.xpath("//img[@title='Contact']")).click();
		
		//Select status as None
		driver.findElement(By.xpath("//label[text()='Status']/following-sibling::div")).click();
		driver.findElement(By.xpath("//span[contains(text(),'None')]")).click();
		
		//Enter Subject as 'Testing' and description as 'Automation testing'
		js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//span[text()='Internal Comments']")));
		driver.findElement(By.xpath("//span[text()='Subject']/parent::label/following-sibling::input")).click();
		driver.findElement(By.xpath("//span[text()='Subject']/parent::label/following-sibling::input")).sendKeys("Testing");
		driver.findElement(By.xpath("//span[text()='Description']/parent::label/following-sibling::textarea")).click();
		driver.findElement(By.xpath("//span[text()='Description']/parent::label/following-sibling::textarea")).sendKeys("Automation Testing");
		
		// Click on Save and Verify Status as Working
		driver.findElement(By.xpath("//button[contains(@class,'forceActionButton')]/span[text()='Save']")).click();

		// Verifying the confirmation message
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='errorsList']")));
		Assert.assertEquals("These required fields must be completed: Case Origin, Status", driver.findElement(By.xpath("//ul[@class='errorsList']/li")).getText());
		driver.quit();
		
	}

}
