package orc;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 14345 图片分割
 */
public class SplitImage {

	public static List<BufferedImage> splitImage(BufferedImage bufferedImage) throws Exception {

		List<BufferedImage> subImgs = new ArrayList<BufferedImage>();
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();
		List<Integer> weightlist = new ArrayList<Integer>();
		for (int x = 0; x < width; ++x) {
			int count = 0;
			for (int y = 0; y < height; ++y) {
				if (isBlack(bufferedImage.getRGB(x, y)) == 1) {
					count++;
				}
			}
			weightlist.add(count);
		}
		for (int i = 0; i < weightlist.size();) {
			int length = 0;
			while (weightlist.get(i++) > 1) {
				length++;
			}
			if (length > 12) {
				subImgs.add(removeBlank(bufferedImage.getSubimage(i - length - 1, 0, length / 2, height)));
				subImgs.add(removeBlank(bufferedImage.getSubimage(i - length / 2 - 1, 0, length / 2, height)));
			} else if (length > 3) {
				subImgs.add(removeBlank(bufferedImage.getSubimage(i - length - 1, 0, length, height)));
			}
		}
		return subImgs;
	}

	public static int isBlack(int colorInt) {
		Color color = new Color(colorInt);
		if (color.getRed() + color.getGreen() + color.getBlue() <= 100) {
			return 1;
		}
		return 0;
	}

	public static int isWhite(int colorInt) {
		Color color = new Color(colorInt);
		if (color.getRed() + color.getGreen() + color.getBlue() > 100) {
			return 1;
		}
		return 0;
	}

	public static BufferedImage removeBlank(BufferedImage img) throws Exception {
		int width = img.getWidth();
		int height = img.getHeight();
		int start = 0;
		int end = 0;
		Label1: for (int y = 0; y < height; ++y) {
			int count = 0;
			for (int x = 0; x < width; ++x) {
				if (isBlack(img.getRGB(x, y)) == 1) {
					count++;
				}
				if (count >= 1) {
					start = y;
					break Label1;
				}
			}
		}
		Label2: for (int y = height - 1; y >= 0; --y) {
			int count = 0;
			for (int x = 0; x < width; ++x) {
				if (isBlack(img.getRGB(x, y)) == 1) {
					count++;
				}
				if (count >= 1) {
					end = y;
					break Label2;
				}
			}
		}
		return img.getSubimage(0, start, width, end - start + 1);
	}


	/** 固定位置分割 */
	public static List<BufferedImage> splitImageByFixed(BufferedImage bufferedImage) throws Exception{
		// 分割宽度
		final int DIGIT_WIDTH = 15;
        // 分割高度
        final int DIGIT_HEIGHT = 20;
        
        List<BufferedImage> digitImageList = new ArrayList<BufferedImage>();
        digitImageList.add(bufferedImage.getSubimage(0 * DIGIT_WIDTH, 0, DIGIT_WIDTH, DIGIT_HEIGHT));
        digitImageList.add(bufferedImage.getSubimage(1 * DIGIT_WIDTH, 0, DIGIT_WIDTH, DIGIT_HEIGHT));
        digitImageList.add(bufferedImage.getSubimage(2 * DIGIT_WIDTH, 0, DIGIT_WIDTH, DIGIT_HEIGHT));
        digitImageList.add(bufferedImage.getSubimage(3 * DIGIT_WIDTH, 0, DIGIT_WIDTH, DIGIT_HEIGHT));
        digitImageList.add(bufferedImage.getSubimage(4 * DIGIT_WIDTH, 0, bufferedImage.getWidth() - 4 * DIGIT_WIDTH, DIGIT_HEIGHT));
        return digitImageList;
	} 
}
