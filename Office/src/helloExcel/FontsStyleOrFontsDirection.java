package helloExcel;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**字体样式和风格的实现*/
public class FontsStyleOrFontsDirection {

	/**字体风格*/
	@SuppressWarnings("deprecation")
	public static void fontsStyle() throws Exception{
      
		//创建工作表
		XSSFWorkbook workbook = new XSSFWorkbook();
		//创建工作页
		XSSFSheet spreadsheet = workbook.createSheet("字体样式页");
		//创建行
		XSSFRow row = spreadsheet.createRow(2);
		//创建字体样式对象.由工作表对象创建
		XSSFFont font = workbook.createFont();
		//设置字体大小
		font.setFontHeightInPoints((short) 30);
		//设置字体样式名
		font.setFontName("华文行楷");
		//设置字体族
		font.setFamily(1);
		//设置是否斜体,传入布尔值,缺省为true.
		font.setItalic(true);
		//设置是否加粗,传入布尔值,缺省为true.
		font.setBold(true);
		//设置底线数量,1为下划线,2为双下划线
		font.setUnderline((byte)1);
		//设置字体颜色
		font.setColor(HSSFColor.BRIGHT_GREEN.index);
		//创建单元格样式.由工作表对象创建
		XSSFCellStyle style = workbook.createCellStyle();
		//将字体样式装载进入单元格样式中
		style.setFont(font);
		//创建单元格,应用样式
		XSSFCell cell = row.createCell(1);
		cell.setCellValue("字体样式");
		//传入单元格样式到单元格中,完成字体样式和单元格样式的设定
		cell.setCellStyle(style);
		//文件输出
		FileOutputStream out = new FileOutputStream(
									new File("字体样式.xlsx"));
		workbook.write(out);
		out.close();
		workbook.close();
		System.out.println("字体样式.xlsx文件创建成功.....");	
	}
	
	/**字体方向规则*/
	public static void fontsDirection() throws Exception{
		//创建工作表对象
		XSSFWorkbook workbook = new XSSFWorkbook();
		//创建工作页对象
	    XSSFSheet spreadsheet = workbook.createSheet("字体方向页");
	    //创建行,第三行
	    XSSFRow row = spreadsheet.createRow(2);
	    //创建单元格样式,由工作表对象创建
	    XSSFCellStyle style = workbook.createCellStyle();
	    //设置单元格内文字的旋转角度
	    style.setRotation((short) 0);
	    //创建单元格
	    XSSFCell cell = row.createCell(1);
	    cell.setCellValue("0°角度旋转");
	    cell.setCellStyle(style);
	    //再次创建一个逆时针80°的旋转
	    style=workbook.createCellStyle();
	    style.setRotation((short) 80);
	    cell = row.createCell(3);
	    cell.setCellValue("80°角度旋转");
	    cell.setCellStyle(style);
	    //在创建个顺时针80°的旋转
	    style = workbook.createCellStyle();
	    style.setRotation((short) -80);
	    cell = row.createCell(5);
	    cell.setCellValue("-80°角度旋转");
	    cell.setCellStyle(style);
	    FileOutputStream out = new FileOutputStream(
	  		  new File("字体旋转.xlsx"));
	    workbook.write(out);
	    out.close();
	    workbook.close();
	    System.out.println( "字体旋转.xlsx文件创建成功......");
	}
	
	public static void main(String[] args) throws Exception {
		fontsStyle();
		
		fontsDirection();
	}
}
