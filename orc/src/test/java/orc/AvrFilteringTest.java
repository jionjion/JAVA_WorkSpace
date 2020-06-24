package orc;

import static org.junit.Assert.*;

import org.junit.Test;

public class AvrFilteringTest {

	@Test
	public void testAvrFilteringStringStringString() {
		String srcPath = "C:\\Users\\14345\\Desktop\\imageCode\\img03.png";
		String destPath = "C:\\Users\\14345\\Desktop\\imageCode\\img03_new2";
		String format = "png";
		
		AvrFiltering.avrFiltering(srcPath, destPath, format);
	}

}
