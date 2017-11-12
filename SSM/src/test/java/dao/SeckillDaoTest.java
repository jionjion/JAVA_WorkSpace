package dao;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bean.Seckill;

/**
 *	Spring和Junit整合,在Junit启动时加载SpringIOC容器
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

	@Resource
	private SeckillDao seckillDao;
	
	@Test
	public void testReduceNumber() {
		Date date = new Date();
		int count = seckillDao.reduceNumber(1000, date);
		System.out.println("减少:"+count);
	}

	@Test
	public void testQueryById() {
		Seckill seckill = seckillDao.queryById(1000);
		System.out.println(seckill);
	}

	@Test
	public void testQueryAll() {
		List<Seckill> list = seckillDao.queryAll(0, 10);
		System.out.println(list.toString());
	}
}
