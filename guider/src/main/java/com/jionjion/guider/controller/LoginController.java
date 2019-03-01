package com.jionjion.guider.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jionjion.guider.bean.User;
import com.jionjion.guider.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 14345 登录及认证管理
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class LoginController {

	@Autowired
	private UserService userService;

	@Autowired
	private HttpSession session;

	/** 登录 */
	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String login(@RequestBody User user) {
		// 1.根据用户名查询数据库用户
		log.info("当前用户:" + user.toString());
		User dbUser = userService.findByUsername(user.getUsername());
		if(ObjectUtils.isEmpty(dbUser)) {
			return "用户不存在!";
		}
		// 2.存在,并验证用户名密码,不相等=>密码错误
		if (!ObjectUtils.nullSafeEquals(userService.digestPassword(user.getPassword()), dbUser.getPassword())) {
			return "密码错误!";
		}
		// 3.生成当前用户唯一token
		String token = userService.createToken();
		dbUser.setToken(token);
		
		// 4.将用户信息存入Session,生成当前token
		session.setAttribute("userInfo", dbUser);
		
		return "Success";
	}

	/** 登出 */
	public void logout() {
		
	}

}
