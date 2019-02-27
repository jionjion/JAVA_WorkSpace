package com.jionjion.guider.controller;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 14345
 *	测试用户类,通过mockMVC模拟请求,与获得响应
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserControllerTest {

	// ICO容器
	@Autowired
	WebApplicationContext context;

	// Session,容器维护
	@Autowired
	MockHttpSession session;
	
	// 模拟数据
	MockMvc mockMVC;
	
	
	/** 初始化mockMVC */
	@Before
	public void init() {
		mockMVC = MockMvcBuilders.webAppContextSetup(context).build();
		// Session,请求token为1
		session.setAttribute("token", "1");
	}
	
	
	/**
	 * 	通过token读取用户头像信息
	 */
	@Test
	public void testHeaderImageGet() throws Exception {
		
		MvcResult result = mockMVC.perform(
										MockMvcRequestBuilders
											.get("/api/image/header/1")  // 访问路径,携带路径参数
											.session(session)	// 构建session
								 )
								.andExpect(status().isOk())	// 请求成功
							//	.andDo(MockMvcResultHandlers.print())  //打印结果,太长的返回结果不作打印
								.andReturn(); // 返回结果
		
		// 请求
		MockHttpServletRequest request = result.getRequest();
		String uri = request.getRequestURI();
		log.info("请求地址:" + uri);
		// 响应
		MockHttpServletResponse response = result.getResponse();
		int status = response.getStatus();
		log.info("响应状态码:" + status);
		String content = response.getContentAsString();
		// 响应内容
		assertNotNull(content);
	}

}

