package orc;

import static org.junit.Assert.*;

import org.junit.Test;

public class MedianFilteringTest {

	@Test
	public void testMedianFilteringStringStringString() {
		
		String srcPath = "C:\\Users\\14345\\Desktop\\imageCode\\img04.png";
		String destPath = "C:\\Users\\14345\\Desktop\\imageCode\\img04_median";
		String format = "png";
		
		MedianFiltering.medianFiltering(srcPath, destPath, format);
	}

}
