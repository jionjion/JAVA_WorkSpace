package springData.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import springData.jpa.repositoryTest.SpringDataJpaBaseTest;

import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

/**
 * @author Jion
 *  测试Set类
 *      操作 集合 Set
 */
@Slf4j
public class SetTest extends SpringDataRedisBaseTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private SetOperations<String, String> setOperations;

    private BoundSetOperations<String, String> boundSetOperations;

    @Before
    public void initOperations(){
        if (Objects.isNull(setOperations)){
            setOperations = redisTemplate.opsForSet();
        }
        if (Objects.isNull(boundSetOperations)){
            boundSetOperations = redisTemplate.boundSetOps("key");
        }
    }


    @Test
    public void testSetOperations(){

        // 添加
        // SADD key member [member ...]
        Long addResult = setOperations.add("set","A","B","C","D","E");
        assertNotNull(addResult);

        // 判断集合是否存在某元素
        // SISMEMBER key member
        boolean isMemberResult = setOperations.isMember("set","A");
        assertTrue(isMemberResult);

        // 从集合key中随机移除count个成员,并返回.当集合不存在时,返回nil.
        // SPOP key [count]
        String popObjectResult = setOperations.pop("set");
        assertNotNull(popObjectResult);
        List<String> popListResult = setOperations.pop("B",2);
        assertNotEquals(0,popListResult.size());

        // 从集合key中随机获得指定个数的成员.若集合为空,则返回nil
        // SRANDMEMBER key [count]
    }
}
