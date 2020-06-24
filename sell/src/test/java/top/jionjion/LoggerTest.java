package top.jionjion;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *  测试日志组件
 *      日志级别
 *           ERROR(40, "ERROR"),
 *           WARN(30, "WARN"),
 *           INFO(20, "INFO"),
 *           DEBUG(10, "DEBUG"),
 *           TRACE(0, "TRACE");
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
//@Data
public class LoggerTest {

    // org.slf4j 日志组件,每次需要初始化当前类才能调用日志对象;也可以采用注解获得日志对象,@Slf4j需要下载lombok小工具
    //private final Logger log = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void test(){
        log.debug("debug级别...{}","默认不输出!");
        log.info("info级别...{}","默认输出以上级别");
        log.warn("warn级别...");
        log.error("error级别...");
    }

    /*
        日志文件的配置
            默认application.yml文件行进配置,或者使用logback-spring.xml进行配置
            - 区分info和error日志
            - 每天产生一个日志文件

     */
}
