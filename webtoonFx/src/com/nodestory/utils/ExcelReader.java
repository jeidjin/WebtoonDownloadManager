package com.nodestory.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	public static List<Map<String, String>> getExcelReader() {
		
		String[] colName = { "monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday" };
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		try {
			
			FileInputStream fis = new FileInputStream("c:\\wdmFx\\db\\webtoonList1.xlsx");
			
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			
			// 시트 수 (첫번째에만 존재하므로 0을 준다)
			// 만약 각 시트를 읽기위해서는 FOR문을 한번더 돌려준다
			XSSFSheet sheet = workbook.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();
			
			Map<String, String> map = null;

			for (int i = 0; i < rows; i++) {

				if (i != 0) {

					XSSFRow row = sheet.getRow(i); // 행을 읽는다

					String value = "";

					// 셀의 갯수를 실제 필드갯수로 지정.
					int cells = colName.length;

					map = new LinkedHashMap<String, String>();

					for (int j = 0; j < cells; j++) {

						XSSFCell cell = row.getCell(j); // 셀값을 읽는다

						if (cell != null) {
							switch (cell.getCellType()) {
							case XSSFCell.CELL_TYPE_FORMULA:
								if (!"".equals(cell.toString())) {
									value = cell.getCellFormula();
								}
								break;
							case XSSFCell.CELL_TYPE_NUMERIC:
								value = cell.getRawValue();
								break;
							case XSSFCell.CELL_TYPE_STRING:
								value = cell.getStringCellValue();
								break;
							case XSSFCell.CELL_TYPE_BLANK:
								value = "";
								break;
							case XSSFCell.CELL_TYPE_ERROR:
								value = cell.getErrorCellValue() + "";
								break;
							default:
								break;
							}

							map.put(colName[j], value);
						}

					} // end for

					if (map.get(colName[0]) != null)
						list.add(map);
					
				} // end for
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		return list;
	}

}
