package com.jionjion.guider.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jionjion.guider.bean.Website;
import com.jionjion.guider.service.WebsiteService;


/**
 * 	首页欢迎页面
 */
@Controller
@RequestMapping("/")
public class IndexController {
	
	@Autowired
	WebsiteService websiteService;
	
	//日志记录
	private static Logger log = LoggerFactory.getLogger(IndexController.class);
	
	
	@RequestMapping(value= {"/index"})
	public String viewIndex(Map<String,Object> model) {
		// 查询结果
//		List<Website> websiteList = websiteService.getWebsiteByXML();
		List<Website> websiteList = websiteService.findAll();
		model.put("websiteList", websiteList);
		return "index";
	}
	
	/**
	 * 	首页
	 */
	@RequestMapping(value= {"/","/index/{uid}","/*"},method=RequestMethod.GET)
	public String viewIndex(@PathVariable(required=false) Integer uid  , Map<String,Object> model ) {
		
		// URL参数
		model.put("uid", uid);
		// 重定向
		return "redirect:/index";
	}
}
