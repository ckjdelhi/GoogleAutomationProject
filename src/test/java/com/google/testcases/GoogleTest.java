package com.google.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.drivers.DriverFactory;
import com.google.drivers.Utils;

public class GoogleTest {
	private static final String GOOGLE_URL = "https://www.google.com";
	private WebDriver driver;
	private String doogleSearchText;
	
	@BeforeClass
	public void setUp() {
		driver=DriverFactory.getBrowser("chrome");
		driver.manage().window().maximize();
	}
	@Test
	public void canTestGooglePageIsOpened() {
		driver.get(GOOGLE_URL);
		//Check title
		Assert.assertEquals(driver.getTitle(), "Google");
		//Check About Link
		Assert.assertTrue(findElement(By.linkText("About")).isDisplayed());
		//Check Gmail Link
		Assert.assertTrue(findElement(By.linkText("Gmail")).isDisplayed());
	}
	
	@Test(dependsOnMethods = "canTestGooglePageIsOpened")
	public void canExtractDoogleTextFromImageLink() {
		doogleSearchText = Utils.extractText(findElement(By.xpath("//*[@id='hplogo']/a")).getAttribute("href"));	
		Assert.assertNotNull(doogleSearchText);
	}
	
	@Test(dependsOnMethods = "canExtractDoogleTextFromImageLink")
	public void canSearchTextInGoogle() {
		WebElement findElement = findElement(By.name("q"));
		String finalSearchText = Utils.removeSpecialChars(doogleSearchText);
		findElement.sendKeys(finalSearchText);
		findElement.submit();
		//validate current URL contains image text
		Assert.assertTrue(driver.getCurrentUrl().contains(doogleSearchText));
		//validate the search bar contains image text
		Assert.assertEquals(findElement(By.xpath("//*[@name='q']")).getAttribute("value"), finalSearchText);
	}
	@Test(dependsOnMethods = "canSearchTextInGoogle")
	public void canClickOn3rdLink() {
		//Wait until the google page shows the result
	    WebElement myDynamicElement = (new WebDriverWait(driver, 10))
	    		.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".WvKfwe")));
	    String currentUrl= driver.getCurrentUrl();
	    //Find 3rd link and click
	    findElement(By.cssSelector(".WvKfwe > div:nth-of-type(3) > div > div > a")).click();
	    
	    //Check currentURL should not be newURL
	    Assert.assertNotEquals(driver.getCurrentUrl(), currentUrl);
	}

	@AfterClass
	public void close() {
		driver.quit();
	}
	private WebElement findElement(By by) {
		return driver.findElement(by);
	}
	
}
