package com.jionjion.guider.bean;

import org.springframework.stereotype.Component;

import com.jionjion.guider.conf.FileConfig;
import com.jionjion.guider.tool.ContextBeanUtil;

/**
 * @author 14345
 *	文件类,定义各种文件信息
 */
@Component
public abstract class FileObject {
	

	// 为了防止在New子类时出现父类属性为空的情况,这里通过手动获得配置对象
//	@Autowired 
//	private FileConfig fileConfig; 

	/** 文件存放根路径 */
	protected String fileRootPath;

	public String getFileRootPath() {
		FileConfig fileConfig = ContextBeanUtil.getBean(FileConfig.class);
		return fileConfig.getFileRootPath();
	}
	
	/** 文件限制最大大小 */
	protected String maxLength;

	public Long getMaxLength() {
		FileConfig fileConfig = ContextBeanUtil.getBean(FileConfig.class);
		return fileConfig.getMaxLength();
	}	
	
	/** 文件名 */
	protected String fileName;
	
	/** 文件大小 */
	protected Long fileLength;
	
	/** 文件存放路径 */
	protected String filePath;
	
	/** 文件类型 */
	protected String fileType;
	
	/** 文件UUID */
	protected String uuid;
	
	
}
