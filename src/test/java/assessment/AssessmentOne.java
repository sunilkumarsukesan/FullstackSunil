package assessment;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AssessmentOne {

	public static void main(String[] args) throws InterruptedException {
		int closedAmount, openAmount;
		String name = "Sunil";
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		// options.setExperimentalOption("debuggerAddress", "localhost:53210");
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		JavascriptExecutor executor = (JavascriptExecutor) driver;

		// Capabilities capabilities = driver.getCapabilities();
		// System.out.println(capabilities.asMap());

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

		// 4. Click Service Console from App Launcher
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[text()='Service Console']")));
		driver.findElement(By.xpath("//p[text()='Service Console']")).click();

		// 5. Select Home from the DropDown
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Show Navigation Menu']")));
		driver.findElement(By.xpath("//button[@title='Show Navigation Menu']")).click();
		driver.findElement(By.xpath("//span[text()='Home']")).click();

		// 6. Add CLOSED + OPEN values and result should set as the GOAL (If the result
		// is less than 10000 then set the goal as 10000)
		// wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//span[text()='--']")));
		Thread.sleep(2000);
		closedAmount = Integer.parseInt(
				driver.findElement(By.xpath("//span[text()='Closed' and @class='metricLabel']/following-sibling::span"))
						.getText().replace("$", "").replace(",", ""));
		openAmount = Integer.parseInt(driver
				.findElement(
						By.xpath("//span[contains(text(),'Open') and @class='metricLabel']/following-sibling::span"))
				.getText().replace("$", "").replace(",", ""));
		if (closedAmount + openAmount < 10000) {
			driver.findElement(By.xpath("//button[@title='Edit Goal']")).click();
			driver.findElement(By.xpath("//span[@id='currencyCode']/following-sibling::input")).clear();
			driver.findElement(By.xpath("//span[@id='currencyCode']/following-sibling::input")).sendKeys("10000");
			driver.findElement(By.xpath("//span[text()='Save']")).click();
		}

		// 7. Select Dashboards from DropDown
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Show Navigation Menu']")));
		driver.findElement(By.xpath("//button[@title='Show Navigation Menu']")).click();
		driver.findElement(By.xpath("//span[text()='Dashboards']")).click();

		// 8. Click on New Dashboard
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='New Dashboard']")));
		driver.findElement(By.xpath("//div[text()='New Dashboard']")).click();

		// 9. Enter the Dashboard name as "YourName_Workout"
		Thread.sleep(2000);
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='dashboard']")));
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dashboardNameInput")));
		driver.findElement(By.id("dashboardNameInput")).click();
		driver.findElement(By.id("dashboardNameInput")).sendKeys(name);

		// 10. Enter Description as Testing and Click on Create
		driver.findElement(By.id("dashboardDescriptionInput")).click();
		driver.findElement(By.id("dashboardDescriptionInput")).sendKeys("Testing");
		driver.findElement(By.xpath("//button[text()='Create']")).click();

		// 11. Click on Done
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Done']")));
		Thread.sleep(1000);

		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='dashboard']")));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Done']")));
		driver.findElement(By.xpath("//button[contains(@class,'doneEditing')]")).click();

		// 12. Verify the Dashboard is Created
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[text()='This dashboard has no components.']")));
		assertEquals(driver.findElement(By.xpath("//span[text()='Dashboard']/following-sibling::span")).getText(),
				name);

		// 13. Click on Subscribe
		Thread.sleep(2000);
		executor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//button[text()='Subscribe']")));

		// 14. Select Frequency as "Daily"
		driver.switchTo().parentFrame();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Edit Subscription']")));
		driver.findElement(By.xpath("//span[text()='Daily']")).click();

		// 15. Time as 10:00 AM
		Select dropdown = new Select(driver.findElement(By.id("time")));
		dropdown.selectByVisibleText("10:00 AM");

		// 16. Click on Save
		driver.findElement(By.xpath("//span[text()='Save']")).click();

		// 17. Verify "You started Dashboard Subscription" message displayed or not
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[contains(@class,'toastMessage')]"))));
		assertEquals("You started a dashboard subscription.", driver.findElement(By.xpath("//span[contains(@class,'toastMessage')]")).getText());
		
		// 18. Close the "YourName_Workout" tab
		driver.findElement(By.xpath("//span[text()='" + name + "' and @class='title slds-truncate']/parent::a/following-sibling::div[contains(@class,'close')]/button")).click();
		
		// 19. Click on Private Dashboards
		driver.findElement(By.xpath("//span[text()='Dashboards']")).click();
		driver.findElement(By.xpath("//a[text()='Private Dashboards']")).click();
		
		// 20. Verify the newly created Dashboard available
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@placeholder,'Search private dashboards')]")));
		driver.findElement(By.xpath("//input[contains(@placeholder,'Search private dashboards')]")).click();
		driver.findElement(By.xpath("//input[contains(@placeholder,'Search private dashboards')]")).sendKeys(name);
		Thread.sleep(3000);
		assertEquals(driver.findElement(By.xpath("//span[@class='highlightSearchText']")).getText(), name);
		
		// 21. Click on dropdown for the item
		driver.findElement(By.xpath("(//span[@class='highlightSearchText']/ancestor::tr//lightning-primitive-icon)[2]")).click();
		
		// 22. Select Delete
		driver.findElement(By.xpath("//span[text()='Delete']")).click();
		
		// 23. Confirm the Delete
		driver.findElement(By.xpath("(//span[text()='Delete']) [2]")).click();
		
		// 24. Verify the item is not available under Private Dashboard folder
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[contains(@class,'toastMessage')]"))));
		Thread.sleep(2000);
		assertEquals("Dashboard was deleted.", driver.findElement(By.xpath("//span[contains(@class,'toastMessage')]")).getText());
		
		driver.close();

	}

}
