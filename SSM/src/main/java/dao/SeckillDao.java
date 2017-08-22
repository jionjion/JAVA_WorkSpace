package dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import bean.Seckill;

/**Seckill表的映射*/
public interface SeckillDao {

	/**
	 * 减库存
	 * @param seckillId 秒杀ID	
	 * @param KillTime	秒杀时间
	 * @return	秒杀记录ID
	 * 	注意:这里引用的param类型需要注意,引号内的参数名称,为mapper映射中传入的#{}的参数名
	 * 	参考资料=> http://blog.csdn.net/gao36951/article/details/44258217
	 */
	int reduceNumber(@Param("seckillId")long seckillId,@Param("killTime")Date KillTime);
	
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
	 * 	offset注解实现向SQL传入参数名称
	 */
	List<Seckill> queryAll(@Param("offset")int offset,@Param("limit")int limit);
	
	
	/**
	 * 通过调用存储过程,完成调用
	 * @param paramMap 传入的参数
	 */
	void executeSeckillByProcedure(Map<String, Object> paramMap);
}
