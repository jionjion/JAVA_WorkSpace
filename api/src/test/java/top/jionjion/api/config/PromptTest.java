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
public class PromptTest extends BaseTest {

    @Autowired
    Prompt prompt;

    @Test
    public void test(){
      log.info(prompt.getAreYouSure());
    }
}