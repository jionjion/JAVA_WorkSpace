package com.jionjion.guider.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author 14345
 *	通过读取属性文件,获得QQ互联信息,并包装为对象
 */

@Component	// 标识该类为配置类
@PropertySource("classpath:guider.properties")	// 属性文件 位置
@ConfigurationProperties(prefix="guider.qq.connect", ignoreUnknownFields=false) // 属性文件前缀 
public class QQConnect {

	private String appId;
	
	private String appKey;
	
	private String version;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "QQConnect [appId=" + appId + ", appKey=" + appKey + ", version=" + version + "]";
	}
}
