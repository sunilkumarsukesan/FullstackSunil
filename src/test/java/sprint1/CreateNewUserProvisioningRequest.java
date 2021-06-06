package sprint1;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateNewUserProvisioningRequest {

	public static void main(String[] args) throws InterruptedException {
		String name = "Sunil";
		boolean IsStep3OptionSelected = false;
		System.setProperty("webdriver.chrome.driver", ".//drivers//chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		WebDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		ArrayList<String> list = new ArrayList<String>();

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
		
		// Click on User Provisioning Request
		executor.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//p[text()='User Provisioning Requests']")));
		driver.findElement(By.xpath("//p[text()='User Provisioning Requests']")).click();
		
		// Click on the open in SalesForce Classic
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Open in Salesforce Classic.']")));
		driver.findElement(By.xpath("//a[text()='Open in Salesforce Classic.']")).click();
		
		Set<String> windowsHandles = driver.getWindowHandles();
		list.addAll(windowsHandles);
		
		for (int window = 0; window < list.size();window++)
		{
			driver.switchTo().window(list.get(window));
			if (driver.getTitle().contains("User Provisioning Requests:"))
			{
				break;
			}
		}
		
		// Click on Create New View
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Create New View']")));
		driver.findElement(By.xpath("//a[text()='Create New View']")).click();
		
		
		// Enter View Name as Snorky[Your name]
		driver.findElement(By.id("fname")).sendKeys("Snorky["+name+"]");
	

		// Enter View Unique Name as Snorky_26[yourname_anynumber]
		driver.findElement(By.id("devname")).sendKeys("_Snorky_26");
		
		// Click on My User Provisioning Requests Under Step two
		driver.findElement(By.id("fscope1")).click();
		
		// Under Field Select First DropDown as Name
		Select firstFieldoptions = new Select(driver.findElement(By.id("fcol1")));
		firstFieldoptions.selectByValue("Name");
		
		//Under Field Get the List of values available in Second Dropdown
		Select secondFieldoptions = new Select(driver.findElement(By.id("fcol2")));
		List<WebElement> allOptions = secondFieldoptions.getOptions();
		for (WebElement option : allOptions)
		{
			System.out.println(option.getText());
		}
		
		// Get the size of DropDown
		int sizeOfSecondDropdown = allOptions.size();
		System.out.println(sizeOfSecondDropdown);

		
		// Under Field Select First DropDown as Created Date
		firstFieldoptions.selectByVisibleText("Created Date");
		
		// Under Step 3 Get the List of Available Fields
		Select step3options = new Select(driver.findElement(By.id("colselector_select_0")));
		List<WebElement> allstep3options = step3options.getOptions();
		for (WebElement optionStep3 : allstep3options)
		{
			System.out.println(optionStep3.getText());
		}
		
		
		// Under Step 3 Get the List of Available Fields
		Select step3Selectedoptions = new Select(driver.findElement(By.id("colselector_select_1")));
		List<WebElement> allstep3Selectedoptions = step3Selectedoptions.getOptions();
		for (WebElement optionSelectedinStep3 : allstep3Selectedoptions)
		{
			System.out.println(optionSelectedinStep3.getText());
		}
		
		// Select an Option From Available Field  and Click Add Option 
		step3options.selectByVisibleText("App Name");
		
		driver.findElement(By.xpath("//a[@id='colselector_select_0_right']/img")).click();
		
		step3Selectedoptions = new Select(driver.findElement(By.id("colselector_select_1")));
		allstep3Selectedoptions = step3Selectedoptions.getOptions();
		for (WebElement optionSelectedinStep3 : allstep3Selectedoptions)
		{
			System.out.println(optionSelectedinStep3.getText());
			if (optionSelectedinStep3.getText().equals("App Name"))
				IsStep3OptionSelected = true;
		}
		
		// Verify whether Field is added to Selected Fields 
		assertTrue(IsStep3OptionSelected);
		
		// Under Step 4  Click on Visible to All Users 
		executor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.name("save")));
		driver.findElement(By.id("fsharefshareall")).click();
		
		// Click on Save
		driver.findElement(By.name("save")).click();
		
		
		
		windowsHandles.clear();
		list.clear();
		windowsHandles = driver.getWindowHandles();
		list.addAll(windowsHandles);
		
		for (int window = 0; window < list.size();window++)
		{
			driver.switchTo().window(list.get(window));
			if (driver.getTitle().contains("Snorky["+name+"]"))
			{
				break;
			}
		}
		
		// Verify New User is Created
		assertTrue(driver.findElement(By.xpath("//h1[text()='Snorky[" + name +"]']")).isDisplayed());
		
		//Get the Title of the Page
		System.out.println(driver.getTitle());
		
		// Close Other Browsers Than Current Browser		
		for (int window = 0; window < list.size();window++)
		{
			driver.switchTo().window(list.get(window));
			if (!driver.getTitle().contains("Snorky["+name+"]"))
			{
				driver.close();
			}
		}
		
	}

}
