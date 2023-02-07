package com.lg.utilities;

import java.io.FileInputStream;
import java.util.*;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelReader {
    static Object[][] testSuitesRunModes = null;

    public static Object[][] getExcelData(String fileName, String sheetName) {
        Object[][] data = null;
        try {
            FileInputStream fis = new FileInputStream(fileName);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet(sheetName);
            XSSFRow row = sheet.getRow(0);
            int noOfRows = sheet.getPhysicalNumberOfRows();
            int noOfCols = row.getLastCellNum();
            org.apache.poi.ss.usermodel.Cell cell;
            data = new Object[noOfRows - 1][1];

            HashMap<String, String> rowData;
            for (int rowNum = 1; rowNum < noOfRows; rowNum++) {
                rowData = new HashMap<>();
                for (int colNum = 0; colNum < noOfCols; colNum++) {
                    row = sheet.getRow(rowNum);
                    cell = row.getCell(colNum);
                    String cellData = cell.getStringCellValue();
                    String columnName = sheet.getRow(0).getCell(colNum).getStringCellValue();
                    rowData.put(columnName, cellData);
                }
                data[rowNum - 1] [0] = rowData;
            }
        } catch (Exception e) {
            System.out.println("The exception is: " + e.getMessage());
            e.printStackTrace();
        }
        return data;
    }

    public static Object[][] getTestSuiteData(String fileName, String sheetName) {
        if (testSuitesRunModes == null) {
            testSuitesRunModes = ExcelReader.getExcelData(fileName, sheetName);
        }
        return testSuitesRunModes;
    }
}