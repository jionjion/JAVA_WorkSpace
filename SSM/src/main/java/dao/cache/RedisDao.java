package dao.cache;
/***
 *	使用Redis缓存
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import bean.Seckill;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDao {

	//日志
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//缓存数据库连接池
	private final JedisPool jedisPool;
	
	//序列化对象的约束
	private RuntimeSchema<Seckill> runtimeSchema = RuntimeSchema.createFrom(Seckill.class);
	
	/**
	 * @param ip 地址
	 * @param port 端口
	 * 自定义构造器,Spring容器可以通过构造方法的方式注入实例
	 */
	public RedisDao(String ip, int port) {
		jedisPool = new JedisPool(ip, port);
	}
	
	
	/**
	 * @param seckillId	商品的ID
	 * @return	秒杀商品的详情
	 */
	public Seckill getSeckill(long seckillId) {
		try {
			//获得数据库对象
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:"+seckillId;		//以key-value的形式,存储数据
				//get -> byte[] -> 反序列化 -> Object(Seckill)
				//使用自定义的序列化
				byte[] bytes = jedis.get(key.getBytes());
				//缓存中获取序列化,并转为对象
				if (bytes != null) {
					//创建空对象
					Seckill seckill = runtimeSchema.newMessage();
					//传入 取出的字节数组,空对象,对象约束->构建缓存对象
					ProtostuffIOUtil.mergeFrom(bytes, seckill, runtimeSchema);
					//返回 反序列化的结果
					return seckill;
				}
			} finally {
				//只是关闭,将异常上抛,捕获日志记录
				jedis.close();
			}
		} catch (Exception e) {
			//日志记录
			logger.error(e.getMessage(),e);
		} 
		
		return null;
	}
	
	/**
	 * @param seckill 秒杀商品
	 * @return 是否成功转化的为字节数组
	 */
	public String setSeckill(Seckill seckill) {
		// Object -> byte[] -> redis
		try {
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:"+seckill.getSeckillId();	//序列化的键
				//将对象转为字节数组	传入,对象,对象序列化约束,缓存器的大小(这里使用缓存器的默认大小)
				byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, runtimeSchema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				//传入key,超时时间,序列化对象字节数组
				String result = jedis.setex(key.getBytes(), 60*60*3, bytes);
				return result;
				
			} finally {
				jedis.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
}
