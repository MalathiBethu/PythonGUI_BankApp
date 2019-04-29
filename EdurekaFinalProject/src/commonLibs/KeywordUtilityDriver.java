package commonLibs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class KeywordUtilityDriver {
	
	CommonDriver commondriver;
	
	public KeywordUtilityDriver()
	{
		commondriver = new CommonDriver();
	}
	
	public String performAction(String sActionName, By oBy, String sValue)
	{
		
		sActionName = sActionName.trim();
		if(sActionName.isEmpty())
		{
			return "";
		}
		if(sActionName.equalsIgnoreCase("click"))
		{
			commondriver.clickElement(oBy);
			return "";
		}
		if(sActionName.equalsIgnoreCase("openBrowser"))
		{
			commondriver.openBrowser(sValue,"about:blank" );
			return "";
		}
		if (sActionName.equalsIgnoreCase("setPageLoadTimeOut")) {

			commondriver.setPageLoadTimeout(Long.valueOf(sValue));
			return "";
		}
		if(sActionName.equalsIgnoreCase("setElementDetectionTimeout"))
		{
			commondriver.setElementDetectionTimeout(Long.valueOf(sValue));
			return "";
			
		}
		if(sActionName.equalsIgnoreCase("threadsleep"))
		{
			try {
				Thread.sleep(Long.parseLong(sValue));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return "";
		}
		if(sActionName.equalsIgnoreCase("navigateToUrl"))
		{
			commondriver.getUrl(sValue);
			return "";
		}
		if(sActionName.equalsIgnoreCase("navigateBack"))
		{
			commondriver.getDriver().navigate().back();
			return "";
		}
		if(sActionName.equalsIgnoreCase("navigateForward"))
		{
			commondriver.getDriver().navigate().forward();
			return "";
		}
		if(sActionName.equalsIgnoreCase("closeAllBrowser"))
		{
			commondriver.closeBrowser();
			return "";
		}
		if(sActionName.equalsIgnoreCase("closeCurrentBrowser"))
		{
			commondriver.getDriver().close();
			return "";
		}
		if(sActionName.equalsIgnoreCase("waitTillElementIsVisible"))
		{
			commondriver.waitTillElementIsVisible(oBy,Long.valueOf(sValue) );
			return "";
		}
		if(sActionName.equalsIgnoreCase("waitTillElementIsClickable"))
		{
			commondriver.waitTillElementIsClickable(oBy, Long.valueOf(sValue));
			return "";
		}
		if(sActionName.equalsIgnoreCase("savepagesnapshot"))
		{
			String returnvalue = commondriver.savePageSnapShot(sValue);
			return returnvalue;
		}
		if(sActionName.equalsIgnoreCase("submit"))
		{
			commondriver.getDriver().findElement(oBy).submit();
			return "";
		}
		if(sActionName.equalsIgnoreCase("clear"))
		{
			commondriver.getDriver().findElement(oBy).clear();
			return "";
		}
		
		if(sActionName.equalsIgnoreCase("selectParentWindow"))
		{
			commondriver.switchToWindow(0);
			return "";
		}
		if(sActionName.equalsIgnoreCase("acceptAlert"))
		{
			commondriver.getDriver().switchTo().alert().accept();
			return "";
		}
		if(sActionName.equalsIgnoreCase("rejectAlert"))
		{
			commondriver.getDriver().switchTo().alert().dismiss();
			return "";
		}
		if(sActionName.equalsIgnoreCase("clear"))
		{
			commondriver.getDriver().findElement(oBy).clear();
			return "";
		}
		if(sActionName.equalsIgnoreCase("selectDefaultframe"))
		{
			commondriver.getDriver().switchTo().defaultContent();
			return "";
		}
		if(sActionName.equalsIgnoreCase("gettext"))
		{
			commondriver.getDriver().findElement(oBy).getText();
			return "";
		}
		if(sActionName.equalsIgnoreCase("getTextboxText"))
		{
			if (commondriver.getDriver().findElement(oBy).getAttribute("value")
					.equals(sValue)) {
				return "pass";
			} else {
				return "error";
			}
		}
		if(sActionName.equalsIgnoreCase("verifytext"))
		{
			if(commondriver.getDriver().findElement(oBy).getText().equals(sValue))
			{
				return "Text Verified";
			}
			else
			{
				return "error";
			}
		}
		if(sActionName.equalsIgnoreCase("getTitle"))
		{
			commondriver.getDriver().getTitle();
			return "";
		}
		if(sActionName.equalsIgnoreCase("verifyTitle"))
		{
			if(commondriver.getDriver().getTitle().equals(sValue))
			{
				return "pass";
			}
			else
			{
				return "Verification Failed";
			}
		}
		if(sActionName.equalsIgnoreCase("getUrl"))
		{
			commondriver.getDriver().getCurrentUrl();
			return "";
		}
		if(sActionName.equalsIgnoreCase("verifyUrl"))
		{
			if(commondriver.getDriver().getCurrentUrl().equals(sValue))
			{
				return "pass";
			}
			else
			{
				return "Verification Failed";
			}
		}
		if(sActionName.equalsIgnoreCase("setText"))
		{
			commondriver.setText(oBy, sValue);
			return "";
		}
		

		if (sActionName.equalsIgnoreCase("isSelected")) {
			return String.valueOf(commondriver.getDriver().findElement(oBy)
					.isSelected());

		}

		if (sActionName.equalsIgnoreCase("getSelectedItem")) {
			Select olist;
			WebElement oElement;

			oElement = commondriver.getDriver().findElement(oBy);
			olist = new Select(oElement);
			return olist.getFirstSelectedOption().getText();

		}

		if (sActionName.equalsIgnoreCase("selectitembytext")) {
			commondriver.selectItemByVisibleText(oBy, sValue);
			return "";

		}

		if (sActionName.equalsIgnoreCase("selectitembyvalue")) {
			commondriver.SelectItemInListByValue(oBy, sValue);
			return "";

		}

		if (sActionName.equalsIgnoreCase("getItemsCount")) {
			Select olist;
			WebElement oElement;

			oElement = commondriver.getDriver().findElement(oBy);
			olist = new Select(oElement);

			return String.valueOf(olist.getOptions().size());

		}

		if (sActionName.equalsIgnoreCase("isdisplayed")) {
			if(commondriver.getDriver().findElement(oBy).isDisplayed()){
				return "Element is present";
			}
			else{
				return "Element is not present";
			}
		}

		if (sActionName.equalsIgnoreCase("isenabled")) {
			commondriver.getDriver().findElement(oBy).isEnabled();
			return "";
		}
		
		if(sActionName.equalsIgnoreCase("switchToNewWindow")) {
			commondriver.switchToWindow(Integer.parseInt(sValue));
			return "";
		}
		
		if(sActionName.equalsIgnoreCase("scrollToElement")) {
			commondriver.scrollToElement(oBy);
			return "";
		}
		
		if(sActionName.equalsIgnoreCase("selectByValue")) {
			commondriver.SelectItemInListByValue(oBy,sValue);
			return "";
		}
		
		if(sActionName.equalsIgnoreCase("iterateElementsAndClick")){
			System.out.println("Inside Keyword Utility to find Elements");
			commondriver.iterateElementsAndClick(oBy);
			return "";
		}
		return "Error: Unknown keyword..";
	
	}
}
