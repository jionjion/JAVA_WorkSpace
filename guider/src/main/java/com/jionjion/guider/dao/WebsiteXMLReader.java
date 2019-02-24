package com.jionjion.guider.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import com.jionjion.guider.tool.ContextInfo;


/**
 * 读取XML获得文本信息/
 */
@Repository
public class WebsiteXMLReader {

	/** 存放站点信息 */
	private static List<Map<String, String>> websiteList = null;

	/** 获得XML的最后修改时间 */
	private static Long getXMLUpdate() {
		
		// 引入文件IO
		InputStream xmlIO = WebsiteXMLReader.class.getClassLoader().getResourceAsStream("db/website.xml");
		File xml;
		try {
			xml = ResourceUtils.getFile("classpath:db/website.xml");
			return xml.lastModified();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return ContextInfo.APP_START_TIME;
		}
	}
	
	/** 读取XML获得站点信息 */
	private static List<Map<String, String>> readXML() throws Exception {

		// 引入文件IO
		InputStream xmlIO = WebsiteXMLReader.class.getClassLoader().getResourceAsStream("db/website.xml");
		// 创建SAXReader对象
		SAXReader saxReader = new SAXReader();
		// 加载XML,生成Document对象
		Document document = saxReader.read(xmlIO);
		// 获得根节点
		Element rootElement = document.getRootElement();
		// 获得根节点下的子节点
		Iterator<Element> childrenIterator = rootElement.elementIterator();
		List<Map<String, String>> websiteList = new ArrayList<>();
		// 遍历迭代器
		while (childrenIterator.hasNext()) {
			// 获得website节点
			Element childrenElement = (Element) childrenIterator.next();
			// 获得属性集合
			List<Attribute> childrenAttributes = childrenElement.attributes();
			// 获得属性集合
			Map<String, String> rootMap = new HashMap<String, String>();
			for (Attribute childrenAttribute : childrenAttributes) {
				String childrenAttributeName = childrenAttribute.getName();
				String childrenAttributeValue = childrenAttribute.getValue();
				// 放入Map中
				rootMap.put(childrenAttributeName, childrenAttributeValue);
			}
			websiteList.add(rootMap);
		}
		return websiteList;
	}

	/** 获得站点信息,返回一个Map */
	public List<Map<String, String>> getWebsiteByXML() {
		if (Objects.isNull(websiteList) || getXMLUpdate() >= ContextInfo.APP_START_TIME) {
			try {
				websiteList = WebsiteXMLReader.readXML();
				ContextInfo.configUpdateTime = getXMLUpdate();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return websiteList;
		} else {
			return websiteList;
		}

	}
}
