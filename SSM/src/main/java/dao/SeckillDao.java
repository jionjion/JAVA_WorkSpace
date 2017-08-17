package dao;

import java.util.Date;
import java.util.List;

import bean.Seckill;

/**Seckill表的映射*/
public interface SeckillDao {

	/**
	 * 减库存
	 * @param seckillId 秒杀ID	
	 * @param KillTime	秒杀时间
	 * @return	秒杀记录ID
	 */
	int reduceNumber(long seckillId,Date KillTime);
	
	/**
	 * 查询秒杀商品
	 * @param seckillId	秒杀id
	 * @return	秒杀对象
	 */
	Seckill queryById(long seckillId);
	
	
	/**
	 * 分页查询全部
	 * @param offet	开始位置
	 * @param limit	偏移量
	 * @return	秒杀对象集合
	 */
	List<Seckill> queryAll(int offet,int limit);
}
