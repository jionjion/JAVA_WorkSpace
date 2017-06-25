package helloExcel;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**单元格操作,存放数据类型和单元格样式*/
public class DataTypeOrCellStyle {

	/**存放不同类型数据单元格*/
	@SuppressWarnings("deprecation")
	public static void makeDateType() throws Exception{
		
		//创建工作表对象
		XSSFWorkbook workbook = new XSSFWorkbook();
		//创建工作页
		XSSFSheet spreadsheet = workbook.createSheet("数据页");
		//创建行对象	第三行
		XSSFRow row = spreadsheet.createRow((short) 2);
		//创建单元格	
		//字符类型
		row.createCell(0).setCellValue("字符型数值");
		row.createCell(1).setCellValue("String");
		//空白类型
		row = spreadsheet.createRow((short) 3);
		row.createCell(0).setCellValue("空白格");
		row.createCell(1);
		//布尔值类型
		row = spreadsheet.createRow((short) 4);
		row.createCell(0).setCellValue("布尔值");
		row.createCell(1).setCellValue(true);
		//错误格式类型
		row = spreadsheet.createRow((short) 5);
		row.createCell(0).setCellValue("设置错误格式");
		row.createCell(1).setCellValue(XSSFCell.CELL_TYPE_ERROR );	//源码为5,所以存入数字5
		//日期类型
		row = spreadsheet.createRow((short) 6);
		row.createCell(0).setCellValue("存放日期");
		row.createCell(1).setCellValue(new Date());
		//公式类型
		row = spreadsheet.createRow((short) 7);
		row.createCell(0).setCellValue("存放公式");
		row.createCell(1).setCellValue(20);
		FileOutputStream out = new FileOutputStream(
		new File("单元格数据类型.xlsx"));
		workbook.write(out);
		out.close();
		workbook.close();
		System.out.println("创建成功......");
	 }
	
