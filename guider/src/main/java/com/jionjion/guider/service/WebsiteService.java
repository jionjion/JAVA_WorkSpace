package com.jionjion.guider.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jionjion.guider.bean.Website;
import com.jionjion.guider.dao.WebsiteXMLReader;
import com.jionjion.guider.dao.japRepository.WebsiteRepository;
import com.jionjion.guider.enums.WebsiteXmlAttributeEnum;

/** 站点信息服务 */
@Service
public class WebsiteService {

	@Autowired
	private WebsiteXMLReader websiteXMLReader;
	
	/** 通过XML获得 */
	public List<Website> getWebsiteByXML(){
		List<Map<String, String>> websiteXmlList = websiteXMLReader.getWebsiteByXML();
		List<Website> websiteList = new ArrayList<>();
		// 解析为对象列表
		for (int i=0; i<websiteXmlList.size(); i++) {
			Website website = new Website();
			website.setSiteId(Integer.parseInt(
									websiteXmlList.get(i).get(WebsiteXmlAttributeEnum.ID.getAttribute())));
			website.setSiteName(websiteXmlList.get(i).get(WebsiteXmlAttributeEnum.NAME.getAttribute()));
			website.setSiteUri(websiteXmlList.get(i).get(WebsiteXmlAttributeEnum.URI.getAttribute()));
			websiteList.add(website);
		}
		return websiteList;
	}
	
	@Autowired
	private WebsiteRepository websiteRepository;
	
	/** 通过数据库获得,查询全部 */
	public List<Website> findAll(){
		return websiteRepository.findAll();
	}
	
	/** 通过Id查询 */
	public Website findBysiteId(Integer siteId) {
		return websiteRepository.findBysiteId(siteId);
	}
	
	/** 保存 */
	public Website save(Website website) {
		return websiteRepository.save(website);
	}
	
	/** 删除 */
	public Integer deleteBysiteId(Integer siteId) {
		return websiteRepository.deleteBysiteId(siteId);
	}	
}
