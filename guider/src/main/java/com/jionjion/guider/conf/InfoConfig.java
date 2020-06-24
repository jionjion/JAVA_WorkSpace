package com.jionjion.guider.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;

/**
 * @author 14345
 * 	系统常见提示
 */

@Component	// 标识该类为配置类
@PropertySource("classpath:guider.properties")	// 属性文件 位置
@ConfigurationProperties(prefix="guider.info", ignoreUnknownFields=false) // 属性文件前缀 
public class InfoConfig {
	
	@Getter
	private String userEmpty;
	
	@Getter
	private String userPasswordError;
	
	@Getter
	private String tokenTimeout;
}
