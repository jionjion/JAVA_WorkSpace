package springData.redis;

import jdk.nashorn.internal.runtime.options.KeyValueOption;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ValueOperations;

import java.util.*;

import static org.junit.Assert.*;

/**
 * @author Jion
 *  测试 List
 *      ListOperations 对List的操作
 */
@Slf4j
public class ListOperationsTest extends SpringDataRedisBaseTest{

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private ListOperations<String,String> listOperations;

    @Before
    public void initOperations(){
        if (Objects.isNull(listOperations)){
            listOperations = redisTemplate.opsForList();
        }
        // 初始化数据,启用事物,并提交
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.multi();
        redisTemplate.delete("ListA");
        listOperations.leftPush ("ListA","A");
        listOperations.leftPush ("ListA","B");
        listOperations.leftPush ("ListA","C");
        redisTemplate.delete("ListB");
        listOperations.leftPush ("ListB","D");
        listOperations.leftPush ("ListB","E");
        listOperations.leftPush ("ListB","F");
        redisTemplate.exec();


//        listOperations.leftPush();
//        listOperations.leftPushIfPresent()
//        listOperations.leftPushAll();
//        listOperations.rightPush();
//        listOperations.rightPushIfPresent();
//        listOperations.rightPushAll();
//        listOperations.leftPop();
//        listOperations.rightPop();
//        listOperations.rightPopAndLeftPush();
//        listOperations.remove();
//        listOperations.size();
//        listOperations.index();
//        listOperations.set();
//        listOperations.range();
//        listOperations.trim();

    }

    /**
     *  语法 `LPUSH key value [value ...]
     *  将一个或者多个`value`值顺次插入到列表`key`的头前.`key`必须为列表类型,否则报错;如果`key`列表不存在,则自动创建.`key`列表的顺序由插入时的顺序决定,并且允许重复插入.
     */

    @Test
    public void testLeftPush(){
        Long result = listOperations.leftPush("ListA","D");
        assertNotNull(result);
        log.info(result.toString());
    }

    /**
     *  语法 `LPUSH key value [value ...]
     *  将一个或者多个`value`值顺次插入到列表`key`的头前.`key`必须为列表类型,否则报错;如果`key`列表不存在,则自动创建.`key`列表的顺序由插入时的顺序决定,并且允许重复插入.
     */
    @Test
    public void testLeftPushAll(){
        // 添加多个
        Long result1 = listOperations.leftPushAll("ListA","a,b,c");
        assertNotNull(result1);
        log.info(result1.toString());

        // 通过List添加
        List<String> values = new ArrayList<>();
        values.add("a");
        values.add("b");
        values.add("c");
        Long result2 = listOperations.leftPushAll("ListA",values);
        assertNotNull(result2);
        log.info(result2.toString());
    }

    @Test
    public void testLeftPushIfPresent(){

    }

    @Test
    public void testRightPush(){

    }

    @Test
    public void testRightPushIfPresent(){

    }

    @Test
    public void testRightPushAll(){

    }

    @Test
    public void testLeftPop(){

    }

    @Test
    public void testRightPop(){

    }

    @Test
    public void testRightPopAndLeftPush(){

    }

    @Test
    public void testRemove(){

    }

    @Test
    public void testSize(){

    }

    @Test
    public void testIndex(){

    }

    @Test
    public void testSet(){

    }

    @Test
    public void testRange(){

    }

    @Test
    public void testTrim(){

    }

}
