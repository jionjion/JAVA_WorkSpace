package service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bean.Seckill;
import dto.Exposer;
import dto.SeckillExecution;
import exception.RepeatKillException;
import exception.SeckillCloseException;
import exception.SeckillException;
/**
 *	对Service的实现类进行单元测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

	//日志
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired	//@Resource也可以实现自动注入
	private SeckillService seckillService;
	
	@Test
	public void testGetSeckillList() {
		List<Seckill> list = seckillService.getSeckillList();
		logger.info("获得秒杀列表:{}",list);
	}

	@Test
	public void testGetById() {
		long id = 1000l;
		Seckill seckill = seckillService.getById(id);
		logger.info("获得秒杀对象:{}",seckill.toString());
	}

	@Test
	public void testExportSeckillUrl() {
		Exposer exposer = seckillService.exportSeckillUrl(1000L);
		logger.info("是否可以执行秒杀请求信息:{}",exposer.toString());
	}

	@Test
	public void testExecuteSeckill() {
		try {
			SeckillExecution seckillExecution = seckillService.executeSeckill(1000L, 15516559773L, "7b5faf7d665a351143ba6a0601cb25a7");
			logger.info("执行秒杀:{}",seckillExecution.toString());
		} catch (RepeatKillException e) {
			logger.error(e.getMessage());
		} catch (SeckillCloseException e) {
			logger.error(e.getMessage());
		} catch (SeckillException e) {
			logger.error(e.getMessage());
		}
	}

	
	@Test
	public void testSeckillLogic() {
		//查询是否开启秒杀
		Exposer exposer = seckillService.exportSeckillUrl(1000L);
		logger.info("是否可以执行秒杀请求信息:{}",exposer.toString());
		//执行秒杀
		try {
			SeckillExecution seckillExecution = seckillService.executeSeckill(1000L, 15516559774L, exposer.getMd5());
			logger.info("执行秒杀:{}",seckillExecution.toString());
		} catch (RepeatKillException e) {
			logger.error(e.getMessage());
		} catch (SeckillCloseException e) {
			logger.error(e.getMessage());
		} catch (SeckillException e) {
			logger.error(e.getMessage());
		}
	}
	
	/*通过存储过程进行秒杀逻辑*/
	@Test
	public void testExecuteSeckillByProcedure() {
		long seckillId = 1000L;
		long userPhone = 15516559778L;
		Exposer exposer = seckillService.exportSeckillUrl(seckillId);	//暴露接口,完成调用
		if (exposer.isExposed()) {
			//如果暴露
			String md5 = exposer.getMd5();
			SeckillExecution seckillExecution = seckillService.executeSeckillByProcedure(seckillId, userPhone, md5);
			logger.info(seckillExecution.getStateInfo());
		}
	}
}

