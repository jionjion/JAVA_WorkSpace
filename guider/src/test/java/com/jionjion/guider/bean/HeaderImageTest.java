package com.jionjion.guider.bean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 14345
 *	测试图片类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class HeaderImageTest {

	// 通过容器自动注入的类可以获得${...}中定义的属性值
//	@Autowired
//	private HeaderImage image;
	
	
	/**
	 * 	通过继承获得父类的属性,属性值由属性文件获得,这种方法通过new关键字获得的子类不能获得父类通过@value方式从容器中的获得的值
	 * 	因此采用获得IOC容器的方式,直接获取bean并重写get方法
	 */
	@Test
	public void test() {
		HeaderImage image = new HeaderImage();
		log.info("文件根路径:" + image.getFileRootPath());
		log.info("文件大小限制:" + image.getMaxLength());
	}

}
