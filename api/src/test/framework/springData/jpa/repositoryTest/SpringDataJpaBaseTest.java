package springData.jpa.repositoryTest;

import org.junit.runner.RunWith;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

/**
 * @author Jion
 *  Spring-Data-JPA的测试基类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ConfigurationProperties("classpath:config/description.properties")
public class SpringDataJpaBaseTest {

}
