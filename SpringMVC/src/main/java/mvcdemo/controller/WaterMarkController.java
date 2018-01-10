package mvcdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 	图片水印
 * @author JionJion
 */

@Controller
@RequestMapping("/file")
public class WaterMarkController {

	
	/** 图片上传处理,并返回增加水印后的图片
	 * 	URL:http://localhost:8080/SpringMVC/file/waterMark */
	@RequestMapping(value="waterMark",method=RequestMethod.POST)
	public String LoginShow() {
		
		return "ImageFileWaterMark";
	}
}
