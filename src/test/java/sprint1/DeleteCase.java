package sprint1;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DeleteCase {

	public static void main(String[] args){
		String TicketNumber;
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

		//Sorting the date order
		driver.findElement(By.xpath("//span[@title='Date/Time Opened']/parent::a")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@title='Date/Time Opened']/parent::a/following-sibling::span[contains(text(),'Sorted')]")));
		if(driver.findElement(By.xpath("//span[@title='Date/Time Opened']/parent::a/following-sibling::span")).getText().equals("Sorted Ascending")) {
			driver.findElement(By.xpath("//span[@title='Date/Time Opened']/parent::a")).click();
		}
		TicketNumber = driver.findElement(By.xpath("//th[@scope='row']//a")).getAttribute("title");
		
		//Click on the Dropdown icon and select Delete from the case you created by referring to "case owner alias"
		driver.findElement(By.xpath("//div[contains(@class,'forceVirtualActionMarker')]/a")).click();
		driver.findElement(By.xpath("//a[@title='Delete']")).click();
		driver.findElement(By.xpath("//button[@title='Delete']")).click();
		
		//Verify the delete confirmation message
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class,'toastMessage')]")));	
		assertEquals("Case \""+ TicketNumber+"\" was deleted. Undo", driver.findElement(By.xpath("//span[contains(@class,'toastMessage')]")).getText());
		System.out.println(TicketNumber);
		
		//Verify the case with your name is deleted or not
		driver.findElement(By.xpath("//a[@title='Select List View']")).click();
		driver.findElement(By.xpath("//span[text()='Recently Viewed' and contains(@class,'virtual')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@placeholder,'Search this')]")));
		driver.findElement(By.xpath("//input[contains(@placeholder,'Search this')]")).click();
		driver.findElement(By.xpath("//input[contains(@placeholder,'Search this')]")).sendKeys(TicketNumber);
		driver.findElement(By.xpath("//span[@title='Date/Time Opened']/parent::a")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='No items to display.']")));	
		assertTrue(driver.findElement(By.xpath("//p[text()='No items to display.']")).isDisplayed());
		}

}
