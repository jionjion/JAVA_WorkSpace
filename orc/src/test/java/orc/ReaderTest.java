package orc;

import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.File;

public class ReaderTest {

	@Test
	public void test(){
		
		String read_dir = "C:\\Users\\14345\\Desktop\\imageCode\\";
		String write_dir = "C:\\Users\\14345\\Desktop\\imageCode\\final\\";
		
        File dir = new File(read_dir);
        File[] files = dir.listFiles();
        
        for (File file : files) {
        	if(file.isDirectory()) {
        		continue;
        	}
        	
            try {
				BufferedImage bufferedImage = ReadOrWriteImage.readImg(file); 
				String result = Reader.read(bufferedImage);
				ReadOrWriteImage.writeImg(bufferedImage, "png", write_dir + result);
			} catch (Exception e) {
//				e.printStackTrace();
				continue;
			}
        
        }
	}

}
