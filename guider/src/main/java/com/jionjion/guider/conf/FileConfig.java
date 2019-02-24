package com.jionjion.guider.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

/**
 * @author 14345
 * 	文件配置类
 *	随容器启动
 */
@Component	// 标识该类为配置类
public class FileConfig {
	
	/** 文件存放根路径 */
	@Value("${guider.file.filepath}")
	@Getter
	private String fileRootPath;

	/** 文件限制最大大小 */
	@Value("${guider.file.maxLength}")
	@Getter
	private Long maxLength;
}
