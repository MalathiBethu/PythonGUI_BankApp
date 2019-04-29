package driver;

import java.util.Properties;

import org.openqa.selenium.By;

import commonLibs.ExcelDriver;
import commonLibs.KeywordUtilityDriver;
import commonLibs.SendingMailUsingJava;
import commonLibs.Utils;

public class DriverClass {

	
	
	private static KeywordUtilityDriver keywordDriver ;
	private static ExcelDriver oExcelDriver;
	private static String sDriverPropertyFile = "C:\\Users\\M1037726\\Oxygen\\python\\EdurekaFinalProject\\src\\conf\\AutomationInput.properties";
	private static Properties oDriverProperites;
	private static String sInputFileFolder;
	private static String sResultFolder;
	private static String sMainDriverInputFile;
	private static String sCurrentTestCaseStatus;
	
	public static void main(String args[])
	{
		
		oDriverProperites = Utils.getProperties(sDriverPropertyFile);
		sInputFileFolder = oDriverProperites.getProperty("InputFolder");
		sMainDriverInputFile = oDriverProperites.getProperty("DriverInputFile");
		sResultFolder= oDriverProperites.getProperty("ResultFolder");
		testSuiteDriver();
		exportToExcel();	
	}
	
	public static void testSuiteDriver()
	{
		String sTestCaseSheetName, sRunFlag, sRunStatus, sComment;
		String sDriverExcelFile;
		int iRow, iRowCount;
		sDriverExcelFile = sInputFileFolder +"\\" + sMainDriverInputFile;
		oExcelDriver = new ExcelDriver();
		oExcelDriver.OpenExcelWorkBook(sDriverExcelFile);
		
		
		iRowCount = oExcelDriver.getRowCountOfSheet("TestSuite"); //gets the rows in sheet
		
		for(iRow=2; iRow<=iRowCount+1 ; iRow++)
		{
			sTestCaseSheetName = "";
			sRunFlag = "";
			sRunStatus ="";
			sComment ="";
			sCurrentTestCaseStatus = "Pass";
			
			sTestCaseSheetName = oExcelDriver.getCellData("TestSuite", iRow, 2);
			sRunFlag = oExcelDriver.getCellData("TestSuite", iRow, 3);
			
			sTestCaseSheetName = sTestCaseSheetName.trim();
			sRunFlag = sRunFlag.toLowerCase().trim();
			
			if(sRunFlag.equals("yes"))
			{
				keywordDriver = null;
				sRunStatus= testCaseDriver(sTestCaseSheetName);
				if(sRunStatus == "")
				{
					if(sCurrentTestCaseStatus == "Pass")
					{
						sRunStatus= "Pass";
					}
					else
					{
						sRunStatus = "Fail";
						sComment = "One or more steps got failed";
					}
				}
				else
				{
					sComment =sRunStatus;
					sRunStatus ="Fail";
					
				}
				
			}
			else
			{
				sRunStatus = "Skipped";
				sComment = "Because, Run Flag is set to "+ sRunFlag;
			}
			
			oExcelDriver.setCellData("TestSuite", iRow, 4 , sRunStatus);
			oExcelDriver.setCellData("TestSuite", iRow, 5 , sComment);

		}
		
		
		
	}
	
	public static String testCaseDriver(String sSheetName)
	{
		int iRowCount, iRow;
		String sTestCaseDriverReturnValue = "";
		String sActionKeyword;
		String sObjectLocator;
		String sArgumentValue;
		String sRunStatus;
		String sComment;
		String sReturnValue;
		By oBy;
		
		try
		{
			 keywordDriver = new KeywordUtilityDriver();
			 iRowCount = oExcelDriver.getRowCountOfSheet(sSheetName);
			System.out.println("The row count is: "+ iRowCount);
			
			for(iRow =2; iRow <= iRowCount +1; iRow++)
			{
				 sActionKeyword = "";
				 sObjectLocator= "";
				 sArgumentValue= "";
				 sRunStatus= "";
				 sComment= "";
				 sReturnValue= "";
				 oBy= null;
				 
				 sActionKeyword = oExcelDriver.getCellData(sSheetName, iRow, 2).trim();
				 sObjectLocator = oExcelDriver.getCellData(sSheetName, iRow, 3).trim();
				 sArgumentValue = oExcelDriver.getCellData(sSheetName, iRow, 4).trim();
				 
				 if( sObjectLocator!= ""  && !sObjectLocator.equals(""))
				 {
					 oBy = Utils.getLocatorBy(sObjectLocator);
				 }
				 
				 if(sActionKeyword == "")
				 {
					 sRunStatus ="Skipped";
					 sComment = "No Action Keyword";
				 }
				 else
				 {
					 try
					 {
						 sReturnValue = keywordDriver.performAction(sActionKeyword, oBy, sArgumentValue);
						 if(sReturnValue.toLowerCase().contains("error"))
						 {
								sRunStatus = "Fail";
								sComment = sReturnValue;
								sReturnValue = "Failure";
								sTestCaseDriverReturnValue = "Fail";
						 }
						 else if(sReturnValue.equals("File Already Exists"))
						 {
							 	sRunStatus = "Fail";
								sComment = "Verification of Url/Title failed";
								sTestCaseDriverReturnValue = "Fail";			
						 }
						 else if(sReturnValue.contains("is Present"))
						 {
							 sRunStatus = "Pass";
							 sComment = "Element is present";	
							
						 }
						 else if(sReturnValue.contains("Verification Failed"))
						 {
							 	sRunStatus = "Fail";
								sComment = "Verification of Url/Title failed";
								sTestCaseDriverReturnValue = "Fail";
						 }
						 else if(sReturnValue.contains("Text"))
						 {
							 sRunStatus = "Pass";
							 sComment = sReturnValue;	
						 }
						 else
						 {
							    sRunStatus = "pass";
								sReturnValue = "Pass";
								sComment = "The test step passed";
						 }
					 }
					 catch(Exception e)
					 {
						 sRunStatus = "Exception";
						 sComment = e.getMessage();
						 sCurrentTestCaseStatus = "Fail";
					 }
				 }
				 
				 System.out.println("loop"+iRow);
				 oExcelDriver.setCellData(sSheetName, iRow, 5, sRunStatus);
				 oExcelDriver.setCellData(sSheetName, iRow, 6, sReturnValue);
				 oExcelDriver.setCellData(sSheetName, iRow, 7, sComment);
				 System.out.println("Loop completed of "+iRow);
			}
			
			
		}
		catch(Exception e)
		{
			sTestCaseDriverReturnValue = e.getMessage();
			sCurrentTestCaseStatus = "Fail";
		}
		
		return sTestCaseDriverReturnValue;
	}
	
	public static void exportToExcel()
	{
		String sOutputFilename;
		String sDateTimeStamp;
		sDateTimeStamp = Utils.getDateTimeStamp();
		sOutputFilename = sResultFolder + "\\Result as on "+ sDateTimeStamp + ".xlsx";
		oExcelDriver.saveAs(sOutputFilename);		
		SendingMailUsingJava.sendMail(sOutputFilename);
	}
	
	
	


	
	
	
}
