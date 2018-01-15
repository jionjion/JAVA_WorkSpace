package mvcdemo.service;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;

public interface WaterMarkService {
	
	//内容
	public static final String MARK_TEXT = "Jion";
	//字体
	public static final String FONT_NAME = "宋体";
	//字形
	public static final int FONT_STYLE = Font.BOLD;
	//字号
	public static final int FONT_SIZE = 60;
	//颜色
	public static final Color FONT_COLOR = Color.BLUE;
	
	//位置
	public static final int X = 20;
	public static final int Y = 20;
	
	//透明化
	public static float ALPHA = 0.3F;
	
	//图片水印
	public static final String LOGO = "F:\\JAVA_WorkSpace\\SpringMVC\\src\\main\\webapp\\resource\\logo.png";
	
	/**
	 * 	拷贝文件信息
	 * @param image			图片文件IO
	 * @param imageName		图片名字
	 * @param uploadPath	相对路径,上传时传入信息
	 * @param realUploadPath 绝对路径,文件磁盘位置信息
	 * @return				图片相对路径地址,用于页面显示	
	 * @throws IOException 
	 */
	public String uploadImage(InputStream image,String imageName,String uploadPath,String realUploadPath) throws IOException;
	
	/**
	 * 	为图片增加文字水印
	 * @param image			图片文件IO
	 * @param imageName		图片名字
	 * @param uploadPath	相对路径,上传时传入信息
	 * @param realUploadPath 绝对路径,文件磁盘位置信息
	 * @return				图片相对路径地址,用于页面显示	
	 * @throws IOException 
	 */
	public String markTextImage(InputStream image,String imageName,String uploadPath,String realUploadPath) throws IOException;
	
	/**
	 * 	为图片增加图片水印
	 * @param image			图片文件IO
	 * @param imageName		图片名字
	 * @param uploadPath	相对路径,上传时传入信息
	 * @param realUploadPath 绝对路径,文件磁盘位置信息
	 * @return				图片相对路径地址,用于页面显示	
	 * @throws IOException 
	 */
	public String markLogoImage(InputStream image,String imageName,String uploadPath,String realUploadPath) throws IOException;
	
	/**
	 * 	为图片增加文字水印
	 * @param image			图片文件IO
	 * @param imageName		图片名字
	 * @param uploadPath	相对路径,上传时传入信息
	 * @param realUploadPath 绝对路径,文件磁盘位置信息
	 * @return				图片相对路径地址,用于页面显示	
	 * @throws IOException 
	 */
	public String markTextsImage(InputStream image,String imageName,String uploadPath,String realUploadPath) throws IOException;
	
	/**
	 * 	为图片增加多个图片水印
	 * @param image			图片文件IO
	 * @param imageName		图片名字
	 * @param uploadPath	相对路径,上传时传入信息
	 * @param realUploadPath 绝对路径,文件磁盘位置信息
	 * @return				图片相对路径地址,用于页面显示	
	 * @throws IOException 
	 */
	public String markLogosImage(InputStream image,String imageName,String uploadPath,String realUploadPath) throws IOException;	
	
}
