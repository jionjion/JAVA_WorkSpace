package orc;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class Reader {

	
	public static String read(BufferedImage bufferedImage) throws TesseractException {
		
       Tesseract tessreact = new Tesseract();  
       tessreact.setDatapath("F:\\JAVA_WorkSpace\\orc\\src\\main\\resources\\tessdata");
       tessreact.setLanguage("eng");
       
       String result = tessreact.doOCR(bufferedImage);  
       return result.replaceAll("\r|\n|\\s", "");
	}
	
	public static String read(String srcPath) throws Exception {
		
		
		// 原图
		BufferedImage bufferedImage = ReadOrWriteImage.readImg(srcPath);
		
		// 灰度
		bufferedImage = GrayImage.grayImage(bufferedImage);
		
		// 对称近邻均值滤波
		bufferedImage = SnnFiltering.snnFiltering(bufferedImage);

		// 灰度
		bufferedImage = GrayImage.grayImage(bufferedImage);
		
		// 二值化
		bufferedImage = Binary.binary(bufferedImage);
		
		// 拆分
		List<BufferedImage> lists = SplitImage.splitImageByFixed(bufferedImage);
		
		// 识别
		StringBuffer result = new StringBuffer();
		for (BufferedImage img : lists) {
			result.append(Reader.read(bufferedImage));
		}
		return result.toString().replaceAll("\r|\n", "");
	}


	public static void main(String[] args) {
		
	    File imageFile = new File("C:\\Users\\14345\\Desktop\\TIM图片20190505093938.png");  
	       Tesseract tessreact = new Tesseract();  
	       tessreact.setDatapath("F:\\JAVA_WorkSpace\\orc\\src\\main\\resources\\tessdata");
	       tessreact.setLanguage("eng");
	       try {
	           String result = tessreact.doOCR(imageFile);  
	           System.out.println(result);  
	       } catch (TesseractException e) {  
	           System.err.println(e.getMessage());  
	       }
	}
	
	
	
}


