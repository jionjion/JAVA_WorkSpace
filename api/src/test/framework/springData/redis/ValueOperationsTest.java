package springData.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Objects;

/**
 * @author Jion
 *  测试 List
 *      ListOperations 对List的操作
 */
@Slf4j
public class ValueOperationsTest extends SpringDataRedisBaseTest{

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    private ValueOperations<String,String> valueOperations;

    @Before
    public void initOperations(){
        if (Objects.isNull(valueOperations)){
            valueOperations = redisTemplate.opsForValue();
        }
        // 初始化数据,启用事物,并提交
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.multi();
        redisTemplate.delete("ValueA");
        valueOperations.set("ValueA","A");
        redisTemplate.exec();

    }



}
