package service;
import java.util.List;

import org.springframework.stereotype.Service;

import bean.Seckill;
import dto.Exposer;
import dto.SeckillExecution;
import exception.RepeatKillException;
import exception.SeckillCloseException;
import exception.SeckillException;

/**
 *	使用注解的方式,配置包扫描
 *	@Component 所有的组件
 *	@Service
 *	@Repository
 *	@Controller
 */
public interface SeckillService {

	
	/**
	 * 	获得秒杀的列表
	 * @return 秒杀物品列表
	 */
	List<Seckill> getSeckillList();
	
	/**
	 * 	获得秒杀
	 * @param seckillId 秒杀对象物品的ID
	 * @return 秒杀对象物品
	 */
	Seckill getById(long seckillId);
	
	/**
	 * 	是否开启秒杀
	 * @param serckillId 如果开启秒杀则暴露秒杀地址,否则返回系统时间
	 * @return 是否开启秒杀
	 */
	Exposer exportSeckillUrl(long seckillId);

	
	/**
	 * 执行秒杀工作
	 * @param seckillId 秒杀物品的ID
	 * @param userPhone 用户电话
	 * @param md5 验证URL
	 * @return 秒杀结果
	 * @throws RepeatKillException 重复秒杀异常
	 * @throws SeckillCloseException 秒杀关闭异常
	 * @throws SeckillException 通用异常
	 */
	SeckillExecution executeSeckill(long seckillId,long userPhone,String md5)
				throws RepeatKillException,SeckillCloseException,SeckillException;
	
	/**
	 * 执行秒杀工作,通过存储过程
	 * @param seckillId 秒杀物品的ID
	 * @param userPhone 用户电话
	 * @param md5 验证URL
	 * @return 秒杀结果
	 */
	SeckillExecution executeSeckillByProcedure(long seckillId,long userPhone,String md5);	
}
