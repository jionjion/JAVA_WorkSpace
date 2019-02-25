package com.jionjion.guider.service;

import java.io.File;
import java.io.IOException;

import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.jionjion.guider.bean.HeaderImage;
import com.jionjion.guider.dao.japRepository.HeaderImageRepository;

/** 头像信息储存类 */
@Service
@Transactional	// 事务
public class HeaderImageService {

	@Autowired
	private HeaderImageRepository headerImageRepository;
	
	/** 通过UUID查询 */
	public HeaderImage findByUuid(String uuid) {
		// 数据库数据
		HeaderImage headerImage = headerImageRepository.findByUuid(uuid);
		// 填入文件对象
		headerImage = readHeaderImageFile(headerImage);
		return headerImage;
	}
	
	/** 保存 */
	public HeaderImage save(HeaderImage headerImage) throws IOException {
		// 数据库首先保存
		headerImage = headerImageRepository.save(headerImage);
		// 不为空,保存文件
		if (!ObjectUtils.isEmpty(headerImage.getUuid())) {
			writeHeaderImageFile(headerImage);
		}
		return headerImage;
	}
	
	/** 删除 */
	public Integer delete(HeaderImage headerImage) {
		// 首先删除数据库
		Integer deleteCount = headerImageRepository.deleteByUuid(headerImage.getUuid());
		// 随后删除文件
		Boolean deleteFlag = removeHeaderImageFile(headerImage);		
		if(deleteCount > 0 && deleteFlag) {
			return deleteCount;
		}
		return 0;
	}
	
	
	
	/** 保存头像文件到磁盘 */
	private Boolean writeHeaderImageFile(HeaderImage headerImage) throws IOException {
		String filePath = headerImage.getFileRootPath() + headerImage.getFilePath() + headerImage.getUuid();
		File file = new File(filePath);
		FileUtils.copyFile(headerImage.getFile(), file);
		return true;
	}
	
	/** 读取磁盘头像文件 */
	private HeaderImage readHeaderImageFile(HeaderImage headerImage) {
		String filePath = headerImage.getFileRootPath() + headerImage.getFilePath() + headerImage.getUuid();
		File file = FileUtils.getFile(filePath);
		headerImage.setFile(file);
		return headerImage;
	}
	
	/** 删除磁盘头像文件 */
	private Boolean removeHeaderImageFile(HeaderImage headerImage) {
		String filePath = headerImage.getFileRootPath() + headerImage.getFilePath() + headerImage.getUuid();
		File file = FileUtils.getFile(filePath);
		return FileUtils.deleteQuietly(file);
	}
	
}

