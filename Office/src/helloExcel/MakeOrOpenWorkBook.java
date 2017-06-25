package helloExcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**测试Excel导出到本地*/
public class MakeOrOpenWorkBook {

	/**创建工作表*/
	public static void makeNewWorkbook() throws IOException {
		
		/*创建工作表部分*/
		//创建空白工作表
		XSSFWorkbook workbook = new XSSFWorkbook(); 
		/*创建sheet页部分*/
		XSSFSheet spreadsheet = workbook.createSheet("员工信息");
		/*创建单元行部分*/
		XSSFRow row = spreadsheet.createRow(0);
		/*创建单元格,写入数据 */
		XSSFCell cell = row.createCell(0);
		cell.setCellValue("员工编号");
		cell = row.createCell(1);
		cell.setCellValue("员工姓名");
		/*创建第二行*/
		row = spreadsheet.createRow(1);
		/*创建单元格,写入数据 */
		cell = row.createCell(0);
		cell.setCellValue("001");
		cell = row.createCell(1);
		cell.setCellValue("张三");
		
		/*创建输出流,用于输出文件*/
		FileOutputStream out = new FileOutputStream(
			new File("F:\\JAVA_WorkSpace\\Office\\程序自动创建.xlsx"));
		//工作表输出到文件中.
		workbook.write(out);
		out.close();
		//工作表关闭
		workbook.close();
		}
	
	/**打开工作表
	 * @throws IOException */
	@SuppressWarnings("deprecation")
	public static void openNewWorkbook() throws IOException {
	  
		/*打开工作表部分*/
		File file = new File("F:\\JAVA_WorkSpace\\Office\\程序自动读取.xlsx");
		FileInputStream fileInputStream = new FileInputStream(file);
		//根据获取文件创建为工作表
		XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
		if(file.isFile() && file.exists()){	//如果file文件存在,则打开成功
			System.out.println("打开工作表成功");
		} else {
			System.out.println("打开工作表失败");
		}
		/*打开工作页*/
		XSSFSheet spreadsheet = workbook.getSheetAt(0);
		/*获得行迭代器*/
		Iterator<Row> rowIterator = spreadsheet.iterator();
		while (rowIterator.hasNext()) {
			/*获得每行*/
			XSSFRow row = (XSSFRow) rowIterator.next();
			/*获得每行单元迭代器*/
			 Iterator < Cell > cellIterator = row.cellIterator();
			 while(cellIterator.hasNext()){
				 /*获得每个单元格*/
				 Cell cell= cellIterator.next();
				 switch (cell.getCellType()) 			//判断获取的单元格元素的类型	0:数字  1:字符串  2:公式  3:空  4:布尔值  5:错误
		            {
		               case Cell.CELL_TYPE_NUMERIC:		//数字类型
		               System.out.print( 
		               cell.getNumericCellValue() + " \t\t " );		
		               break;
		               case Cell.CELL_TYPE_STRING:		//字符串类型
		               System.out.print(
		               cell.getStringCellValue() + " \t\t " );
		               break;
		            }
			 }
			
		}
		fileInputStream.close();
		workbook.close();
	}
	
	public static void main(String[] args) throws Exception {
		
		//创建
		makeNewWorkbook();
		//读取
		openNewWorkbook();
	}
	
}
