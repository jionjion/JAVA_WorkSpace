package dao;

import org.apache.ibatis.annotations.Param;

import bean.SuccessSeckill;

/**SuccessKilled表的映射*/
public interface SuccessSeckillDao {

	/**
	 * 插入购买明细
	 * @param seckillId	秒杀物品ID
	 * @param userPhone	用户手机号
	 * @return	插入的记录ID
	 */
	int insertSuccessKill(@Param("seckillId") long seckillId ,@Param("userPhone") long userPhone);
	
	/**
	 * 根据秒杀ID查询SuccessKilled表,并返回携带秒杀对象实体
	 * @param seckillId :秒杀成功表的ID
	 * @return	秒杀记录明细
	 */
	SuccessSeckill queryByIdIdWithSeckill(@Param("seckillId") long seckillId ,@Param("userPhone") long userPhone);
}
