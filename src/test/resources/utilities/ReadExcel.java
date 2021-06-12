package utilities;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {

	@org.testng.annotations.DataProvider(name="SalesForceData")
	public Object[][] readExcel() throws IOException {
		
		DataFormatter dataFormatter = new DataFormatter();
		FileInputStream dataFile = new FileInputStream(".//src/test//resources//data//SalesforceData.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(dataFile);
		XSSFSheet sheetAt = workbook.getSheetAt(0);

		int rows = sheetAt.getPhysicalNumberOfRows();
		int columns = sheetAt.getRow(0).getLastCellNum();
		System.out.println(rows + " "+columns);

		Object[][] salesForceData = new Object[rows - 1][columns];
		for (int row = 1; row < rows; row++) {
			for (int col = 0; col < columns; col++) {	
				salesForceData[row-1][col] = dataFormatter.formatCellValue(sheetAt.getRow(row).getCell(col));
			}

		}
		workbook.close();
		return salesForceData;
	}

}
