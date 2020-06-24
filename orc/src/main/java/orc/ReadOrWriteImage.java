package orc;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ReadOrWriteImage {

	
	/** 读取图片 */
	public static BufferedImage readImg(String srcPath) {
		try {
			File file = new File(srcPath);
			BufferedImage image = ImageIO.read(file);
			return image;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/** 读取图片 */
	public static BufferedImage readImg(File file) {
		try {
			BufferedImage image = ImageIO.read(file);
			return image;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}	
	
	/** 读取图片,从IO流中 */
	public static BufferedImage readImg(InputStream input) {
		try {
			BufferedImage image = ImageIO.read(input);
			return image;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/** 写入图片 */
	public static void writeImg(BufferedImage img, String format, String destPath) {
		try {
			// 路径
			File outputfile = new File(destPath + '.' + format);
			ImageIO.write(img, format, outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
