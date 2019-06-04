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
        redisTemplate.exec();

        listOperations.leftPush();
        listOperations.leftPushAll(); X2
        listOperations.rightPush();
        listOperations.rightPushAll(); X2
        listOperations.leftPop(); X2
        listOperations.rightPop(); X2
        listOperations.rightPopAndLeftPush(); X2;
        listOperations.remove();
        listOperations.size();
        listOperations.index();
        listOperations.set();
        listOperations.range();
        listOperations.trim();


    }



}
