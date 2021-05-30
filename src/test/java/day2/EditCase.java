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

public class EditCase {

	public static void main(String[] args) throws InterruptedException {
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

		// Getting the ticketnumber and status
		TicketNumber = driver.findElement(By.xpath("//table[@role='grid']//tr[1]//span/a")).getText();

		// Click on the Dropdown icon and select Edit from the case you created by
		// referring "case owner alias"
		driver.findElement(By.xpath("//*[name()='svg' and @data-key='down']/parent::span")).click();
		driver.findElement(By.xpath("//a[@title='Edit']")).click();

		// Update Status as Working
		wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.xpath("//h2[contains(text(),'" + TicketNumber + "')]"))));
		wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='Status']/following-sibling::div")));
		Thread.sleep(1000);
		driver.findElement(By.xpath("//label[text()='Status']/following-sibling::div")).click();
		wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//lightning-base-combobox-item[@data-value='Working']")));
		driver.findElement(By.xpath("//lightning-base-combobox-item[@data-value='Working']")).click();

		// Update Priority to low
		driver.findElement(By.xpath("//span[text()='Priority']/parent::span/following-sibling::div")).click();
		driver.findElement(By.xpath("//a[text()='Low']")).click();

		// Update Case Origin as Phone
		driver.findElement(By.xpath("//span[text()='Case Origin']/parent::span/following-sibling::div")).click();
		driver.findElement(By.xpath("//a[text()='Phone']")).click();

		// Update SLA violation to No
		js.executeScript("arguments[0].scrollIntoView(true);",
				driver.findElement(By.xpath("//span[text()='SLA Violation']")));
		driver.findElement(By.xpath("//span[text()='SLA Violation']/parent::span/following-sibling::div")).click();
		driver.findElement(By.xpath("//a[text()='No']")).click();

		// Click on Save and Verify Status as Working
		driver.findElement(By.xpath("//button[contains(@class,'forceActionButton')]/span[text()='Save']")).click();

		// Verifying the confirmation message
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Case']")));
		Assert.assertTrue("Case", driver.findElement(By.xpath("//span[text()='Case']")).isDisplayed());
		System.out.println("Newly created task id is, " + TicketNumber);
		Assert.assertTrue("Case ID", driver.findElement(By.xpath("//span[contains(text(),'" + TicketNumber + "')]")).isDisplayed());
		Assert.assertTrue("was created", driver.findElement(By.xpath("//span[text()=' was saved.']")).isDisplayed());
		driver.quit();

	}

}
