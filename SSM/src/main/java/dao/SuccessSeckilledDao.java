package dao;

import bean.SuccessSeckilled;

/**SuccessKilled表的映射*/
public interface SuccessSeckilledDao {

	/**
	 * 插入购买明细
	 * @param seckillId	秒杀物品ID
	 * @param userPhone	用户手机号
	 * @return	插入的记录ID
	 */
	int insertSuccessKilled(long seckillId , long userPhone);
	
	/**
	 * 根据秒杀ID查询SuccessKilled表,并返回携带秒杀对象实体
	 * @param seckillId :秒杀成功表的ID
	 * @return	秒杀记录明细
	 */
	SuccessSeckilled queryByIdIdWithSeckill(long seckillId);
}
