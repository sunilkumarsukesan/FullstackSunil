package epics;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import utilities.ReadExcel;
import utilities.SeleniumMethods;

public class ConsumptionSchedule extends SeleniumMethods {
	String name, deleteConsumptionScheme;
	String cloneName = RandomStringUtils.randomAlphabetic(5);

	@Test(dataProvider = "SalesForceData", dataProviderClass = ReadExcel.class, enabled = true)
	public void createConsumptionSchedule(String UserName, String Password, ITestContext context)
			throws InterruptedException {
		name = RandomStringUtils.randomAlphabetic(5);

		// Launch the app and login with the credentials
		loadAddress(context.getCurrentXmlTest().getParameter("URL"));
		type("name", "username", UserName);
		type("id", "password", Password);
		click("id", "Login");

		// 4. Click on Global Actions SVG icon and click View All
		click("xpath", "//div[contains(@class,'appLauncher ')]");
		click("xpath", "//button[text()='View All']");

		// 5. Click on Consumption Schedule and Click new
		scrollPageToFind("xpath", "//p[text()='Consumption Schedules']");
		click("xpath", "//p[text()='Consumption Schedules']");
		waitForTheElementToBeClickable("xpath", "//div[text()='New']");
		click("xpath", "//div[text()='New']");

		// 6. Enter Name as "Your Name"
		waitForTheElementToBeClickable("xpath",
				"//span[text()='Consumption Schedule Name']/parent::label/following-sibling::input");
		click("xpath", "//span[text()='Consumption Schedule Name']/parent::label/following-sibling::input");
		type("xpath", "//span[text()='Consumption Schedule Name']/parent::label/following-sibling::input", name);

		// 7. Select Type as Range
		click("xpath", "//span[text()='Type']/parent::span/following-sibling::div");
		click("xpath", "//a[text()='Range']");

		// 8. Enter billing term as 6
		click("xpath", "//span[text()='Billing Term']/parent::label/following-sibling::input");
		type("xpath", "//span[text()='Billing Term']/parent::label/following-sibling::input", "6");

		// 9. Select Billing term Unit as Month
		click("xpath", "//span[text()='Billing Term Unit']/parent::span/following-sibling::div");
		click("xpath", "//a[text()='Month']");

		// 10. Click on Save
		click("xpath", "//button[contains(@class,'forceActionButton')]/span[text()='Save']");

		// 11. Verify the newly created Consumption Schedule
		waitForTheElementToBeVisible("xpath", "//span[contains(@class,'toastMessage')]");
		System.out.println(driver.findElement(By.xpath("//span[contains(@class,'toastMessage')]")).getText());
		Assert.assertEquals("Consumption Schedule \"" + name + "\" was created.", getText("xpath", "//span[contains(@class,'toastMessage')]"));
	}

