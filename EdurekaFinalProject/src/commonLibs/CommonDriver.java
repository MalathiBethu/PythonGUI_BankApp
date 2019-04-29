package commonLibs;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonDriver {

	
	//Declare the WebDriver
	private WebDriver driver;
	private long lngPageLoadTimeout;
	private long lngImplicitTimeout;
	String firstChildWindow;
	
	//Constructor
	public CommonDriver()
	{
		lngPageLoadTimeout = 60L;
		lngImplicitTimeout = 30L;
	}
	
	//Declare the PageLoadTimeout and ElementDetectionTimeout
	public void setPageLoadTimeout(long lngPageLoadTimeout)
	{
		this.lngPageLoadTimeout= lngPageLoadTimeout;
	}

	//set the element detection timeout
    public void setElementDetectionTimeout(long lngImplicitTimeout)
    {
    	this.lngImplicitTimeout= lngImplicitTimeout;
    }
	
	//Method for ivoking the browser based on the browsertype
    public void openBrowser(String sBrowserType, String Url)
    {
    	try
    	{
    		switch(getBrowserTypeIndexed(sBrowserType))
    		{
    		case 1:
    			System.setProperty("webdriver.gecko.driver","D:\\System_Softwares\\GeckoDrivers\\geckodriver-v0.23.0-win32\\geckodriver.exe");
    			driver = new FirefoxDriver();
    			break;
    		case 2:
    			System.setProperty("webdriver.ie.driver", "D:\\System_Softwares\\Selenium_WebDriver\\IEDriverServer_x64_3.3.0\\IEDriverServer.exe");
    			driver = new InternetExplorerDriver();
    			break;
    			
    		case 3:
    			System.setProperty("webdriver.chrome.driver","D:\\Chromedriver\\chromedriver_win32\\chromedriver.exe");
    			driver = new ChromeDriver();
    			break;
    			default:
    				throw new Exception("Unknown Browser Type= "+sBrowserType);
    		}
    	
    		if(Url.isEmpty())
    		{
    		  Url = "about:blank";	
    		}
    		driver.manage().window().maximize();
    		driver.manage().deleteAllCookies();
    		driver.manage().timeouts().pageLoadTimeout(lngPageLoadTimeout, TimeUnit.SECONDS);
    		driver.manage().timeouts().implicitlyWait(lngImplicitTimeout, TimeUnit.SECONDS);
    	    driver.get(Url);
    	    //Thread.sleep(2000);
    	
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    
    public int getBrowserTypeIndexed(String sBrowserType)
    {
    	sBrowserType = sBrowserType.toLowerCase().trim();
    	if(sBrowserType.isEmpty())
    	{
    		return -1;
    	}
    	
    	if(sBrowserType.equals("ff") || sBrowserType.equals("firefox") || sBrowserType.equals("mozilla firefox") )
    	{
    		return 1;
    	}
    	if(sBrowserType.equals("ie") || sBrowserType.equals("internetexplorer") || sBrowserType.equals("explorer"))
    	{
    		return 2;
    	}
    	if(sBrowserType.equals("chrome") || sBrowserType.equals("google") || sBrowserType.equals("google chrome"))
    	{
    		return 3;
    	}
    	
    	return -1;
    }
    
	//close the browser
    public void closeBrowser()
    {
    	try
    	{
    		if(driver!=null)
    		{
    			driver.quit();
    		}
    		
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
	//get the webdriver object
	public WebDriver getDriver()
	{
		return driver;
		
	}
    
    
    //get url
	public void getUrl(String url)
	{
		driver.get(url);
	}
	
	
	//waitTillElementIsVisible
	public void waitTillElementIsVisible(By oBy, long timeoutseconds)
	{
		try
		{
			WebDriverWait owait = new WebDriverWait(driver, timeoutseconds);
			owait.until(ExpectedConditions.visibilityOfElementLocated(oBy));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	//waitTillElementIsClickable
	public void waitTillElementIsClickable(By oBy, long timeoutseconds)
	{
		try
		{
			WebDriverWait owait = new WebDriverWait(driver, timeoutseconds);
			owait.until(ExpectedConditions.elementToBeClickable(oBy));	
		}
		catch(Exception e)
		{
			
		}
	}
	
	
	
	//SavePageSnapshot
	public String savePageSnapShot(String sImagePath)
	{
		try
		{
		TakesScreenshot oCamera;
		File oTmpFile, oImageFile;
		oImageFile = new File(sImagePath);

		if (new File(sImagePath).exists()) {
			throw new Exception("Image File already Exists");
		}

		oCamera = (TakesScreenshot)driver;
		oTmpFile = oCamera.getScreenshotAs(OutputType.FILE);
		oCamera.getScreenshotAs(OutputType.FILE);

		FileUtils.copyFile(oTmpFile, oImageFile);

		return "File got saved";

	} catch (Throwable t) {
		t.printStackTrace();
		return "File already exists";
	}
	
	}
	
	//setText
	public void setText(By oBy, String sText)
	{
		try
		{
		driver.findElement(oBy).sendKeys(sText);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//getText
	public String getText(By oBy)
	{
		try
		{
			return driver.findElement(oBy).getText();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}
	
	
	//ClickElement
	public void clickElement(By oBy)
	{
		try
		{
			driver.findElement(oBy).click();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	//SelectItemInListByVisibleText
	public void selectItemByVisibleText(By oBy, String sItemVisibleText)
	{
		try
		{
		Select list;
		list = new Select(driver.findElement(oBy));
		list.selectByVisibleText(sItemVisibleText);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	//SelectItemInListByIndex
	public void SelectItemInListByIndex(By oBy, int sItemIndex)
	{
		try
		{
		Select list;
		list = new Select(driver.findElement(oBy));
		list.selectByIndex(sItemIndex);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//SelectItemInListByValue
	public void SelectItemInListByValue(By oBy, String sItemByValue)
	{
		try
		{
		Select list;
		list = new Select(driver.findElement(oBy));
		list.selectByValue(sItemByValue);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	

	
	//switch to window
	public void switchToWindow(int x)
	{
		try
		{
		firstChildWindow = driver.getWindowHandles().toArray()[x].toString();
		driver.switchTo().window(firstChildWindow);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		}
	
	//scroll to element
	public void scrollToElement(By oBy)
	{
		JavascriptExecutor jse;
		jse = (JavascriptExecutor)driver;
		int x = driver.findElement(oBy).getLocation().x;
		int y = driver.findElement(oBy).getLocation().y;
		String command = String.format("window.scrollTo(%d,%d)", x,y);
		jse.executeScript(command);
	}
	
	
	//iterate elements and click
	public void iterateElementsAndClick(By oBy)
	{
		List<WebElement> resultlist = driver.findElements(oBy);
		for(WebElement eachResult : resultlist)
		{
			eachResult.click();
		}
	}
	
}
