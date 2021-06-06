package utilities;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class Tools {
	WebDriver driver;
	
	public Tools(WebDriver driver) {
		this.driver = driver;
	}

	public void setDate(int days) throws Exception {
		int MonthDisplayed;
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime currentDay = LocalDateTime.now();
		LocalDateTime currentDayPlus = currentDay.plusDays(days);
		System.out.println("Date to be set is, "+pattern.format(currentDayPlus));
		
		//if the future date is on the same year
		if ((currentDayPlus.getYear() == currentDay.getYear()) && (days>0)) {
			for (int diff = 0; diff < (currentDayPlus.getMonthValue() - currentDay.getMonthValue()); diff++) {
				Thread.sleep(250);
				driver.findElement(By.xpath("//a[contains(@class,'nextMonth')]")).click();
			}
		}
		
		//if the future date is on a different year
		else if (days>0)
		{
			//setting the year
			Select select = new Select(driver.findElement(By.xpath("//select[@class='slds-select picklist__label']")));
			select.selectByValue(String.valueOf(currentDayPlus.getYear()));
			
			MonthDisplayed = Month.valueOf(driver.findElement(By.xpath("//h2[@class='monthYear']")).getText().toUpperCase()).getValue();

			
			//if the month to be selected comes before the current month shown in the calendar
			if (MonthDisplayed > currentDayPlus.getMonthValue())
			{
				for (int diff = 0; diff < (MonthDisplayed - currentDayPlus.getMonthValue()); diff++) {
					Thread.sleep(250);
					driver.findElement(By.xpath("//a[contains(@class,'prevMonth')]")).click();
				}
			}
			//if the month to be selected comes after the current month shown in the calendar
			else if (MonthDisplayed < currentDayPlus.getMonthValue())
			{
				for (int diff = 0; diff < (currentDayPlus.getMonthValue() - MonthDisplayed); diff++) {
					Thread.sleep(250);
					driver.findElement(By.xpath("//a[contains(@class,'nextMonth')]")).click();
				}	
			}
			
		}
		else
		{
			 throw new Exception("cannot set a past date");
		}
		
		driver.findElement(By.xpath("//td[@data-datevalue='" + pattern.format(currentDayPlus) + "']")).click();
	}
}
