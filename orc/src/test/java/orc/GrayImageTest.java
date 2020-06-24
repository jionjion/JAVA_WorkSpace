package orc;

import java.awt.image.BufferedImage;

import org.junit.Test;

public class GrayImageTest {

	@Test
	public void test() throws Exception {
		
		String srcPath = "C:\\Users\\14345\\Desktop\\imageCode\\img26_Snn.png";
		
		String format = "png";
		
		String destPath = "C:\\Users\\14345\\Desktop\\imageCode\\img26_gray2";
		
		BufferedImage bufferedImage = ReadOrWriteImage.readImg(srcPath);
		// 处理
		bufferedImage = GrayImage.grayImage(bufferedImage);
		// 保存
		ReadOrWriteImage.writeImg(bufferedImage, format, destPath);
	}

}
