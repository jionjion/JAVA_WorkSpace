package com.jionjion.guider.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jionjion.guider.bean.HeaderImage;
import com.jionjion.guider.service.HeaderImageService;

/**
 * @author 14345
 *	用户管理
 */
@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private HeaderImageService headerImageService; 
	
    @Autowired
    private HttpSession session;
    
	/** 登录 */
	
	/** 登出 */
	
	/** 头像 */
	@GetMapping("image/header/{token}")
	public String headerImageGet(@PathVariable String token) {
		
		String sessionToken = (String) session.getAttribute("token");
		
		// token不为空,或者session与请求token不相等
		if(!StringUtils.isEmpty(sessionToken) || !sessionToken.equals(sessionToken)) {
			
			HeaderImage headerImage = headerImageService.findByUuid("402881816929bba6016929bbc3c20000");
			File imageFile = headerImage.getFile();
			byte[] imageBytes;
			
			try {
				imageBytes = FileUtils.readFileToByteArray(imageFile);
				return Base64Utils.encodeToString(imageBytes);
			} catch (IOException e) {
				return null;
			}
		}
		return null;
	}
	
}
