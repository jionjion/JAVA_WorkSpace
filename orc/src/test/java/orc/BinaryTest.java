package orc;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.junit.Test;

public class BinaryTest {

	@Test
	public void testBinary() throws IOException {
		
		String srcPath = "C:\\Users\\14345\\Desktop\\imageCode\\img26_gray2.png";
		
		String format = "png";
		
		String destPath = "C:\\Users\\14345\\Desktop\\imageCode\\img26_binary";
		
		BufferedImage bufferedImage = ReadOrWriteImage.readImg(srcPath);
		// 处理
		bufferedImage = Binary.binary(bufferedImage);
		// 保存
		ReadOrWriteImage.writeImg(bufferedImage, format, destPath);
	}

}
