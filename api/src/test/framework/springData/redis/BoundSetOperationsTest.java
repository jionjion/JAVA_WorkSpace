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
public class BoundSetOperationsTest extends SpringDataRedisBaseTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private BoundSetOperations<String, String> boundSetOperations;

    @Before
    public void initOperations(){
        if (Objects.isNull(boundSetOperations)){
            boundSetOperations = redisTemplate.boundSetOps("SetA");
        }
//        下列方法与 SetOperations 一致
//        boundSetOperations.add();
//        boundSetOperations.intersectAndStore();
//        boundSetOperations.pop();
//        boundSetOperations.randomMember();
//        boundSetOperations.remove();
//        boundSetOperations.move();
//        boundSetOperations.size();
//        boundSetOperations.members();
//        boundSetOperations.scan();
//        boundSetOperations.intersect();
//        boundSetOperations.intersectAndStore();
//        boundSetOperations.union();
//        boundSetOperations.unionAndStore();
//        boundSetOperations.diff();
//        boundSetOperations.diffAndStore();
    }


    @Test
    public void testGetKey(){

    }

    @Test
    public void testGetType(){

    }

    @Test
    public void testGetExpire(){

    }

    @Test
    public void testExpire(){

    }

    @Test
    public void testExpireAt(){

    }

    @Test
    public void testPersist(){

    }

    @Test
    public void testRename(){

    }


}
