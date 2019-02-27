package com.jionjion.guider.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.jionjion.guider.bean.User;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 14345
 *	测试登录权限
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class LoginControllerTest {

	// 模拟数据
	@Autowired
	MockMvc mockMVC;
	
	@Autowired
	MockHttpSession session;
	
	/** 登录测试
	 * 	登录时携带JSON对象 */
	@Test
	public void testLogin() throws Exception {
		
		// JSON对象
		JSONObject json = new JSONObject();
		json.put("username", "JionJion");
		json.put("password", "123456");
		
		
		MvcResult result = mockMVC.perform(
										MockMvcRequestBuilders
											.post("/api/login")  // 访问路径
											.contentType(MediaType.APPLICATION_JSON_UTF8) // json格式数据
											.content(json.toString())
											.session(session)
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
		
		// Session信息
		HttpSession session = request.getSession();
		User userInfo = (User) session.getAttribute("userInfo");
		log.info("token信息:" + userInfo.getToken());
		
	}

	@Test
	public void testLogout() {
		fail("Not yet implemented");
	}

}
