package com.jionjion.guider.service;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import com.jionjion.guider.bean.HeaderImage;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 14345
 * 	
 * 	测试头像保存服务,包括文件保存与数据库
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class HeaderImagerServiceTest {

	@Autowired
	private HeaderImagerService service;
	
	@Autowired
	private HeaderImage headerImage;
	
	/**
	 * 	测试找寻文件
	 * 	文件路径存在,且文件不为空
	 */
	@Test
	public void testFindByUuid() {
		// 查询获得的属性
		HeaderImage headerImage = service.findByUuid("40288181691ea57c01691ea5c4540000");
		
		log.info("文件路径:" + headerImage.getFilePath());
		log.info("父类属性:" + headerImage.getFileRootPath());
		// 文件对象
		assertNotNull(headerImage.getFile());
	}

	/**
	 * 	测试保存文件
	 */
	@Test
	public void testSave() {
		try {
			// 获得resource目录下的文件
			File file = ResourceUtils.getFile("classpath:\\static\\img\\favicon.ico");
			headerImage.setFile(file);
			headerImage.setFileLength(file.length());
			headerImage.setFileName(file.getName());
			headerImage.setFileType("png");
			headerImage.setFilePath("\\header\\");
			service.save(headerImage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Test
	public void testDelete() {
		String uuid = "40288181691ea57c01691ea5c4540000";
		HeaderImage headerImage = service.findByUuid(uuid);
		service.delete(headerImage);
	}

}
