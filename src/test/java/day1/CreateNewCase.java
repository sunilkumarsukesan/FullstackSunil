package day1;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateNewCase {

	public static void main(String[] args) throws InterruptedException {
		String Taskid;
		System.setProperty("webdriver.chrome.driver", ".//drivers/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		WebDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, 15);
		driver.manage().window().maximize();
		JavascriptExecutor js = (JavascriptExecutor) driver;

		//Launch the app and login with the credentials
		driver.get("https://login.salesforce.com/");
		driver.findElement(By.name("username")).sendKeys("mercury.bootcamp@testleaf.com");
		driver.findElement(By.id("password")).sendKeys("Bootcamp@123");
		driver.findElement(By.id("Login")).click();
		
		//Click on Global Actions SVG icon
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@data-aura-class='oneGlobalCreate']")));
		WebElement button = driver.findElement(By.xpath("//*[name()='svg' and @data-key='add']/ancestor::a"));
		js.executeScript("arguments[0].click();", button);

		// Click on New Case
		driver.findElement(By.linkText("New Case")).click();

		// Choose Contact Name from the dropdown
		driver.findElement(By.xpath("//input[@title='Search Contacts']")).click();
		driver.findElement(By.xpath("//li[contains(@class,'default uiAutocompleteOption')]")).click();

		// Select status as Escalated
		driver.findElement(By.linkText("New")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Escalated')]")).click();

		// Enter Subject as 'Testing' and description as 'Dummy'
		driver.findElement(By.xpath("//span[text()='Subject']/following::input")).click();
		driver.findElement(By.xpath("//span[text()='Subject']/following::input")).sendKeys("Testing");
		driver.findElement(By.xpath("//span[text()='Description']/following::textarea")).click();
		driver.findElement(By.xpath("//span[text()='Description']/following::textarea")).sendKeys("Dummy");

		// Click 'Save'
		driver.findElement(By.xpath("//button[contains(@class,'forceActionButton')]/span[text()='Save']")).click();

		// Verifying the confirmation message
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Case']")));
		Assert.assertTrue("Case", driver.findElement(By.xpath("//span[text()='Case']")).isDisplayed());
		Taskid = driver.findElement(By.xpath("//span[text()='Case']/a")).getAttribute("title");
		System.out.println("Newly created task id is, " + Taskid);
		Assert.assertTrue("Case ID", driver.findElement(By.xpath("//div[text()='" + Taskid + "']")).isDisplayed());
		Assert.assertTrue("was created", driver.findElement(By.xpath("//span[text()=' was created.']")).isDisplayed());
		driver.quit();

	}

}
