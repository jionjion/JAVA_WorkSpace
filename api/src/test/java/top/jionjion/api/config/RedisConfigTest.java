package top.jionjion.api.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import springData.jpa.repositoryTest.SpringDataJpaBaseTest;
import top.jionjion.api.BaseTest;

import static org.junit.Assert.*;

/**
 * @author Jion
 *  Redis的配置测试
 */
@Slf4j
public class RedisConfigTest extends BaseTest{

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Test
    public void test(){

        String result = redisTemplate.opsForValue().get("user");
        assertNotNull(result);
        log.info(result);
    }
}