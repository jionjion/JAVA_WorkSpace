package com.jionjion.guider.enums;

/**
 * @author 14345
 *	站点定义MXL文件节点属性
 */
public enum WebsiteXmlAttributeEnum {

	ID("id"),
	NAME("name"),
	URI("uri");
	
	private String attribute;
	
	private WebsiteXmlAttributeEnum(String attribute) {
		this.attribute = attribute;
	}
	
	public String getAttribute() {
		return this.attribute;
	}
	
}
