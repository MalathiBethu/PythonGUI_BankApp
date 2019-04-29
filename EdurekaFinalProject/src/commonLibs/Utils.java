package commonLibs;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;

public class Utils {
	

	public static void waitForSeconds(long seconds)
	{
		try
		{
			Thread.sleep(seconds*1000L);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//to get the locators of the web elements
	public static By getLocatorBy(String sLocatorString)
	{
		try {
		String[] alocator;
		sLocatorString = sLocatorString.trim();
		if(sLocatorString.isEmpty() || !sLocatorString.contains(":="))
		{
				throw new Exception("Invalid Locator String");
			
		}
		
		alocator = sLocatorString.split(":=");
		if(alocator[0].equalsIgnoreCase("id"))
		{
			return By.id(alocator[1]);
		}
		if(alocator[0].equalsIgnoreCase("name"))
		{
			return By.name(alocator[1]);
		}
		if(alocator[0].equalsIgnoreCase("Classname"))
 		{
 			return By.className(alocator[1]);
 		}
 		if(alocator[0].equalsIgnoreCase("cssSelector"))
 		{
 			return By.cssSelector(alocator[1]);
 		}
 		if(alocator[0].equalsIgnoreCase("LinkText"))
 		{
 			return By.linkText(alocator[1]);
 		}
 		if(alocator[0].equalsIgnoreCase("partialLinkText"))
 		{
 			return By.partialLinkText(alocator[1]);
 		}
 		if(alocator[0].equalsIgnoreCase("xpath"))
 		{
 			return By.xpath(alocator[1]);
 		}
 		if(alocator[0].equalsIgnoreCase("tagname"))
 		{
 			return By.tagName(alocator[1]);
 		}
 		throw new Exception("Invalid Locator String");
 		
	
        }
		 catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	
		
	}
	
	
	//to retrieve the properties from the properties file
	public static Properties getProperties(String sPropertiesFile)
	{
		try
		{
			InputStream filereader;
			Properties oProperty;
			
			filereader = new FileInputStream(sPropertiesFile);
			oProperty = new Properties();
			oProperty.load(filereader);
			return oProperty;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	
	//get the date time stamp
	public static String getDateTimeStamp()
	{
		Date oDate;
		String[] sDatePart;
		String sDateStamp;
		oDate = new Date();
		System.out.println(oDate.toString());
		//day month date hrs:min:secs IST Year format
		
		sDatePart = oDate.toString().split(" ");
		sDateStamp = sDatePart[5] + "_" + sDatePart[1] + "_"+
		             sDatePart[2] + "_"+ sDatePart[3];
		
		sDateStamp = sDateStamp.replace(":","_");
		System.out.println(sDateStamp);
		return sDateStamp;
	}
}
