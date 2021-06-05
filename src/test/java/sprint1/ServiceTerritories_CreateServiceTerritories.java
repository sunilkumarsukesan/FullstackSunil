package sprint1;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class ServiceTerritories_CreateServiceTerritories {

	public static void main(String[] args) throws InterruptedException {
		String name = "Sunil";
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
		
		//Click on Service Territories
		executor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//p[text()='Service Territories']")));
		driver.findElement(By.xpath("//p[text()='Service Territories']")).click();
		
		//Click on New 
		driver.findElement(By.xpath("//div[@title='New']/parent::a")).click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//input[contains(@placeholder,'Search Service Territories..')]"))));
		
		//Enter Your Name in the Name field
		driver.findElement(By.xpath("//span[text()='Name']/parent::label/following-sibling::input")).click();
		driver.findElement(By.xpath("//span[text()='Name']/parent::label/following-sibling::input")).sendKeys(name);
		
		//Click on Operating Hours and Choose the First option
		driver.findElement(By.xpath("//input[@title='Search Operating Hours']")).click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//img[@alt='Operating Hours']"))));
		driver.findElement(By.xpath("//img[@alt='Operating Hours']")).click();
		
		//Check Active Field
		driver.findElement(By.xpath("//span[text()='Active']/parent::label/following-sibling::input")).click();
		
		//Enter the City you're residing in City Field
		executor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//span[text()='Description']")));
		driver.findElement(By.xpath("//input[contains(@class,'city')]")).click();
		driver.findElement(By.xpath("//input[contains(@class,'city')]")).sendKeys("Chennai");
		
		//Enter the State your residing in State Field
		driver.findElement(By.xpath("//input[contains(@class,'state')]")).click();
		driver.findElement(By.xpath("//input[contains(@class,'state')]")).sendKeys("Tamil Nadu");
		
		//Enter your current Postal Zip Code 
		driver.findElement(By.xpath("//input[contains(@class,'postalCode')]")).click();
		driver.findElement(By.xpath("//input[contains(@class,'postalCode')]")).sendKeys("600072");
		
		//Enter the Country you're residing in Country Field
		driver.findElement(By.xpath("//input[contains(@class,'country')]")).click();
		driver.findElement(By.xpath("//input[contains(@class,'country')]")).sendKeys("India");
		
		//Click on Save
		driver.findElement(By.xpath("//button[contains(@class,'forceActionButton')]/span[text()='Save']")).click();
		
		//Verify Service Territory is created Successfully
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[contains(@class,'toastMessage')]"))));	
		Assert.assertEquals("Service Territory \"" + name + "\" was created.", driver.findElement(By.xpath("//span[contains(@class,'toastMessage')]")).getText());
		driver.close();
	}

}
