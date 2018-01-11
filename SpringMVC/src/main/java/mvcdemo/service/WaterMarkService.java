package mvcdemo.service;

import java.io.IOException;
import java.io.InputStream;

public interface WaterMarkService {
	
	/**
	 * 	拷贝文件信息
	 * @param image			图片文件IO
	 * @param imageName		图片名字
	 * @param uploadPath	相对路径,用于web访问
	 * @param realUploadPath 绝对路径,文件磁盘位置信息
	 * @return				图片相对路径地址	
	 * @throws IOException 
	 */
	public String uploadImage(InputStream image,String imageName,String uploadPath,String realUploadPath) throws IOException;
}
