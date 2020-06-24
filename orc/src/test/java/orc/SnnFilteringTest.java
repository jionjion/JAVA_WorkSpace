package orc;

import static org.junit.Assert.*;

import org.junit.Test;

/** 对称近邻均值滤波 */
public class SnnFilteringTest {

	@Test
	public void testSnnFilteringStringStringString() {
		String srcPath = "C:\\Users\\14345\\Desktop\\imageCode\\img26_gray.png";
		String destPath = "C:\\Users\\14345\\Desktop\\imageCode\\img26_Snn";
		String format = "png";
		
		SnnFiltering.snnFiltering(srcPath, destPath, format);
	}

}
