package springData.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author Jion
 * 测试Set类
 * 操作 集合 Set
 */
@Slf4j
public class ZSetOperationsTest extends SpringDataRedisBaseTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private ZSetOperations<String, String> zSetOperations;

    /**
     * 初始化数据
     */
    @Before
    public void initData() {
        if (Objects.isNull(zSetOperations)) {
            zSetOperations = redisTemplate.opsForZSet();
        }
        // 初始化数据,启用事物,并提交
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.multi();
        redisTemplate.delete("SetA");
        redisTemplate.exec();
    }

    /**
     * 测试SetOperations是否存在
     */
    @Test
    public void testAutowired() {
        assertNotNull(zSetOperations);
    }


}
