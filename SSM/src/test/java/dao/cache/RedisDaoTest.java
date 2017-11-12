package dao.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bean.Seckill;
import dao.SeckillDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {

	@Autowired
	private RedisDao redisDao;	//缓存操作
	
	@Autowired
	private SeckillDao seckillDao;	//数据库操作
	
	private long seckillId = 1000;	//商品的ID
	/**
	 * 	测试缓存的get/set方法.
	 */
	@Test
	public void testSetAndGetSeckill() {
		//首先试图通过缓存获得秒杀商品的信息
		Seckill seckill = redisDao.getSeckill(seckillId);
		//如果获取失败,从数据库中获取
		if (seckill == null) {
			seckill = seckillDao.queryById(seckillId);
			//将获取的对象存入缓存中
			if (seckill != null) {
				String result = redisDao.setSeckill(seckill);
				System.out.println("缓存存入对象结果:"+result);
			}
			//重试将对象从缓存中读取
			seckill = redisDao.getSeckill(seckillId);
			System.out.println("从缓存中读取对象:"+seckill);
		}
	}
}



