package top.jionjion.api.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 *  @author Jion
 *  测试属性文件读取
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class InfoTest {

    @Autowired
    private Info info;

    /** 测试属性文件读取 */
    @Test
    public void test(){
        // 只能通过容器获得
        //Info info = new Info();
        log.info("信息类" + info);
        log.info("数组" + Arrays.toString(info.getCnNumbers()));
        log.info("列表" + info.getAddressList());
        log.info("键值对" + info.getMap());
    }
}