package dao;


import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bean.SuccessSeckill;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessSeckillDaoTest {

	@Resource
	private SuccessSeckillDao successSeckillDao;
	
	@Test
	public void testInsertSuccessKill() {
		int result = successSeckillDao.insertSuccessKill(1000, 15516559772L);
		System.out.println("插入的主键为:"+result);
	}

	@Test
	public void testQueryByIdIdWithSeckill() {
		SuccessSeckill  successSeckill = successSeckillDao.queryByIdIdWithSeckill(1000L,15516559772L);
		System.out.println(successSeckill.toString());
	}

}