	@Test(dataProvider = "SalesForceData", dataProviderClass = ReadExcel.class, dependsOnMethods = "createConsumptionSchedule", enabled = true)
	public void EditConsumptionSchedule(String UserName, String Password, ITestContext context)
			throws InterruptedException {
		// Launch the app and login with the credentials
		loadAddress(context.getCurrentXmlTest().getParameter("URL"));
		type("name", "username", UserName);
		type("id", "password", Password);
		click("id", "Login");

		// 4. Click on Global Actions SVG icon and click View All
		click("xpath", "//div[contains(@class,'appLauncher ')]");
		click("xpath", "//button[text()='View All']");

		// 5. Click on Consumption Schedule
		scrollPageToFind("xpath", "//p[text()='Consumption Schedules']");
		click("xpath", "//p[text()='Consumption Schedules']");

		// 6. Click on show more Action on the created Schedule
		click("xpath", "//div[@class='forceVirtualActionMarker forceVirtualAction']");

		// 7. Click on edit
		clickUsingJavaScriptExecutor("xpath", "//a[@title='Edit']/div[@role='button']");

		// 8. Change Type as Slab
		Thread.sleep(2000);
		waitForTheElementToBeVisible("xpath", "//h2[text()='Edit " + name + "']");
		click("xpath", "//span[text()='Type']/parent::span/following-sibling::div");
		click("xpath", "//a[text()='Slab']");

		// 9. Add description
		click("xpath", "//span[text()='Description']/parent::label/following-sibling::textarea");
		type("xpath", "//span[text()='Description']/parent::label/following-sibling::textarea",
				"Editing Consumption Schedule");

		// 10. Click on Save
		click("xpath", "//button[contains(@class,'forceActionButton')]/span[text()='Save']");

		// 11.Click on the Edited Consumption Schedule
		click("xpath", "//input[@placeholder='Search this list...']");
		type("xpath", "//input[@placeholder='Search this list...']", name);
		keyboardActions("xpath", "//input[@placeholder='Search this list...']", Keys.ENTER);
		waitForTheElementToBeClickable("xpath", "//a[text()='" + name + "']");
		Thread.sleep(3000);
		click("xpath", "//a[text()='" + name + "']");

		// 12. Verify Type and Description
		waitForTheElementToBeVisible("xpath",
				"//div[contains(@class,'container slds-form-element__label')]//span[text()='Type']");
		assertEquals(getText("xpath", "//div[contains(@class,'itemBody')]//span[text()='Slab']"), "Slab");
	}

	@Test(dataProvider = "SalesForceData", dataProviderClass = ReadExcel.class, dependsOnMethods = "EditConsumptionSchedule", enabled = true)
	public void cloneConsumptionSchedule(String UserName, String Password, ITestContext context)
			throws InterruptedException {
		// Launch the app and login with the credentials
		loadAddress(context.getCurrentXmlTest().getParameter("URL"));
		type("name", "username", UserName);
		type("id", "password", Password);
		click("id", "Login");

		// 4. Click on Global Actions SVG icon and click View All
		click("xpath", "//div[contains(@class,'appLauncher ')]");
		click("xpath", "//button[text()='View All']");

		// 5. Click on Consumption Schedule
		scrollPageToFind("xpath", "//p[text()='Consumption Schedules']");
		click("xpath", "//p[text()='Consumption Schedules']");

		// 6. Click on show more Action on the created Schedule
		click("xpath", "//input[@placeholder='Search this list...']");
		type("xpath", "//input[@placeholder='Search this list...']", name);
		keyboardActions("xpath", "//input[@placeholder='Search this list...']", Keys.ENTER);
		waitForTheElementToBeClickable("xpath", "//a[text()='" + name + "']");
		Thread.sleep(3000);
		click("xpath", "//a[text()='" + name + "']");

		// 7. Click on the Show More tab
		waitForTheElementToBeClickable("xpath", "//span[text()='Show more actions']");
		clickUsingJavaScriptExecutor("xpath", "//span[text()='Show more actions']");

		// 8. Click on clone
		click("xpath", "//a[@title='Clone']");

		// 9. Append The Name with 001
		clearTheTextBox("xpath", "//span[text()='Consumption Schedule Name']/parent::label/following-sibling::input");
		type("xpath", "//span[text()='Consumption Schedule Name']/parent::label/following-sibling::input", cloneName);

		// 10. Click on Save
		click("xpath", "//button[contains(@class,'forceActionButton')]/span[text()='Save']");

		// 11. Verify the clone name
		waitForTheElementToBeVisible("xpath", "//span[contains(@class,'toastMessage')]");
		System.out.println(driver.findElement(By.xpath("//span[contains(@class,'toastMessage')]")).getText());
		Assert.assertEquals("Consumption Schedule \"" + cloneName + "\" was created.",
				driver.findElement(By.xpath("//span[contains(@class,'toastMessage')]")).getText());

		// 12. Click on Global Actions SVG icon and click View All
		click("xpath", "//div[contains(@class,'appLauncher ')]");
		click("xpath", "//button[text()='View All']");

		// 13. Click on Consumption Schedule
		scrollPageToFind("xpath", "//p[text()='Consumption Schedules']");
		click("xpath", "//p[text()='Consumption Schedules']");

		Thread.sleep(2000);
		if (isElementDisplayed("xpath", "//button[@data-element-id='searchClear']")) {
			click("xpath", "//button[@data-element-id='searchClear']");
		}

		// 11.Click on the Edited Consumption Schedule
		click("xpath", "//input[@placeholder='Search this list...']");
		type("xpath", "//input[@placeholder='Search this list...']", cloneName);
		keyboardActions("xpath", "//input[@placeholder='Search this list...']", Keys.ENTER);
		waitForTheElementToBeClickable("xpath", "//a[text()='" + name + "']");
		Thread.sleep(3000);
		assertTrue(isElementDisplayed("xpath", "//a[text()='" + cloneName + "']"));

	}

