package orc;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;

import org.junit.Test;

public class DenoiseTest {

	@Test
	public void testDenoise() {
		
		String srcPath = "C:\\Users\\14345\\Desktop\\imageCode\\gray\\img01_gray.png";
		
		String format = "png";
		
		String destPath = "C:\\Users\\14345\\Desktop\\imageCode\\denoise\\img03_denoise";
		
		BufferedImage bufferedImage = ReadOrWriteImage.readImg(srcPath);
		// 处理
		bufferedImage = Denoise.denoise(bufferedImage);
		// 保存
		ReadOrWriteImage.writeImg(bufferedImage, format, destPath);
	}

}
