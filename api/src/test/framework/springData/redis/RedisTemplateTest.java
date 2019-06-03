package springData.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundGeoOperations;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import springData.jpa.repositoryTest.SpringDataJpaBaseTest;

import static org.junit.Assert.assertNotNull;

/**
 * @author Jion
 *  Redis的配置测试
 */
@Slf4j
public class RedisTest extends SpringDataJpaBaseTest {

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

    /** Redis操作模板,框架提供 */
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    /** 字符串操作模板,框架提供 */
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /** 测试注入 */
    @Test
    public void testAutowiredBean(){
        assertNotNull(redisTemplate);
        assertNotNull(stringRedisTemplate);
    }


//    GeoOperations geoOperations = redisTemplate.opsForGeo();
//    BoundGeoOperations boundGeoOperations = redisTemplate.boundGeoOps(null);




}