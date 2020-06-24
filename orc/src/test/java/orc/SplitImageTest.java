package orc;

import java.awt.image.BufferedImage;
import java.util.List;

import org.junit.Test;

public class SplitImageTest {

	@Test
	public void test() throws Exception {
		
		String srcPath = "C:\\Users\\14345\\Desktop\\imageCode\\img26_binary.png";
		
		String format = "png";
		
		String destPath = "C:\\Users\\14345\\Desktop\\imageCode\\img26_split";
		
		BufferedImage bufferedImage = ReadOrWriteImage.readImg(srcPath);
		// 处理
		List<BufferedImage> lists = SplitImage.splitImageByFixed(bufferedImage);
		// 保存
		for (int i = 0; i < lists.size(); i++) {
			ReadOrWriteImage.writeImg(lists.get(i), format, destPath + '_' + i);
		}
	}

}
