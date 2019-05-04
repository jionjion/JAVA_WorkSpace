package top.jionjion.api.controller.auth;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import top.jionjion.api.BaseTest;

import static org.junit.Assert.assertNotNull;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Jion
 * 用户测试类
 */
@Slf4j
public class UserControllerTest extends BaseTest {


    // 模拟数据
    @Autowired
    private MockMvc mockMVC;

    /**
     * 测试查询方法
     */
    @Test
    public void testGetUser() throws Exception {
        // JSON对象
        JSONObject json = new JSONObject();
        json.put("username", "JionJion");
        json.put("password", "123456");

        // 发送请求
        MvcResult result = mockMVC.perform(
                MockMvcRequestBuilders
                        .post("/auth/user/info")  // 访问路径
                        .contentType(MediaType.APPLICATION_JSON_UTF8) // json格式数据
                        .content(json.toString())
        )
                .andExpect(status().isOk())    // 请求成功
                .andDo(MockMvcResultHandlers.print())  // 打印结果,太长的返回结果不作打印
                .andDo(document("用户认证")    // 导出说明文档


                )
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

    /**
     * 测试查询方法,查询一个不存在用户
     */
    @Test
    public void testGetUserNotExist() throws Exception {
        // JSON对象
        JSONObject json = new JSONObject();
        json.put("username", "XXX");
        json.put("password", "123");

        // 发送请求
        MvcResult result = mockMVC.perform(
                MockMvcRequestBuilders
                        .post("/auth/user/info")  // 访问路径
                        .contentType(MediaType.APPLICATION_JSON_UTF8) // json格式数据
                        .content(json.toString())
        )
                .andExpect(status().isOk())    // 请求成功
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

    /**
     * 测试保存方法
     */
    public void testAddUser() throws Exception {
        // JSON对象
        JSONObject json = new JSONObject();
        json.put("username", "admin");
        json.put("password", "123456");
    }
}