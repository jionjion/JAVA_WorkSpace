package helloExcel;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**创建公式*/
public class MakeFormula {

	/**输出公式*/
	@SuppressWarnings("deprecation")
	public static void makeFormula() throws Exception{
		//创建工作薄对象
		XSSFWorkbook workbook = new XSSFWorkbook();
		//创建工作页对象
	    XSSFSheet spreadsheet = workbook.createSheet("公式页");
	    //创建单元行
	    XSSFRow row = spreadsheet.createRow(1);
	    //创建单元格
	    XSSFCell cell = row.createCell(1);
	    cell.setCellValue("A =" );				//表达式
	    cell = row.createCell(2);				
	    cell.setCellValue(2);					//数值
	    row = spreadsheet.createRow(2);
	    cell = row.createCell(1);
	    cell.setCellValue("B =");				//表达式
	    cell = row.createCell(2);
	    cell.setCellValue(4);
	    row = spreadsheet.createRow(3);
	    cell = row.createCell(1);
	    cell.setCellValue("Total =");
	    cell = row.createCell(2);
	    // 创建公式,首先设置单元格格式为2,表示公式
	    cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
	    // 公式内容
	    cell.setCellFormula("SUM(C2:C3)" );
	    cell = row.createCell(3);
	    cell.setCellValue("SUM(C2:C3)");
	    row = spreadsheet.createRow(4);
	    cell = row.createCell(1);
	    cell.setCellValue("POWER =");
	    cell=row.createCell(2);
	    // 创建立方公式
	    cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
	    cell.setCellFormula("POWER(C2,C3)");
	    cell = row.createCell(3);
	    cell.setCellValue("POWER(C2,C3)");
	    row = spreadsheet.createRow(5);
	    cell = row.createCell(1);
	    cell.setCellValue("MAX =");
	    cell = row.createCell(2);
	    // 创建求最大值公式
	    cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
	    cell.setCellFormula("MAX(C2,C3)");
	    cell = row.createCell(3);
	    cell.setCellValue("MAX(C2,C3)");
	    row = spreadsheet.createRow(6);
	    cell = row.createCell(1);
	    cell.setCellValue("FACT =");
	    cell = row.createCell(2);
	    // 创建阶乘函数
	    cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
	    cell.setCellFormula("FACT(C3)");
	    cell = row.createCell(3);
	    cell.setCellValue("FACT(C3)");
	    row = spreadsheet.createRow(7);
	    cell = row.createCell(1);
	    cell.setCellValue("SQRT =");
	    cell = row.createCell(2);
	    // 创建开方公式
	    cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
	    cell.setCellFormula("SQRT(C5)");
	    cell = row.createCell(3);
	    cell.setCellValue("SQRT(C5)");
	    workbook.getCreationHelper()
	    .createFormulaEvaluator()
	    .evaluateAll();
	    FileOutputStream out = new FileOutputStream(
	    new File("公式.xlsx"));
	    workbook.write(out);
	    out.close();
	    workbook.close();
	     System.out.println("公式.xlsx 创建成功.....");
	}
	
	public static void main(String[] args) throws Exception{
		makeFormula();
	}
}
