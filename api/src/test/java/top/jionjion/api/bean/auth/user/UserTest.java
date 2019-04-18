package top.jionjion.api.bean.auth.user;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *  测试方法
 *  @author Jion
 */
@RunWith(SpringRunner.class) // 测试环境
@SpringBootTest // 测试
//@Transactional // 开启事物,可以在测试方法中,使用@Rollback回滚
@Slf4j
public class UserTest {

    @Test
    public void testToString(){
        User user = new User();
        user.setUsername("Jion");
        user.setPassword("12345");
        user.setToken("123456789abcedf");
        log.info("用户 >>" + user.toString());
    }
}