	/**单元格样式*/
	@SuppressWarnings("deprecation")
	public static void makeCellStyle() throws Exception{
		//创建工作表
		XSSFWorkbook workbook = new XSSFWorkbook(); 
		//创建工作页
	    XSSFSheet spreadsheet = workbook.createSheet("工作页样式");
	    //创建行元素
	    XSSFRow row = spreadsheet.createRow((short) 1);
	    //设置行高
	    row.setHeight((short) 800);
	    //设置单元格
	    XSSFCell cell = (XSSFCell) row.createCell((short) 1);
	    cell.setCellValue("合并单元格样式");
	    //合并单元格
	    spreadsheet.addMergedRegion(new CellRangeAddress(
	    1, //开始行,第二行
	    1, //最后行,第二行
	    1, //开始列,第二列
	    4 //结束列,第五列
	    ));
	    /*设置第一种样式对象*/
	    row = spreadsheet.createRow(5); 
	    cell = (XSSFCell) row.createCell(0);
	    //设置行高
	    row.setHeight((short) 800);
	    //单元格样式由工作表对象创建
	    XSSFCellStyle style1 = workbook.createCellStyle();
	    //设置单元格宽度
	    spreadsheet.setColumnWidth(0, 8000);
	    //设置水平对齐方式,左对齐
	    style1.setAlignment(XSSFCellStyle.ALIGN_LEFT);
	    //设置垂直对齐方式,顶对齐
	    style1.setVerticalAlignment(XSSFCellStyle.VERTICAL_TOP);
	    cell.setCellValue("水平居左,垂直居上");
	    //对单元格设置样式
	    cell.setCellStyle(style1);
	    
	    /*设置第二种样式对象*/
	    row = spreadsheet.createRow(6); 
	    cell = (XSSFCell) row.createCell(1);
	    row.setHeight((short) 800);
	    XSSFCellStyle style2 = workbook.createCellStyle();
	    //水平对齐方式,居中对齐
	    style2.setAlignment(XSSFCellStyle.ALIGN_CENTER);
	    //垂直对齐方式,居中对齐
	    style2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
	    cell.setCellValue("水平居中,垂直居中"); 
	    //设置样式
	    cell.setCellStyle(style2);
	    
	    /*设置第三种样式对象*/
	    row = spreadsheet.createRow(7); 
	    cell = (XSSFCell) row.createCell(2);
	    row.setHeight((short) 800);
	    XSSFCellStyle style3 = workbook.createCellStyle();
	    //水平对齐,右对齐
	    style3.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
	    //垂直对齐,底对齐
	    style3.setVerticalAlignment(XSSFCellStyle.VERTICAL_BOTTOM);
	    cell.setCellValue("水平居右,垂直局低");
	    cell.setCellStyle(style3);
	    
	    /*设置第三种样式对象*/
	    row = spreadsheet.createRow(8);
	    cell = (XSSFCell) row.createCell(3);
	    XSSFCellStyle style4 = workbook.createCellStyle();
	    //水平对齐方式,文本自动调整,分散对齐
	    style4.setAlignment(XSSFCellStyle.ALIGN_JUSTIFY);
	    //垂直对齐方式,文本自动调整,分散对齐
	    style4.setVerticalAlignment(XSSFCellStyle.VERTICAL_JUSTIFY);
	    cell.setCellValue("文字不超过表格范围,自动换行,超出后自动填充行高"); 
	    cell.setCellStyle(style4);
	    
	    /*创建第四中样式对象*/
	    row = spreadsheet.createRow((short) 10);
	    row.setHeight((short) 800);
	    cell = (XSSFCell) row.createCell((short) 1);
	    cell.setCellValue("单元格边框");
	    XSSFCellStyle style5 = workbook.createCellStyle();
	    //设置底边框样式,粗边框
	    style5.setBorderBottom(XSSFCellStyle.BORDER_THICK);
	    //设置底边框颜色,传入颜色对应的索引颜色
	    style5.setBottomBorderColor(IndexedColors.BLUE.getIndex());
	    //设置左边框样式,双边框
	    style5.setBorderLeft(XSSFCellStyle.BORDER_DOUBLE);
	    //设置左边框颜色
	    style5.setLeftBorderColor(IndexedColors.GREEN.getIndex());
	    //设置右边框样式,虚线样式
	    style5.setBorderRight(XSSFCellStyle.BORDER_HAIR);
	    style5.setRightBorderColor(IndexedColors.RED.getIndex());
	    //设置顶边框样式,点划线样式
	    style5.setBorderTop(XSSFCellStyle.BIG_SPOTS);
	    style5.setTopBorderColor(IndexedColors.CORAL.getIndex());
	    //设置单元格样式
	    cell.setCellStyle(style5);
	    
	    /*创建第五中样式对象*/
	    row = spreadsheet.createRow((short) 10 );
	    cell = (XSSFCell) row.createCell((short) 1);
	    XSSFCellStyle style6 = workbook.createCellStyle();
	    //设置单元格背景颜色
	    style6.setFillBackgroundColor(HSSFColor.LEMON_CHIFFON.index);
	    //设置单元格背景颜色填充模式,栅点填充颜色
	    style6.setFillPattern(XSSFCellStyle.LESS_DOTS);
	    //设置水平对齐方式,填充
	    style6.setAlignment(XSSFCellStyle.ALIGN_FILL);
	    //设置宽度.第二列,宽度8000px.每一个字为256px
	    spreadsheet.setColumnWidth(1,8000);	
	    cell.setCellValue("背景颜色");
	    cell.setCellStyle(style6);
	    /*前景色填充*/
	    row = spreadsheet.createRow((short) 12);
	    cell = (XSSFCell) row.createCell((short) 1);
	    XSSFCellStyle style7=workbook.createCellStyle();
	    //前景色填充,主要使用前景色填充
	    style7.setFillForegroundColor(HSSFColor.BLUE.index);
	    //填充模式,栅点填充颜色
	    style7.setFillPattern( XSSFCellStyle.LESS_DOTS);
	    //对齐方式,填充
	    style7.setAlignment(XSSFCellStyle.ALIGN_FILL);
	    cell.setCellValue("前景颜色");
	    cell.setCellStyle(style7);
	    FileOutputStream out = new FileOutputStream(
	    							new File("单元格样式.xlsx"));
	    workbook.write(out);
	    out.close();
	    workbook.close();
	    System.out.println("单元格样式类型创建成功.....");
	}
	
	
	public static void main(String[] args) throws Exception{
		makeDateType();
		makeCellStyle();
	}
}
