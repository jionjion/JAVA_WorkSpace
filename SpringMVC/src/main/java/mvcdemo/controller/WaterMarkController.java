package mvcdemo.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import mvcdemo.bean.ImageInfo;
import mvcdemo.service.WaterMarkService;

/**
 * 	图片水印
 * @author JionJion
 */

@Controller
@RequestMapping("/file")
public class WaterMarkController {
	
	@Autowired
	private WaterMarkService waterMarkService;
	
	/** 跳转图片上传文件页面 		 <br>
	 * 	URL:http://localhost:8080/SpringMVC/file/imageFileUpload */
	@RequestMapping(value="imageFileUpload",method=RequestMethod.GET)
	public String imageFileUploadShow() {
		
		return "ImageFileUpload";
	}	
	
	/** 图片上传处理,并返回增加水印后的图片		 <br>
	 * 	URL:http://localhost:8080/SpringMVC/file/waterMark 
	 * @throws Exception IO操作异常 */
	@RequestMapping(value="waterMark",method=RequestMethod.POST)
	public String addFileWaterMark(@RequestParam(value="image") MultipartFile image ,
													HttpServletRequest request) throws Exception {
		
		//图片文件夹存放位置
		String uploadPath ="waterImages";
		
		//文件夹真实位置
		String realUploadPath = request.getServletContext().getRealPath("/") + "\\WEB-INF\\resource\\waterImages";
		
		//处理图片,并返回带水印的图片的位置
		String WithWaterMarkUrl = waterMarkService.uploadImage(image.getInputStream(), image.getOriginalFilename(), uploadPath, realUploadPath);
		
		ImageInfo imageInfo = new ImageInfo();
		imageInfo.setWithWaterMarkUrl(WithWaterMarkUrl);
		
		return "ImageFileWaterMark";
	}
	
	
	
}
