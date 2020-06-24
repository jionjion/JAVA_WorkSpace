package orc;

import java.awt.image.BufferedImage;
import java.util.List;

public class Demo2 {

	
	public static void main(String[] args) throws Exception {
		
		// 识别图片
		String srcPath = "C:\\Users\\14345\\Desktop\\TIM图片20190505093938.png";
		
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
//		for (BufferedImage img : lists) {
			result.append(Reader.read(bufferedImage));
//		}
		System.out.println("辨识结果:" +  result);
	}
}
