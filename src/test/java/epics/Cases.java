package epics;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import utilities.BaseClass;
import utilities.ReadExcel;

public class Cases extends BaseClass {

	@Test(dataProvider="SalesForceData",dataProviderClass=ReadExcel.class,priority = 0,enabled = true,groups = {"Smoke","Cases","Regression"})
	public void createCase(String UserName, String Password, ITestContext context) throws InterruptedException {
		String Taskid;

		// Launch the app and login with the credentials
		driver.get(context.getCurrentXmlTest().getParameter("URL"));
		driver.findElement(By.name("username")).sendKeys(UserName);
		driver.findElement(By.id("password")).sendKeys(Password);
		driver.findElement(By.id("Login")).click();

		// Click on Global Actions SVG icon
		wait.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(By.xpath("//div[@data-aura-class='oneGlobalCreate']")));
		WebElement button = driver.findElement(By.xpath("//*[name()='svg' and @data-key='add']/ancestor::a"));
		javaScriptExecutor.executeScript("arguments[0].click();", button);

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
		driver.findElement(By.xpath("//button[contains(@class,'publisherShareButton')]/span[text()='Save']")).click();

		// Verifying the confirmation message
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Case']")));
		Assert.assertTrue("Case", driver.findElement(By.xpath("//span[text()='Case']")).isDisplayed());
		Taskid = driver.findElement(By.xpath("//span[text()='Case']/a")).getAttribute("title");
		System.out.println("Newly created task id is, " + Taskid);
		Assert.assertTrue("Case ID", driver.findElement(By.xpath("//div[text()='" + Taskid + "']")).isDisplayed());
		Assert.assertTrue("was created", driver.findElement(By.xpath("//span[text()=' was created.']")).isDisplayed());
	}

	@Test(dataProvider="SalesForceData",dataProviderClass=ReadExcel.class, priority = 1,enabled = false,groups= {"Cases"})
	public void editCase(String UserName, String Password,ITestContext context) throws InterruptedException {
		String TicketNumber;

		// Launch the app and login with the credentials
		driver.get(context.getCurrentXmlTest().getParameter("URL"));
		driver.findElement(By.name("username")).sendKeys(UserName);
		driver.findElement(By.id("password")).sendKeys(Password);
		driver.findElement(By.id("Login")).click();

		// Click on toggle menu button from the left corner
		driver.findElement(By.xpath("//div[@class='slds-icon-waffle']")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='View All']")));
		driver.findElement(By.xpath("//button[text()='View All']")).click();

		// Click view All and click Sales from App Launcher
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-name='Sales']")));
		driver.findElement(By.xpath("//div[@data-name='Sales']")).click();

		// Click on Cases tab visible or select from more.
		javaScriptExecutor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//a[@title='Cases']")));
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
		javaScriptExecutor.executeScript("arguments[0].scrollIntoView(true);",
				driver.findElement(By.xpath("//span[text()='SLA Violation']")));
		driver.findElement(By.xpath("//span[text()='SLA Violation']/parent::span/following-sibling::div")).click();
		driver.findElement(By.xpath("//a[text()='No']")).click();

		// Click on Save and Verify Status as Working
		driver.findElement(By.xpath("//button[contains(@class,'forceActionButton')]/span[text()='Save']")).click();

		// Verifying the confirmation message
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Case']")));
		Assert.assertTrue("Case", driver.findElement(By.xpath("//span[text()='Case']")).isDisplayed());
		System.out.println("Edited task id is, " + TicketNumber);
		Assert.assertTrue("Case ID",
				driver.findElement(By.xpath("//span[text()='\"" + TicketNumber + "\"']")).isDisplayed());
		Assert.assertTrue("was Edited", driver.findElement(By.xpath("//span[text()=' was saved.']")).isDisplayed());
	}

	@Test(dataProvider="SalesForceData",dataProviderClass=ReadExcel.class,priority = 2,enabled = true,groups= {"Regression"},dependsOnGroups = {"Smoke"})
	public void CreateCaseWOmandatoryfields(String UserName, String Password, ITestContext context) throws InterruptedException {
		// Launch the app and login with the credentials
		driver.get(context.getCurrentXmlTest().getParameter("URL"));
		driver.findElement(By.name("username")).sendKeys(UserName);
		driver.findElement(By.id("password")).sendKeys(Password);
		driver.findElement(By.id("Login")).click();

		// Click on toggle menu button from the left corner
		driver.findElement(By.xpath("//div[@class='slds-icon-waffle']")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='View All']")));
		driver.findElement(By.xpath("//button[text()='View All']")).click();

		// Click view All and click Sales from App Launcher
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-name='Sales']")));
		driver.findElement(By.xpath("//div[@data-name='Sales']")).click();

		// Click on Cases tab visible or select from more.
		javaScriptExecutor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//a[@title='Cases']")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Updated')]")));

		// Click on New button
		driver.findElement(By.xpath("//div[text()='New']")).click();

		// Choose Contact Name as 'Naveen Elumalai'
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@title='Search Contacts']")));
		driver.findElement(By.xpath("//input[contains(@title,'Search Contacts')]")).click();
		driver.findElement(By.xpath("//input[@title='Search Contacts']")).sendKeys("Naveen Elumalai");
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mark[text()='Naveen']")));
		javaScriptExecutor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//mark[text()='Naveen']")));
		//driver.findElement(By.xpath("//img[@title='Contact']")).click();

		// Select status as None
		driver.findElement(By.xpath("//label[text()='Status']/following-sibling::div")).click();
		driver.findElement(By.xpath("//span[contains(text(),'None')]")).click();

		// Enter Subject as 'Testing' and description as 'Automation testing'
		javaScriptExecutor.executeScript("arguments[0].scrollIntoView(true);",
				driver.findElement(By.xpath("//span[text()='Internal Comments']")));
		driver.findElement(By.xpath("//span[text()='Subject']/parent::label/following-sibling::input")).click();
		driver.findElement(By.xpath("//span[text()='Subject']/parent::label/following-sibling::input"))
				.sendKeys("Testing");
		driver.findElement(By.xpath("//span[text()='Description']/parent::label/following-sibling::textarea")).click();
		driver.findElement(By.xpath("//span[text()='Description']/parent::label/following-sibling::textarea"))
				.sendKeys("Automation Testing");

		// Click on Save and Verify Status as Working
		driver.findElement(By.xpath("//button[contains(@class,'forceActionButton')]/span[text()='Save']")).click();

		// Verifying the confirmation message
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='errorsList']")));
		Assert.assertEquals("These required fields must be completed: Case Origin, Status",
				driver.findElement(By.xpath("//ul[@class='errorsList']/li")).getText());
	}


	@Test(dataProvider="SalesForceData",dataProviderClass=ReadExcel.class,priority = 3,enabled = true,dependsOnMethods = "createCase",groups= {"Cases"})
	public void deleteCase(String UserName, String Password, ITestContext context) {
		String TicketNumber;

		// Launch the app and login with the credentials
		driver.get(context.getCurrentXmlTest().getParameter("URL"));
		driver.findElement(By.name("username")).sendKeys(UserName);
		driver.findElement(By.id("password")).sendKeys(Password);
		driver.findElement(By.id("Login")).click();

		// Click on toggle menu button from the left corner
		driver.findElement(By.xpath("//div[@class='slds-icon-waffle']")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='View All']")));
		driver.findElement(By.xpath("//button[text()='View All']")).click();

		// Click view All and click Sales from App Launcher
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-name='Sales']")));
		driver.findElement(By.xpath("//div[@data-name='Sales']")).click();

		// Click on Cases tab visible or select from more.
		javaScriptExecutor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//a[@title='Cases']")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Updated')]")));

		// Sorting the date order
		driver.findElement(By.xpath("//span[@title='Date/Time Opened']/parent::a")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//span[@title='Date/Time Opened']/parent::a/following-sibling::span[contains(text(),'Sorted')]")));
		if (driver.findElement(By.xpath("//span[@title='Date/Time Opened']/parent::a/following-sibling::span"))
				.getText().equals("Sorted Ascending")) {
			driver.findElement(By.xpath("//span[@title='Date/Time Opened']/parent::a")).click();
		}
		TicketNumber = driver.findElement(By.xpath("//th[@scope='row']//a")).getAttribute("title");

		// Click on the Dropdown icon and select Delete from the case you created by
		// referring to "case owner alias"
		driver.findElement(By.xpath("//div[contains(@class,'forceVirtualActionMarker')]/a")).click();
		driver.findElement(By.xpath("//a[@title='Delete']")).click();
		driver.findElement(By.xpath("//button[@title='Delete']")).click();

		// Verify the delete confirmation message
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class,'toastMessage')]")));
		assertEquals("Case \"" + TicketNumber + "\" was deleted. Undo",
				driver.findElement(By.xpath("//span[contains(@class,'toastMessage')]")).getText());
		System.out.println(TicketNumber);

		// Verify the case with your name is deleted or not
		driver.findElement(By.xpath("//span[text()='Select List View']/parent::lightning-icon/parent::button")).click();
		driver.findElement(By.xpath("//span[text()='Recently Viewed' and contains(@class,'virtual')]")).click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//input[contains(@placeholder,'Search this')]")));
		driver.findElement(By.xpath("//input[contains(@placeholder,'Search this')]")).click();
		driver.findElement(By.xpath("//input[contains(@placeholder,'Search this')]")).sendKeys(TicketNumber);
		driver.findElement(By.xpath("//span[@title='Date/Time Opened']/parent::a")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='No items to display.']")));
		assertTrue(driver.findElement(By.xpath("//span[text()='No items to display.']")).isDisplayed());
	}

}
