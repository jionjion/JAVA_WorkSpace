package top.jionjion.api.controller.auth;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import top.jionjion.api.bean.auth.user.User;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Jion
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class UserControllerTest {

    // 模拟数据
    @Autowired
    private MockMvc mockMVC;

    @Test
    public void testGetUser() throws Exception {
        // JSON对象
        JSONObject json = new JSONObject();
        json.put("username", "JionJion");
        json.put("password", "123456");

        MvcResult result = mockMVC.perform(
                MockMvcRequestBuilders
                        .post("/auth/user/info")  // 访问路径
                        .contentType(MediaType.APPLICATION_JSON_UTF8) // json格式数据
                        .content(json.toString())
        )
                .andExpect(status().isOk())	// 请求成功
                .andDo(MockMvcResultHandlers.print())  //打印结果,太长的返回结果不作打印
                .andReturn(); // 返回结果

        // 响应
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();
        log.info("登录返回:" + content);
        assertNotNull(content);

        // 请求
        MockHttpServletRequest request = result.getRequest();
        String requestURI = request.getRequestURI();
        log.info("请求地址:" + requestURI);
    }
}