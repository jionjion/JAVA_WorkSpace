package orc;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Binary {

	public static BufferedImage binary(BufferedImage bufferedImage) throws IOException {
		int h = bufferedImage.getHeight();
		int w = bufferedImage.getWidth();
		int[][] gray = new int[w][h];
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				gray[x][y] = getGray(bufferedImage.getRGB(x, y));
			}
		}

		BufferedImage nbi = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY);
		// 阈值
		int SW = 80;
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				// 直接将当前像素灰度
				if (/*getAverageColor(gray, x, y, w, h)*/ gray[x][y] > SW) {
					int max = new Color(255, 255, 255).getRGB();
					nbi.setRGB(x, y, max);
				} else {
					int min = new Color(0, 0, 0).getRGB();
					nbi.setRGB(x, y, min);
				}
			}
		}

		return nbi;
	}

	/*
	 * 	以当前RGB的平均值作为相对灰度
	 */
	public static int getGray(int rgb) {
		String str = Integer.toHexString(rgb);
		int r = Integer.parseInt(str.substring(2, 4), 16);
		int g = Integer.parseInt(str.substring(4, 6), 16);
		int b = Integer.parseInt(str.substring(6, 8), 16);
		// or 直接new个color对象
		Color c = new Color(rgb);
		r = c.getRed();
		g = c.getGreen();
		b = c.getBlue();
		int top = (r + g + b) / 3;
		return (int) (top);
	}

	
	/**
	 * 	自己加周围8个灰度值再除以9，算出其相对灰度值
	 */
	public static int getAverageColor(int[][] gray, int x, int y, int w, int h) {
		int rs = gray[x][y] + (x == 0 ? 255 : gray[x - 1][y]) + (x == 0 || y == 0 ? 255 : gray[x - 1][y - 1])
				+ (x == 0 || y == h - 1 ? 255 : gray[x - 1][y + 1]) + (y == 0 ? 255 : gray[x][y - 1])
				+ (y == h - 1 ? 255 : gray[x][y + 1]) + (x == w - 1 ? 255 : gray[x + 1][y])
				+ (x == w - 1 || y == 0 ? 255 : gray[x + 1][y - 1])
				+ (x == w - 1 || y == h - 1 ? 255 : gray[x + 1][y + 1]);
		return rs / 9;
	}

}