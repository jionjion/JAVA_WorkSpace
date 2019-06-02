package top.jionjion.api.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.*;
import springData.jpa.repositoryTest.SpringDataJpaBaseTest;
import top.jionjion.api.BaseTest;

import static org.junit.Assert.*;

/**
 * @author Jion
 *  Redis的配置测试
 */
@Slf4j
public class RedisConfigTest extends BaseTest{

/*
接口	                        描述,操作对象
GeoOperations               空间地理
HashOperations              Hash 操作
HyperLogLogOperations       HyperLogLog 操作
ListOperations              List 操作
SetOperations               Set 操作
ValueOperations             Value 操作
ZSetOperations              Zset 操作

                            绑定操作
BoundGeoOperations          空间地理
BoundHashOperations         Hash 绑定操作
BoundKeyOperations          Key 绑定操作
BoundListOperations         List 绑定操作
BoundSetOperations          Set 绑定操作
BoundValueOperations        Value 绑定操作
BoundZSetOperations         Zset 绑定操作

 */

    /** 配置文件 */
    @Autowired
    RedisConfig redisConfig;

    /** Redis操作模板,框架提供 */
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    /** 连接工厂 */
    @Autowired
    JedisConnectionFactory jedisConnectionFactory;

    /** 字符串操作模板,框架提供 */
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    GeoOperations geoOperations = redisTemplate.opsForGeo();

    BoundGeoOperations boundGeoOperations = redisTemplate.boundGeoOps(null);



    /** 测试注入连接工厂 */
    @Test
    public void testAutowiredBean(){
        assertNotNull(redisConfig);
        assertNotNull(redisTemplate);
        assertNotNull(jedisConnectionFactory);
        assertNotNull(stringRedisTemplate);
    }


}