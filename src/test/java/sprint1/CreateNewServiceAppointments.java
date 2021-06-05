package sprint1;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateNewServiceAppointments {

	public static void main(String[] args) throws InterruptedException {
		String name = RandomStringUtils.randomAlphabetic(5);
		String currenTime,min,hour = null,AppointmentNumber;
		System.setProperty("webdriver.chrome.driver", ".//drivers//chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		WebDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		JavascriptExecutor executor = (JavascriptExecutor) driver;

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

		// Click on Service Appointments
		executor.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//p[text()='Service Appointments']")));
		driver.findElement(By.xpath("//p[text()='Service Appointments']")).click();
		
		// Click on New 
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='New']")));
		driver.findElement(By.xpath("//a[@title='New']")).click();
		
		//Enter Description as Creating Service Appointments
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[text()='General Information']"))));
		driver.findElement(By.xpath("//span[text()='Description']/parent::label/following-sibling::textarea")).click();
		driver.findElement(By.xpath("//span[text()='Description']/parent::label/following-sibling::textarea")).sendKeys("Creating Service Appointments");
		
		//Click on Search Accounts under Parent Record
		executor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//span[text()='Parent Record']")));
		driver.findElement(By.xpath("//input[@title='Search Accounts']")).click();
		
		//Click on New  Account 
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@title='New Account']")));
		driver.findElement(By.xpath("//span[@title='New Account']")).click();
		
		//Enter Your name in the Account Name
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Account Information']")));
		driver.findElement(By.xpath("//span[text()='Account Name']/parent::label/following-sibling::input")).click();
		driver.findElement(By.xpath("//span[text()='Account Name']/parent::label/following-sibling::input")).sendKeys(name);
		
		//Click On Save 
		driver.findElement(By.xpath("//button[@title='Save' and contains(@class,'default')]")).click();
		
		
		//Verify Parent Record
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Close']")));
		driver.findElement(By.xpath("//button[@title='Close']")).click();
		driver.findElement(By.xpath("//span[text()='"+ name +"']")).isDisplayed();
		
		//Select Today date in Earliest Start Permitted		
		executor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//span[text()='Earliest Start Permitted']")));
		driver.findElement(By.xpath("//span[text()='Earliest Start Permitted']/ancestor::fieldset//a[@class='datePicker-openIcon display']")).click();
		driver.findElement(By.xpath("//span[contains(@class,'todayDate')]")).click();
		
		//Select nearest Current Time
		driver.findElement(By.xpath("//span[text()='Earliest Start Permitted']/ancestor::fieldset//a[@class='timePicker-openIcon display']")).click();
		
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("hh:mm a");  
		LocalDateTime now = LocalDateTime.now();  
		currenTime = pattern.format(now);
		min = currenTime.substring(3, 5);
				
		if (currenTime.substring(0, 1).equals("0")) {
		hour = currenTime.substring(0, 2).replace("0", "");
		}
		
		if  (Integer.parseInt(min) > 30)
		{
			min = "00";
			hour = Integer.toString((Integer.parseInt(hour) + 1));
		}
		else
			min= "30";
		
		driver.findElement(By.xpath("//li[text()='" + hour + ":" + min+ " "+ currenTime.substring(6, 8) +"']")).click();
		
		// Select 5+ days from Today's date as Due Date
		driver.findElement(By.xpath("//span[text()='Due Date']/ancestor::fieldset//a[@class='datePicker-openIcon display']")).click();
		pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
		LocalDateTime currenDay= LocalDateTime.now();  
		LocalDateTime currenDayPluse5 = currenDay.plusDays(5);
		if (currenDayPluse5.getMonth()!= currenDay.getMonth()) {
			driver.findElement(By.xpath("//a[contains(@class,'nextMonth')]")).click();
		}
		driver.findElement(By.xpath("//td[@data-datevalue='" + pattern.format(currenDayPluse5) + "']")).click();
		
		//click on save 
		driver.findElement(By.xpath("//button[contains(@class,'neutral uiButton--brand')]")).click();
		
		//Get the Appointment Number 
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[contains(@class,'toastMessage')]"))));			
		AppointmentNumber = driver.findElement(By.xpath("//span[contains(@class,'toastMessage')]")).getText().replace("\" was created.", "").replace("Service Appointment \"", "");
		driver.findElement(By.xpath("//div[@title='" + AppointmentNumber +"']")).isDisplayed();	
		
		// Click on the App Launcher Icon left to Setup
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='slds-icon-waffle']")));
		driver.findElement(By.xpath("//div[@class='slds-icon-waffle']")).click();

		// Click on View All
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='View All']")));
		driver.findElement(By.xpath("//button[text()='View All']")).click();

		// Click on Service Appointments
		executor.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//p[text()='Service Appointments']")));
		driver.findElement(By.xpath("//p[text()='Service Appointments']")).click();
	
		//Verify the Appointment Numbe
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Search this list...']")));
		driver.findElement(By.xpath("//input[@placeholder='Search this list...']")).click();	
		driver.findElement(By.xpath("//input[@placeholder='Search this list...']")).sendKeys(AppointmentNumber);	
		driver.findElement(By.xpath("//input[@placeholder='Search this list...']")).sendKeys(Keys.ENTER);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='"+ AppointmentNumber + "' and @rel='noreferrer']")));
		driver.findElement(By.xpath("//a[@title='"+ AppointmentNumber + "' and @rel='noreferrer']")).isDisplayed();
		
		driver.close();
	}

}
