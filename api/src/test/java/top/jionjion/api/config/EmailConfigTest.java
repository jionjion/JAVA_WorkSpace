package top.jionjion.api.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.jionjion.api.BaseTest;

import static org.junit.Assert.*;

/**
 * @author Jion
 */
@Slf4j
public class EmailConfigTest extends BaseTest {

    @Autowired
    private EmailConfig emailConfig;

    @Test
    public void test(){
      log.info(emailConfig.toString());
      assertNotNull(emailConfig);
    }
}