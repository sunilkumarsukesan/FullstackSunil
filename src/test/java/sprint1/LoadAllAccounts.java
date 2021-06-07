package sprint1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoadAllAccounts {
	
	public static void main(String[] args) throws InterruptedException {
		int currentCount =0,previousCount=0;
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
		driver.findElement(By.id("password")).sendKeys("Bootcamp@123");
		driver.findElement(By.id("Login")).click();

		// Click on toggle menu button from the left corner
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='slds-icon-waffle']")));
		driver.findElement(By.xpath("//div[@class='slds-icon-waffle']")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='View All']")));
		driver.findElement(By.xpath("//button[text()='View All']")).click();

		// Click view All and click Sales from App Launcher
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-name='Sales']")));
		driver.findElement(By.xpath("//div[@data-name='Sales']")).click();

		// Click on Accounts tab visible or select from more.
		js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//a[@title='Accounts']")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Updated')]")));
		
		currentCount =  driver.findElements(By.xpath("(//table[contains(@class,'uiVirtualDataTable')]//tr)")).size();	
		
		//Scrolling the table rows until we reach the last row
		while (currentCount!=previousCount) {	
			js.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("(//table[contains(@class,'uiVirtualDataTable')]//tr)[" + currentCount+ "]")));
			Thread.sleep(250);
			previousCount = currentCount;
			currentCount = driver.findElements(By.xpath("(//table[contains(@class,'uiVirtualDataTable')]//tr)")).size();
		}
		
		//getting the account Names
		List<WebElement> accountNamesWebElements = new ArrayList<WebElement>();
		accountNamesWebElements = driver.findElements(By.xpath("//table[contains(@class,'uiVirtualDataTable')]//tr//a[@rel='noreferrer']"));
		List<String> accountNames = new ArrayList<String>();
		for (WebElement account : accountNamesWebElements) {
			accountNames.add(account.getText());
		}
		System.out.println(accountNames);
		System.out.println(accountNames.size());
	}
}
