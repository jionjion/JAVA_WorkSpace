package mvcdemo.service.imp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import mvcdemo.service.WaterMarkService;

@Service("waterMarkService")
public class WaterMarkServiceIpm implements WaterMarkService {

	/**
	 * 	拷贝文件信息
	 * @param image			图片文件
	 * @param imageName		图片名字
	 * @param uploadPath	相对路径,用于web访问
	 * @param realUploadPath 绝对路径,文件磁盘位置信息
	 * @return				图片相对路径地址	
	 * @throws IOException 
	 */
	public String uploadImage(InputStream image,String imageName,String uploadPath,String realUploadPath) throws IOException {
		
		FileUtils.copyToFile(image, new File(realUploadPath + '/' + imageName));
		
		return uploadPath + '/' + imageName;
	}
}
