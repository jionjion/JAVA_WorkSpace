package helloExcel;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/** 打印区域 */
public class PrintArea {

	public static void printArea() throws Exception {
		//创建工作簿
		XSSFWorkbook workbook = new XSSFWorkbook();
		//创建工作页
		XSSFSheet spreadsheet = workbook.createSheet("Print Area");
		//创建打印区域
		workbook.setPrintArea(
			0, 							// 当前打印第几页
			0, 							// 开始列位置
			5, 							// 结束列位置
			0, 							// 开始行位置
			5 							// 结束行位置
		);
		// 设置纸张大小 A4纸
		spreadsheet.getPrintSetup().setPaperSize(XSSFPrintSetup.A4_PAPERSIZE);
		// 提示打印区域
		spreadsheet.setDisplayGridlines(true);
		// 设置边界线
		spreadsheet.setPrintGridlines(true);
		FileOutputStream out = new FileOutputStream(new File("打印文档.xlsx"));
		workbook.write(out);
		out.close();
		System.out.println("打印文档.xlsx 创建成功");
		workbook.close();
	}
	
	public static void main(String[] args) throws Exception {
		printArea();
	}
}
