package export.core;
/**
 * 	导出模板
 * @author JionJion
 */

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;



public class ExportExcel {

	private static List<Student> list = new ArrayList<Student>();
	
	/**类加载时初始化数据*/
	static {
		Student a = new Student(1, "Jion", 23, "男", new Date(), "读书,看报","Jion同学介绍");
		Student b = new Student(2, "Arise",22, "女", new Date(), "爬山,游泳","Arise同学介绍");
		Student c = new Student(3, "Tom",  23, "男", new Date(), "台球,网球","Tom同学介绍");
		Student d = new Student(4, "Mary", 23, "女", new Date(), "睡觉,吃饭","Mary同学介绍");
		Student e = new Student(5, "Wete", 23, "男", new Date(), "看剧,追漫","Wete同学介绍");
		list.add(a);
		list.add(b);
		list.add(c);
		list.add(d);
		list.add(e);
	}	
	
	/** 根据list中已有的对象信息和传入的类型规范,导出Excel文件*/
	public static void export(List<?> list , Class clazz , String fileName) throws Exception {
		
		//校验,list不为空
		if (list.isEmpty()) {
			System.err.println("集合为空!!");
			return;
		}
		
		//遍历,集合中元素是否存在,是否符合泛型要求
		for(int i=0 ; i<list.size() ; i++) {
			if (!(StringUtils.equals(list.get(i).getClass().getName(), clazz.getName()))) {
				System.err.println("集合混入了奇怪的元素!");
				return;
			}
		}
		
		//反射获得泛型类中定义的所有私有属性,作为导出字段
		Field[] fields = clazz.getDeclaredFields();
		List<Field> privateFieldList = new ArrayList<Field>();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			//获得私有属性
			if (field.getModifiers() == Modifier.PRIVATE) {
				privateFieldList.add(field);
			}
		}
		
		//将要导出的字段集合
		Set<Field> exportFieldSet = new LinkedHashSet<Field>();
		//遍历每个私有属性
		for(int i=0 ; i<privateFieldList.size() ; i++) {
			
			Field privateField = privateFieldList.get(i);
			
			//方法名 	getXxx ,注意单词字母大写
			String methodName = "get" + privateField.getName().substring(0, 1).toUpperCase()
										+ privateField.getName().substring(1);
			
			//反射调用get()方法,获得每个对象的值,当List中所有对象都不含有该属性值时,不作导出
			for(int j=0 ; j<list.size() ; j++) {
				Object object = list.get(j);
				Method getMethod = object.getClass().getMethod(methodName);
				Object result = getMethod.invoke(object);
				//不为空,放入导出集合
				if (!Objects.isNull(result)) {
					exportFieldSet.add(privateField);
				}
			}
		}
		
		//创建Excel
		HSSFWorkbook excel = new HSSFWorkbook();
		
		//创建sheet页
		HSSFSheet sheet = excel.createSheet(fileName);
		
		//初始行列坐标
		int rownum = 0 ;
		int colnum = 0 ;
		
		//设置宽度
		Iterator<Field> exportFieldWithIterator = exportFieldSet.iterator();
		if(exportFieldWithIterator.hasNext()) {
			//列宽
			int columnWith = Math.round(exportFieldWithIterator.next().getName().length() * 300F);
			sheet.setColumnWidth(colnum, columnWith);
			colnum ++;
		}
		
