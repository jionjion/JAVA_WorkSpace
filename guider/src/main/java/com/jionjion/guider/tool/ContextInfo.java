package com.jionjion.guider.tool;

import java.util.HashMap;

import org.springframework.stereotype.Component;

/**
 * 	项目级别常量
 */
@Component
public class ContextInfo extends HashMap<String, Object>{

	private static final long serialVersionUID = 1L;
	
	/** 系统启动时间 */
	public final static Long APP_START_TIME = System.currentTimeMillis();
	
	/** 配置文件更新时间 */
	public static volatile Long configUpdateTime = System.currentTimeMillis();
			
}
