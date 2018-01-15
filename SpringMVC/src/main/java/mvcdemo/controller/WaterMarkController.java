package mvcdemo.controller;

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
	public String addTextWaterMark(@RequestParam(value="image") MultipartFile image ,
													HttpServletRequest request) throws Exception {
		
		//图片文件夹存放位置,相对于项目的/temp目录下
		String uploadPath ="temp";
		
		//文件夹真实位置 由项目发布的Tomcat决定,内嵌Tomcat还是独立Tomcat,发布在容器根路径,并注意设置Eclipse的发布路径
		String realUploadPath = request.getServletContext().getRealPath("/") + "\\temp";
		
		//保存图片,并返回图片的访问位置
		String notWaterMarkUrl = waterMarkService.uploadImage(image.getInputStream(), image.getOriginalFilename(), uploadPath, realUploadPath);
		
		String markType = request.getParameter("markType");
		String WithWaterMarkUrl = null;
		if ("text".equals(markType)) {
			//添加文字水印,并返回图片的访问位置
			WithWaterMarkUrl = waterMarkService.markTextImage(image.getInputStream(), image.getOriginalFilename(), uploadPath, realUploadPath);
		}else if ("logo".equals(markType)) {
			//添加图片水印,并返回图片的访问位置
			WithWaterMarkUrl = waterMarkService.markLogoImage(image.getInputStream(), image.getOriginalFilename(), uploadPath, realUploadPath);
		}else if ("texts".equals(markType)) {
			//添加多个图片水印,并返回图片的访问位置
			WithWaterMarkUrl = waterMarkService.markTextsImage(image.getInputStream(), image.getOriginalFilename(), uploadPath, realUploadPath);
		}else if ("logos".equals(markType)) {
			//添加多个图片水印,并返回图片的访问位置
			WithWaterMarkUrl = waterMarkService.markLogosImage(image.getInputStream(), image.getOriginalFilename(), uploadPath, realUploadPath);
		}else {
			WithWaterMarkUrl = ""; 
		}
		
		ImageInfo imageInfo = new ImageInfo();
		imageInfo.setNotWaterMarkUrl(notWaterMarkUrl);
		imageInfo.setWithWaterMarkUrl(WithWaterMarkUrl);
		request.setAttribute("imageInfo", imageInfo);
		return "ImageFileWaterMark";
	}
	
	
}
