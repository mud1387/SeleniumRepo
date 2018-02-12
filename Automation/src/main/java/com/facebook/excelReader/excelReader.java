package com.facebook.excelReader;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class excelReader {

	public FileInputStream fis;
	public XSSFWorkbook wb;
	public XSSFSheet sheet;
	public String path;
	public XSSFRow row;
	public XSSFCell cell;
	
	public excelReader(String path) {
		
		this.path=path;
		
		try {
			fis= new FileInputStream(path);
			wb=new XSSFWorkbook(fis);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String[][] getDataFromSheet(String sheetName, String excelName){
		
		String data[][]=null;
		
		try {
			XSSFSheet sheet= wb.getSheet(sheetName);
			int totalRow= sheet.getLastRowNum()+1;
			int totalColumn= sheet.getRow(0).getLastCellNum();
			
			data= new String[totalRow-1][totalColumn];
			
			for(int i=1;i<totalRow;i++) {
				
				XSSFRow rows= sheet.getRow(i);
				
				for(int j=0; j<totalColumn;j++) {
					
					XSSFCell cell=rows.getCell(j);
					if(cell.getCellType()==Cell.CELL_TYPE_STRING) {
						
						data[i-1][j]=cell.getStringCellValue();
					}
					else if (cell.getCellType()==Cell.CELL_TYPE_NUMERIC) {
						
						String cellText= String.valueOf(cell.getNumericCellValue());
						data[i-1][j]=cellText;
					}
					else {
						
						data[i-1][j]=String.valueOf(cell.getBooleanCellValue());
					}
				}
			}
			
			return data;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	public String getCellData(String sheetName, String columnName, int rowNum) {
		
		try {
			
			int colNum=0;
			int index=wb.getSheetIndex(sheetName);
			sheet=wb.getSheetAt(index);
			XSSFRow row= sheet.getRow(0);
			
			for(int i=0; i<row.getLastCellNum();i++) {
				
				if(row.getCell(i).getStringCellValue().equals(columnName)) {
					colNum=i;
					break;
				}
				
				row=sheet.getRow(rowNum-1);
				
			XSSFCell cell=row.getCell(colNum);	
			if(cell.getCellType()==Cell.CELL_TYPE_STRING) {
				
				return cell.getStringCellValue();
			}
			else if (cell.getCellType()==Cell.CELL_TYPE_BLANK) {
				return "";
			}
		}
		
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return null;
	}
	
}
