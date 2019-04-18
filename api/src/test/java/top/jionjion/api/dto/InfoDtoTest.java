package top.jionjion.api.dto;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import top.jionjion.api.enums.ApiResultStatus;

import static org.junit.Assert.*;

/**
 * @author Jion
 *  测试输出为JSON字符串
 */

@Slf4j
public class InfoDtoTest {

    @Test
    public void jsonString() throws JsonProcessingException {
        InfoDto infoDto = new InfoDto(ApiResultStatus.SUCCESS,"执行成功!");
        String result = new ObjectMapper().writeValueAsString(infoDto);
        log.info(result);
    }
}