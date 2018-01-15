package mvcdemo.service.imp;

import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import mvcdemo.service.WaterMarkService;

@Service("waterMarkService")
public class WaterMarkServiceIpm implements WaterMarkService {

	@Override
	public String uploadImage(InputStream image,String imageName,String uploadPath,String realUploadPath) throws IOException {
		
		FileUtils.copyToFile(image, new File(realUploadPath + '/' + imageName));
		
		return uploadPath + '/' + imageName;
	}

	@Override
	public String markTextImage(InputStream image, String imageName, String uploadPath, String realUploadPath) throws IOException {
		
		//获得原图形的信息
		Image srcImage = ImageIO.read(image);
		int src_width = srcImage.getWidth(null);
		int src_height = srcImage.getHeight(null);
		
		//设置图片宽高,显示模式(3位RGB),在缓存中生成图片对象
		BufferedImage bufferedImage = new BufferedImage(src_width, src_height, BufferedImage.TYPE_3BYTE_BGR);
		
		//根据图片对象生成绘图工具
		Graphics2D graphics2d = bufferedImage.createGraphics();
		
		//绘制原图像,坐标,高宽,预加载设备
		graphics2d.drawImage(srcImage, 0, 0, src_width, src_height, null);
		
		//设置字体效果
		Font font = new Font(FONT_NAME, FONT_STYLE, FONT_SIZE);
		graphics2d.setFont(font);
		graphics2d.setColor(FONT_COLOR);
		
		//水印高度,宽度
		int mark_height = FONT_SIZE;
		int mark_width = FONT_SIZE * getTextLength(MARK_TEXT);
		
		//原图水印相差
		int diff_height = src_height - mark_height;
		int diff_width = src_width - mark_width;
		
		//水印的位置
		int x = X;
		int y = Y;
		
		//如果水印的起始位置大于水平的差值,则取两者的最大差值.
		if (x>diff_width) {
			x = diff_width;
		}
		
		//如果水印的结束位置大于垂直的差值,则取两者的最大差值.
		if (y>diff_height) {
			y = diff_height;
		}
		
		//透明度设置,和合成规则
		graphics2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, ALPHA));
		
		//绘制,调整字体高度
		graphics2d.drawString(MARK_TEXT, x, y + FONT_SIZE);
	
		//关闭工具
		graphics2d.dispose();
		
		//设置图片输出
		String mark_imageName =  "mark_"+ imageName;
		OutputStream outputStream = new FileOutputStream(realUploadPath + '/' + mark_imageName);
		
		//设置图片编码
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(outputStream);

		//将图片从内存中写入磁盘
		encoder.encode(bufferedImage);
		
		outputStream.close();
		
		//返回图片访问位置
		return uploadPath + "/" + mark_imageName;
	}
	/** 传入水印文本,获得文字的长度,使其小于上传的图片,放置于图片右下角	<br>
	 * 	字体宽度为设置的字体大小,而本文长度则根据中英文而定.两个英文等于一个字符长度*/
	private int getTextLength(String text) {
		//转为英文长度
		int length = text.length();
		for(int i=0 ; i<text.length() ; i++) {
			//获得每一位的字符串
			String str = String.valueOf(text.charAt(i));
			//将字符转为byte数组,如果为中文,1
			if(str.getBytes().length > 1) {
				//对于中文,长度加一
				length ++;
			}
		}
		//折断处理,将半角转为全角长度.
		length = (length%2==0 ? length/2 : length/2+1);
		return length;
	}
	
	@Override
	public String markLogoImage(InputStream image, String imageName, String uploadPath, String realUploadPath)
			throws IOException {
		//获得原图形的信息
		Image srcImage = ImageIO.read(image);
		int src_width = srcImage.getWidth(null);
		int src_height = srcImage.getHeight(null);
		
		//设置图片宽高,显示模式(3位RGB),在缓存中生成图片对象
		BufferedImage bufferedImage = new BufferedImage(src_width, src_height, BufferedImage.TYPE_3BYTE_BGR);
		
		//根据图片对象生成绘图工具
		Graphics2D graphics2d = bufferedImage.createGraphics();
		
		//绘制原图像,坐标,高宽,预加载设备
		graphics2d.drawImage(srcImage, 0, 0, src_width, src_height, null);
	
		//水印图片
		String logoPath = LOGO;
		File logo = new File(logoPath);
		Image imageLogo = ImageIO.read(logo);
		
		//水印的宽高
		int mark_width = imageLogo.getWidth(null);
		int mark_height = imageLogo.getHeight(null);

		//原图水印相差
		int diff_height = src_height - mark_height;
		int diff_width = src_width - mark_width;
		
		//水印的位置
		int x = X;
		int y = Y;
		
		//如果水印的起始位置大于水平的差值,则取两者的最大差值.
		if (x>diff_width) {
			x = diff_width;
		}
		
		//如果水印的结束位置大于垂直的差值,则取两者的最大差值.
		if (y>diff_height) {
			y = diff_height;
		}
		
		//透明度设置,和合成规则
		graphics2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, ALPHA));
	
		//绘制图片水印
		graphics2d.drawImage(imageLogo, x, y, null);
		
		//关闭工具
		graphics2d.dispose();
		
		//设置图片输出
		String mark_imageName =  "mark_"+ imageName;
		OutputStream outputStream = new FileOutputStream(realUploadPath + '/' + mark_imageName);
		
		//设置图片编码
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(outputStream);

		//将图片从内存中写入磁盘
		encoder.encode(bufferedImage);
		
		outputStream.close();
		
		//返回图片访问位置
		return uploadPath + "/" + mark_imageName;

	}
	
	@Override
	public String markTextsImage(InputStream image, String imageName, String uploadPath, String realUploadPath) throws IOException {
		
		//获得原图形的信息
		Image srcImage = ImageIO.read(image);
		int src_width = srcImage.getWidth(null);
		int src_height = srcImage.getHeight(null);
		
		//设置图片宽高,显示模式(3位RGB),在缓存中生成图片对象
		BufferedImage bufferedImage = new BufferedImage(src_width, src_height, BufferedImage.TYPE_3BYTE_BGR);
		
		//根据图片对象生成绘图工具
		Graphics2D graphics2d = bufferedImage.createGraphics();
		
		//绘制原图像,坐标,高宽,预加载设备
		graphics2d.drawImage(srcImage, 0, 0, src_width, src_height, null);
		
		//设置字体效果
		Font font = new Font(FONT_NAME, FONT_STYLE, FONT_SIZE);
		graphics2d.setFont(font);
		graphics2d.setColor(FONT_COLOR);
		
		//水印高度,宽度
		int mark_height = FONT_SIZE;
		int mark_width = FONT_SIZE * getTextLength(MARK_TEXT);		
		
		//透明度设置,和合成规则
		graphics2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, ALPHA));
		
		//旋转图片 , 弧度,图片中心
		graphics2d.rotate(Math.toRadians(30), bufferedImage.getWidth()/2, bufferedImage.getHeight()/2);
		
		//水印开始位置.自定义的,便于水印旋转后均匀铺开
		int x = -src_width/2;
		int y = -src_height/2;
		
		//循环,均匀地铺开水印
		while (x<src_width*1.5) {
			
			y = -src_height/2;		//初始y轴,从上往下添加
			while(y<src_height*1.5) {
				//绘制
				graphics2d.drawString(MARK_TEXT, x, y);
				
				y += mark_height + 150;		//纵向铺开,间隔150
			}
			x += mark_width + 300; 	//横向铺开,间隔300
		}
		
		
	
		//关闭工具
		graphics2d.dispose();
		
		//设置图片输出
		String mark_imageName =  "mark_"+ imageName;
		OutputStream outputStream = new FileOutputStream(realUploadPath + '/' + mark_imageName);
		
		//设置图片编码
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(outputStream);

		//将图片从内存中写入磁盘
		encoder.encode(bufferedImage);
		
		outputStream.close();
		
		//返回图片访问位置
		return uploadPath + "/" + mark_imageName;
	}
	
	@Override
	public String markLogosImage(InputStream image, String imageName, String uploadPath, String realUploadPath) throws IOException {
		
		//获得原图形的信息
		Image srcImage = ImageIO.read(image);
		int src_width = srcImage.getWidth(null);
		int src_height = srcImage.getHeight(null);
		
		//设置图片宽高,显示模式(3位RGB),在缓存中生成图片对象
		BufferedImage bufferedImage = new BufferedImage(src_width, src_height, BufferedImage.TYPE_3BYTE_BGR);
		
		//根据图片对象生成绘图工具
		Graphics2D graphics2d = bufferedImage.createGraphics();
		
		//绘制原图像,坐标,高宽,预加载设备
		graphics2d.drawImage(srcImage, 0, 0, src_width, src_height, null);
		
		String logoPath = LOGO;
		
		File logo = new File(logoPath);
		
		Image logoImage = ImageIO.read(logo);
		
		//图片宽高
		int mark_width = logoImage.getWidth(null);
		int mark_height = logoImage.getHeight(null);
		
		
		//透明度设置,和合成规则
		graphics2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, ALPHA));
		
		//旋转图片 , 弧度,图片中心
		graphics2d.rotate(Math.toRadians(30), bufferedImage.getWidth()/2, bufferedImage.getHeight()/2);
		
		//水印开始位置.自定义的,便于水印旋转后均匀铺开
		int x = -src_width/2;
		int y = -src_height/2;
		
		//循环,均匀地铺开水印
		while (x<src_width*1.5) {
			
			y = -src_height/2;		//初始y轴,从上往下添加
			while(y<src_height*1.5) {
				//绘制
				graphics2d.drawImage(logoImage, x, y, null);
				
				y += mark_height + 150;		//纵向铺开,间隔150
			}
			x += mark_width + 300; 	//横向铺开,间隔300
		}
		
		
	
		//关闭工具
		graphics2d.dispose();
		
		//设置图片输出
		String mark_imageName =  "mark_"+ imageName;
		OutputStream outputStream = new FileOutputStream(realUploadPath + '/' + mark_imageName);
		
		//设置图片编码
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(outputStream);

		//将图片从内存中写入磁盘
		encoder.encode(bufferedImage);
		
		outputStream.close();
		
		//返回图片访问位置
		return uploadPath + "/" + mark_imageName;
	}	
}
