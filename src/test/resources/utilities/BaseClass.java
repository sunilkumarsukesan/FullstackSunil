package utilities;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
		public WebDriver driver;
		public WebDriverWait wait;	
		public JavascriptExecutor javaScriptExecutor;

@org.testng.annotations.Parameters("BrowserName")		
@BeforeMethod(alwaysRun = true)
public void setUp(String BrowserName) {
	switch (BrowserName) {
	case "Chrome":
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		driver = new ChromeDriver(options);
		break;
	case "Firefox":
		WebDriverManager.firefoxdriver().setup();
		driver = new FirefoxDriver();
	case "Edge":
		WebDriverManager.edgedriver().setup();
		driver = new EdgeDriver();
	default:
		System.err.println("Specify the browser name");
		break;
	}
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	driver.manage().window().maximize();
	wait = new WebDriverWait(driver, 10);
	javaScriptExecutor = (JavascriptExecutor) driver;
}

@AfterMethod(alwaysRun = true)
public void tearDown(){
	driver.close();
}

}