	@Test(dataProvider = "SalesForceData", dataProviderClass = ReadExcel.class, enabled = true)
	public void deleteConsumptionSchedule(String UserName, String Password, ITestContext context)
			throws InterruptedException {

		// Launch the app and login with the credentials
		loadAddress(context.getCurrentXmlTest().getParameter("URL"));
		type("name", "username", UserName);
		type("id", "password", Password);
		click("id", "Login");

		// 4. Click on Global Actions SVG icon and click View All
		click("xpath", "//div[contains(@class,'appLauncher ')]");
		click("xpath", "//button[text()='View All']");

		// 5. Click on Consumption Schedule
		scrollPageToFind("xpath", "//p[text()='Consumption Schedules']");
		click("xpath", "//p[text()='Consumption Schedules']");

		// 6. Click on show more Action on the created Schedule
		deleteConsumptionScheme = getText("xpath", "//th//a[contains(@class,'outputLookupLink')]");
		click("xpath", "//div[@class='forceVirtualActionMarker forceVirtualAction']");

		// 7. Click on Delete
		clickUsingJavaScriptExecutor("xpath", "//a[@title='Delete']/div[@role='button']");

		// 8. Click delete on the Confirmation dialogue box
		click("xpath", "//span[text()='Delete']");

		// 9. Verify the Schedule is deleted
		waitForTheElementToBeVisible("xpath", "//span[contains(@class,'toastMessage')]");
		System.out.println(driver.findElement(By.xpath("//span[contains(@class,'toastMessage')]")).getText());
		Assert.assertEquals("Consumption Schedule \"" + deleteConsumptionScheme + "\" was deleted. Undo",
				driver.findElement(By.xpath("//span[contains(@class,'toastMessage')]")).getText());

	}

	@Test(dataProvider = "SalesForceData", dataProviderClass = ReadExcel.class,enabled = true)
	public void createWithoutMandatoryFields(String UserName, String Password, ITestContext context)
			throws InterruptedException {

		// Launch the app and login with the credentials
		loadAddress(context.getCurrentXmlTest().getParameter("URL"));
		type("name", "username", UserName);
		type("id", "password", Password);
		click("id", "Login");

		// 4. Click on Global Actions SVG icon and click View All
		click("xpath", "//div[contains(@class,'appLauncher ')]");
		click("xpath", "//button[text()='View All']");

		// 5. Click on Consumption Schedule and Click new
		scrollPageToFind("xpath", "//p[text()='Consumption Schedules']");
		click("xpath", "//p[text()='Consumption Schedules']");
		waitForTheElementToBeClickable("xpath", "//div[text()='New']");
		click("xpath", "//div[text()='New']");
		waitForTheElementToBeClickable("xpath",
				"//span[text()='Consumption Schedule Name']/parent::label/following-sibling::input");

		// 6. Click on save
		click("xpath", "//button[contains(@class,'forceActionButton')]/span[text()='Save']");
		
		// 7. Verify the Error Message Displayed	
		assertTrue(isElementDisplayed("xpath", "//span[text()='Review the errors on this page.']"));
	}

}
