package com.qa.opencart.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {
	
	public static final String TEST_DATA_SHEET_PATH = "./src/test/resorces/testData/OpenCartTestData.xlsx";
    public static Workbook book;
	public static Sheet sheet;
	
	public static Object[][] getTestData(String sheetName) { //we make it static so that we can call this by ExcelUtil.java
		Object data[][]=null;
		
		//to capture the sheet
		try {
			FileInputStream ip = new FileInputStream(TEST_DATA_SHEET_PATH);  //by the file input stream we give the particular path of the sheet
			
			book = WorkbookFactory.create(ip);
		    sheet = book.getSheet(sheetName);
		    
		    data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];  //so here we have to give the rows and column 
			                                 //in array[][] so in first we give row and in second we give column 
		    for(int i=0; i< sheet.getLastRowNum(); i++) {
		    	for(int j=0; j<sheet.getRow(0).getLastCellNum(); j++) {
		    		data[i][j] = sheet.getRow(i+1).getCell(j).toString();  //toString bcz we put in excel string		    	}
		         }
		     }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return data;
	
		}
	
}
