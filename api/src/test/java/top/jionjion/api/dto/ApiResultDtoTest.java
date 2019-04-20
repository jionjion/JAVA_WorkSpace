package top.jionjion.api.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.jionjion.api.bean.auth.user.User;

/**
 * @author Jion
 *  测试Dto类是否符合要求
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ApiResultDtoTest {



    @Test
    public void test() throws JsonProcessingException {
        // 数据
        User user = new User();
        user.setUsername("Jion");
        user.setPassword("123456");
        user.setToken("Ox0001");
        DataDto dataDto = new DataDto(user);

        ApiResultDto apiResultDto = new ApiResultDto(dataDto);

        String result = new ObjectMapper().writeValueAsString(apiResultDto);
        log.info(result);

    }
}