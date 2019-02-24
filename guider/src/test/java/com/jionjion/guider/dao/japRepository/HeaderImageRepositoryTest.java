package com.jionjion.guider.dao.japRepository;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jionjion.guider.bean.HeaderImage;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 14345
 *	测试属性文件的获取
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class HeaderImageRepositoryTest {

	
	@Autowired
	private HeaderImageRepository repository;
	
	@Test
	public void testFindByUuid() {
		String uuid = "40288181691de03201691de054920000";
		HeaderImage image = repository.findByUuid(uuid);
		assertNotNull(image);
	}

	@Test
	public void testSave() {
		HeaderImage image = new HeaderImage();
		image.setFileName("头像");
		image.setFileLength(1024L);
		image.setFileType("png");
		image.setFilePath("\\header");
		repository.save(image);
		log.info("保存图片对象:" + image);
	}

	@Test
	public void testDeleteByUuid() {
		String uuid = "40288181691de03201691de054920000";
		Integer deleteCount = repository.deleteByUuid(uuid);
		assertNotNull(deleteCount);
	}

}
