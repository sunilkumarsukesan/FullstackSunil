package sprint1;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateServiceResources {

	public static void main(String[] args) throws InterruptedException {
		String name = RandomStringUtils.randomAlphabetic(5);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		System.setProperty("webdriver.chrome.driver", ".//drivers//chromedriver.exe");
		WebDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		WebDriverWait wait = new WebDriverWait(driver, 15);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		
		//Launch the app, Click Login and Login with the credentials
		driver.get("https://login.salesforce.com/");
		driver.findElement(By.id("username")).sendKeys("mercury.bootcamp@testleaf.com");
		driver.findElement(By.id("password")).sendKeys("Bootcamp@123");
		driver.findElement(By.id("Login")).click();
		
		//Click on the App Launcher Icon left to Setup
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='slds-icon-waffle']")));
		driver.findElement(By.xpath("//div[@class='slds-icon-waffle']")).click();
		
		//Click on View All
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='View All']")));
		driver.findElement(By.xpath("//button[text()='View All']")).click();
		
		//Click on Service Resources
		executor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//p[text()='Service Resources']")));
		driver.findElement(By.xpath("//p[text()='Service Resources']")).click();
		
		//Click on Show one more action Dropdown
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//tbody//*[name()='svg' and @data-key='down']/ancestor::a")));
		driver.findElement(By.xpath("//tbody//*[name()='svg' and @data-key='down']/ancestor::a")).click();
		
		//Click on edit
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='Edit']")));
		driver.findElement(By.xpath("//a[@title='Edit']")).click();
		
		//Change the Name as your name
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@class=' input']")));
		driver.findElement(By.xpath("//input[@class=' input']")).clear();
		driver.findElement(By.xpath("//input[@class=' input']")).click();
		driver.findElement(By.xpath("//input[@class=' input']")).sendKeys(name);
		
		//Click on Save
		driver.findElement(By.xpath("//button[contains(@class,'forceActionButton')]/span[text()='Save']")).click();
		
		//Verify the new name has changed
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[contains(@class,'toastMessage')]"))));	
		Assert.assertEquals("Service Resource \"" + name + "\" was saved.", driver.findElement(By.xpath("//span[contains(@class,'toastMessage')]")).getText());
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//th//a[text()='" + name + "']"))));	
		driver.findElement(By.xpath("//th//a[text()='" + name + "']")).isDisplayed();
		
		driver.close();

	}

}
