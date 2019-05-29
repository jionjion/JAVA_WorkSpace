package top.jionjion.api.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
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
Interface	                Description
                            Key Type Operations
GeoOperations               Redis geospatial operations, such as GEOADD, GEORADIUS,…​
HashOperations              Redis hash operations
HyperLogLogOperations       Redis HyperLogLog operations, such as PFADD, PFCOUNT,…​
ListOperations              Redis list operations
SetOperations               Redis set operations
ValueOperations             Redis string (or value) operations
ZSetOperations              Redis zset (or sorted set) operations
Key Bound Operations        BoundGeoOperations
                            Redis key bound geospatial operations
BoundHashOperations         Redis hash key bound operations
BoundKeyOperations          Redis key bound operations
BoundListOperations         Redis list key bound operations
BoundSetOperations          Redis set key bound operations
BoundValueOperations        Redis string (or value) key bound operations
BoundZSetOperations         Redis zset (or sorted set) key bound operations

参考 org.springframework.data.redis.support
 */


    @Autowired
    RedisConfig redisConfig;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    JedisConnectionFactory jedisConnectionFactory;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /** 测试注入连接工厂 */
    @Test
    public void testAutowiredBean(){
        assertNotNull(redisConfig);
        assertNotNull(redisTemplate);
        assertNotNull(jedisConnectionFactory);
        assertNotNull(stringRedisTemplate);

    }


}