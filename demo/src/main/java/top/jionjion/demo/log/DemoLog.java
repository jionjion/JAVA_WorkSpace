package top.jionjion.demo.log;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.stereotype.Component;

/**
 *  @author Jion
 *      日志类
 *      SLF4J是接口规范,LogBack是具体实现
 *      application.yml配置文件,为实现类的配置文件.
 */

@Component
@Slf4j
public class DemoLog {

    /** 日志测试 */
    public void testLog(){
        // 跟踪日志
        log.trace("这是 trace ...");
        // 调试日志
        log.debug("这是 debug ...");
        // 信息日志
        log.info("这是 info ...");
        // 警告日志
        log.warn("这是 warn ...");
        // 错误日志
        log.error("这是 error ...");
    }

}
