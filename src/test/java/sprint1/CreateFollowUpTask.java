package sprint1;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateFollowUpTask {

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", ".//drivers//chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		WebDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		
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
		
		// Click on Content tab 
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[text()='Content']")));
		driver.findElement(By.xpath("//p[text()='Content']")).click();
		
		// Click View All from Today's Task
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@aria-label='View All Tasks']/span")));
		driver.findElement(By.xpath("//a[@aria-label='View All Tasks']/span")).click();
		
		// Click the Display as dropdown under Recently Viewed and select Table
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='Select List View']")));
		driver.findElement(By.xpath("//a[@title='Select List View']")).click();
		driver.findElement(By.xpath("//span[text()='Recently Viewed' and @class =' virtualAutocompleteOptionText']")).click();
		
		// Click the dropdown of first result and click Create Follow-Up Task
		List<WebElement> findElements = driver.findElements(By.xpath("//span[@class='slds-truncate test-splitViewCardData']"));
		for (int i =0;i<findElements.size();i++)
		{
			if (findElements.get(i).getText().equals(""))
			{
				driver.findElement(By.xpath("(//span[contains(@class,'fade test-splitViewCardData')])["+ i+1+"]")).click();
				break;
			}
		}
				
		driver.findElement(By.xpath("//div[text()='Create Follow-Up Task']")).click();
		
		// Select a Account name in Assigned to field and 
		if (driver.findElement(By.xpath("//span[text()='Press Delete to Remove']")).isDisplayed())
		{
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='deleteIcon']")));
			driver.findElement(By.xpath("//span[@class='deleteIcon']")).click();
		}
		driver.findElement(By.xpath("//input[@title='Search People']")).click();
		driver.findElement(By.xpath("//div[contains(@class,'lookup__result')]")).click();
		
		// Select a subject as Call
		driver.findElement(By.xpath("//label[text()='Subject']/parent::lightning-grouped-combobox//input")).click();
		driver.findElement(By.xpath("//span[@title='Call']")).click();
		
		// Set Priority as High 
		driver.findElement(By.xpath("//span[text()='Priority']/parent::span/following-sibling::div//a")).click();
		driver.findElement(By.xpath("//a[@title='High']")).click();
		
		// Status as In Progress
		driver.findElement(By.xpath("//span[text()='Status']/parent::span/following-sibling::div//a")).click();
		driver.findElement(By.xpath("//a[@title='In Progress']")).click();
		
		// Click on the image of Name field, click on Contacts and select Contact
		driver.findElement(By.xpath("//img[@title='Contacts']")).click();
		driver.findElement(By.xpath("//a[@title='Contacts']")).click();
		driver.findElement(By.xpath("//input[@title='Search Contacts']")).click();
		driver.findElement(By.xpath("(//div[contains(@class,'result-text')])[2]")).click();
		
		// Click on the image of Related To field, click on Product and Select Product
		driver.findElement(By.xpath("//img[@title='Accounts']")).click();
		driver.findElement(By.xpath("//a[@title='Accounts']")).click();
		driver.findElement(By.xpath("//input[@title='Search Accounts']")).click();
		driver.findElement(By.xpath("//div[contains(@class,'mruIcon')]//img[@title='Account']")).click();
		
		
		// Click Save
		driver.findElement(By.xpath("//span[text()='Save']")).click();
		
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[contains(@class,'toastMessage')]"))));
		System.out.println(driver.findElement(By.xpath("//span[contains(@class,'toastMessage')]")).getText());
		assertEquals("Task Call was created.", driver.findElement(By.xpath("//span[contains(@class,'toastMessage')]")).getText());
		
		driver.close();
	}

}
