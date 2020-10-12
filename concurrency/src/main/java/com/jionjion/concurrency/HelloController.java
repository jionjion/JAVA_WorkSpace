package com.jionjion.concurrency;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *	测试密集访问
 */
@RestController
@Slf4j	//日志
public class HelloController {

	@RequestMapping("hello")
	@ResponseBody
	public String sayHello() {
		return "Hello World";
	} 
	
	
	/** 日志测试  */
	@Test
	public void loggerTest(){
		Logger logger = LoggerFactory.getLogger(HelloController.class);
		logger.debug("日志....");
		logger.warn("日志....");
		logger.info("日志....");
		logger.error("日志....");
		logger.trace("日志....");
	}
}
