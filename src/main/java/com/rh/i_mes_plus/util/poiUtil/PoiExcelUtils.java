package com.rh.i_mes_plus.util.poiUtil;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PoiExcelUtils {
	public static Workbook exportExcel(String[] titles,String name) throws Exception{

		// 新建工作簿对象
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 创建sheet对象
		XSSFSheet sheet = workBook.createSheet(name);
		// 创建行,标题行
		XSSFRow row = sheet.createRow(0);
		for(int i = 0; i < titles.length; i++){
			// 创建单元格
			XSSFCell cell = row.createCell(i);
			// 设置单元格内容
			cell.setCellValue(titles[i]);
		}

		return workBook;
	}
}
