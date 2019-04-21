package top.jionjion.api;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

/**
 * @author Jion
 *  基础的测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public abstract class BaseTest {

}