		//创建标题
		{
			//标题行列坐标
			rownum = 0 ;
			colnum = 0 ;		
			//标题单元格
			HSSFRow row = sheet.createRow(rownum);
			//创建单元格
			HSSFCell cell = row.createCell(colnum);
			
			//创建单元格样式
			HSSFCellStyle cellStyle = excel.createCellStyle();
			cellStyle.setAlignment(HorizontalAlignment.CENTER);
			
			//设置字体样式
			HSSFFont font = excel.createFont();
			font.setFontName("微软雅黑");
			font.setBold(true);
			font.setFontHeightInPoints((short)12);
			//将字体加入样式中
			cellStyle.setFont(font);		
			//合并居中		开始行 , 结束行 , 开始列 , 结束列
			sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, colnum, colnum + exportFieldSet.size()-1 ));
			//单元格设置样式
			cell.setCellStyle(cellStyle);
			cell.setCellValue(fileName);
		}
		//创建表头信息
		{
			//坐标
			rownum = 1 ;
			colnum = 0 ;
			//根据属性类型,确定数据列格式
			Iterator<Field> exportFieldFormatIterator = exportFieldSet.iterator();
			//创建当前行
			HSSFRow row = sheet.createRow(rownum);
			while(exportFieldFormatIterator.hasNext()) {
				
				//创建单元格
				HSSFCell cell = row.createCell(colnum);
				//创建单元格样式
				HSSFCellStyle cellStyle = excel.createCellStyle();
				cellStyle.setAlignment(HorizontalAlignment.CENTER);
				//数据字段
				Field exportField = exportFieldFormatIterator.next();
				cell.setCellValue(exportField.getName());
				cell.setCellStyle(cellStyle);
				colnum ++;
			}
		}		
		
		//创建数据
		{
			//坐标
			rownum = 2 ;
			colnum = 0 ;
			
			//遍历List数据,每个获得每个对象
			for(int j=0 ; j<list.size() ; j++) {
				
				//创建当前行
				HSSFRow row = sheet.createRow(rownum);
				colnum = 0;
				//数据对象
				Object object = list.get(j);
				Iterator<Field> exportFieldDataIterator = exportFieldSet.iterator();
				//对每一行数据,通过反射调用getXxx()方法,获得对象的值
				while(exportFieldDataIterator.hasNext()) {
					
					//创建单元格样式
					HSSFCellStyle cellStyle = excel.createCellStyle();
					//创建单元格数据格式化
					HSSFDataFormat format = excel.createDataFormat();
					
					//数据字段
					Field exportField = exportFieldDataIterator.next();							
					//方法名 	getXxx ,注意单词字母大写
					String methodName = "get" + exportField.getName().substring(0, 1).toUpperCase()
												+ exportField.getName().substring(1);					
					//反射调用get()方法,获得每个对象的值
					Method getMethod = object.getClass().getMethod(methodName);
					Object result = getMethod.invoke(object);
					
					//创建单元格,并根据类型赋值
					HSSFCell cell = row.createCell(colnum);					
					//类型判断,并格式化
					if(StringUtils.equals(exportField.getType().getSimpleName(), "Integer")) {
						//数字类型
						cell.setCellType(CellType.NUMERIC);
						//设置单元格样式格式化
						cellStyle.setDataFormat(format.getFormat("##00"));
						cell.setCellStyle(cellStyle);
						cell.setCellValue((Integer)result);
					}else if(StringUtils.equals(exportField.getType().getSimpleName(), "Date")) {
						//日期类型
						cell.setCellType(CellType.NUMERIC);
						cellStyle.setDataFormat(format.getFormat("yyyy-MM-dd"));	
						cell.setCellStyle(cellStyle);
						cell.setCellValue((Date)result);
					}else {
						//其他做文本处理
						cell.setCellType(CellType.STRING);
						cellStyle.setDataFormat(format.getFormat("@"));
						cell.setCellStyle(cellStyle);
						cell.setCellValue((String)result);
					}
					colnum ++;
				}
				rownum ++;
			}
		}
		
		/* 导出Excel */
		String exportPath = System.getProperty("user.dir") + "\\bin\\export\\template\\"+ fileName +".xls";
		File exportFile = new File(exportPath);
		//先删除,再创建
		exportFile.delete();	
		exportFile.createNewFile();
		FileOutputStream outputStream =FileUtils.openOutputStream(exportFile);
		//写入文件
		excel.write(outputStream);
		excel.close();
		
		System.out.println("文件路径>>>" + exportPath);
	}
	
	public static void main(String[] args) throws Exception {
		
		export(list, Student.class, "学生信息汇总");
	}
}
