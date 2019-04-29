package commonLibs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDriver {

	private InputStream inputstream;
	private OutputStream outputstream;
	private Workbook workbook;
	private String excelfilename;
	private Sheet sheet;
	private Row row;
	private Cell cell;

	public ExcelDriver() {
		this.setNull();

	}

	public void setNull() {
		inputstream = null;
		outputstream = null;
		workbook = null;
		excelfilename = "";
	}

	public void createExcelWorkBook(String filename) throws Exception {
		try {
			filename = filename.trim();
			if (filename.isEmpty()) {
				throw new Exception("No filename specified");
			}
			if ((new File(filename)).exists()) {
				throw new Exception("File Already exists");
			}
			if (filename.toLowerCase().endsWith("xlsx")) {
                workbook = new XSSFWorkbook();
			} else if (filename.toLowerCase().endsWith("xls")) {
				workbook = new HSSFWorkbook();
				
			} else {
				throw new Exception("Invalid File Extension");
			}

			outputstream = new FileOutputStream(filename);
			workbook.write(outputstream);
			outputstream.close();
			workbook.close();
            this.setNull();
            
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void OpenExcelWorkBook(String filename) {
		try
		{
			filename = filename.trim();
			if(filename.isEmpty())
			{
				throw new Exception("No file name Specified");
			}
			if(!(new File(filename)).exists())
			{
				throw new Exception("File does not exists");
			}
			
			inputstream = new FileInputStream(filename);
			excelfilename = filename;
			workbook = WorkbookFactory.create(inputstream);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

	public void createSheet(String sheetname, String sworkbook) {
		try
		{
			sheetname = sheetname.trim();
			if(sheetname.isEmpty())
			{
				throw new Exception("sheet name not specified");
			}
			
			Sheet osheet;
			OpenExcelWorkBook(sworkbook);
			osheet = (Sheet) workbook.getSheet(sheetname);
			if(osheet !=null)
			{
				throw new Exception("sheet already Exist");
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		

	}

	public int getRowCountOfSheet(String Sheetname) {
		try
		{
			Sheetname= Sheetname.trim();
			if(Sheetname.isEmpty())
			{
				throw new Exception("Sheetname not specified");
			}
			Sheet osheet;
			osheet = workbook.getSheet(Sheetname);
			System.out.println("Sheet name: "+ osheet);
			if(osheet == null)
			{
				throw new Exception("Sheet does not exist");
			}		
			return osheet.getLastRowNum(); // gives the index of the last row
			
		    
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return -1;
		}
		

	}

	public int getCellCount(String Sheetname, int irow) {
		try
		{
			Sheetname = Sheetname.trim();
			if(Sheetname.isEmpty())
			{
				throw new Exception("File not specified");
			}
			Sheet oSheet;
			oSheet =workbook.getSheet(Sheetname);
			if(oSheet == null)
			{
				throw new Exception("Sheet does not exist");
			}
			
			if(irow<1)
			{
				throw new Exception("Row index cannot be less than 1");
			}
			
			row = oSheet.getRow(irow-1);
			
			if(row == null)
			{
				return 0;
			}
			else
			{
				return row.getLastCellNum() + 1;
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return -1;
		}
		
	}

	public String getCellData(String Sheetname, int irow, int cellnum) {
	
		try
		{
			Sheetname = Sheetname.trim();
			if(Sheetname.isEmpty())
			{
				throw new Exception("Sheet name not specified");
			}
			sheet = workbook.getSheet(Sheetname);
			
			if(sheet == null)
			{
				throw new Exception("Sheet does not Exist");
				
			}
			if(irow<1 || cellnum <1)
			{throw new Exception("Row & Cell index start form 1");
				
			}
			
			row = sheet.getRow(irow-1);
			if(row == null)
			{
				return "";
			}
		   
			cell = row.getCell(cellnum-1);
			if(cell == null)
			{
				//fill the return string as per your requirement
				return "";
			}
			else
			{
				int type = cell.getCellType();
				if(type == Cell.CELL_TYPE_NUMERIC)
				{
					return String.valueOf((long) cell.getNumericCellValue());
				}
				else
				{
					return cell.getStringCellValue();
				}
			}
			
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "";
		}

	}

	public void setCellData(String sheetname, int irow , int cellnum, String sValue) {

		try
		{
			sheetname = sheetname.trim();
			if(sheetname.isEmpty())
			{
				throw new Exception("sheet name not specified");
			}
			
			//Sheet sheet;
			sheet = workbook.getSheet(sheetname);
			if(sheet == null)
			{
				throw new Exception("sheet does not exist");
			}
			
			if(irow<1 || cellnum<1)
			{
				throw new Exception("Row & Cell Index Start from 1");
			}
			
			
			Row row = sheet.getRow(irow-1);
			
			if(row == null)
			{
				sheet.createRow(irow-1);
				row = sheet.getRow(irow-1);	
			}
			
			
			Cell ocell;
		    ocell = row.getCell(cellnum-1);
		    if(ocell == null)
		    {
		      row.createCell(cellnum-1);
		      ocell = row.getCell(cellnum-1);
		    }
		    
		    ocell.setCellValue(sValue);	
		    System.out.println("cell value is"+ocell.getStringCellValue());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	public void close() {
		try
		{
			workbook.close();
			outputstream.close();
			setNull();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

	public void save() {
		try
		{
		outputstream = new FileOutputStream(excelfilename);
		workbook.write(outputstream);
        outputstream.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	public void saveAs(String filename) {
	try
	{
		if(filename.isEmpty())
		{
			throw new Exception("No filename specified");
		}
		if((new File(filename)).exists())
       {
    	   throw new Exception("File already Exists");
       }
       
       outputstream = new FileOutputStream(filename);
       workbook.write(outputstream);
       outputstream.close();
       
    }
	catch(Exception e)
	{
		e.printStackTrace();
	}
	}

}
