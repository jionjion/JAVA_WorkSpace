package top.jionjion.demo.log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Jion
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoLogTest {

    @Autowired
    DemoLog log;

    @Test
    public void testLog(){
        log.testLog();
    }
}