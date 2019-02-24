package com.jionjion.guider.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jionjion.guider.bean.Website;
import com.jionjion.guider.dto.ResultJsonDto;
import com.jionjion.guider.service.WebsiteService;

/**
 * @author 14345
 *	对外提供站点修改接口
 */
@RestController	//对外提供restful服务
@RequestMapping("/api")
public class WebsiteController {

	@Autowired
	WebsiteService websiteService;
	
	/**
	 * 	根据请求站点ID返回查询结果
	 * @param siteId 站点ID
	 * @return 返回结果
	 */
	@GetMapping("/website/{siteId}")
	public ResultJsonDto<?> websiteGet(@PathVariable(required=false) Integer siteId){
		// 返回,根据ID查询站点信息
		return ResultJsonDto.returnSuccessDto(websiteService.findBysiteId(siteId));
	}
	
	
	/** 保存 */
	@PostMapping("/website")
	public ResultJsonDto<?> websiteSave(@RequestBody Website website){
		// 保存
		return ResultJsonDto.returnSuccessDto(websiteService.save(website));
	}
	
	/** 删除 */
	@DeleteMapping("/website/{siteId}")
	public ResultJsonDto<?> deleteBysiteId(@PathVariable Integer siteId) {
		// 返回删除结果
		return ResultJsonDto.returnSuccessDto(websiteService.deleteBysiteId(siteId));
	}		

}
