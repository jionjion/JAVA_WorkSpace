package com.jionjion.guider.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.jionjion.guider.bean.User;
import com.jionjion.guider.dao.japRepository.UserRepository;

/** 用户服务 */
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	/** 通过UUID查询 */
	public User findByUuid(String uuid) {
		return userRepository.findByUuid(uuid);
	};
	
	/** 通过用户ID查询 */
	public User findById(Integer id) {
		return userRepository.findById(id);
	}
	
	/** 通过用户名查询 */
	public User findByUsername(String username){ 
		return userRepository.findByUsername(username);
	}
	
	/** 查询全部 */
	public List<User> findAll() {
		return userRepository.findAll();
	};
	
	/** 保存 */
	public User save(User user) {
		String password = user.getPassword();
		// 密码加密为MD5处理后存入
		String passwordDigest = digestPassword(password);
		user.setPassword(passwordDigest);
		
		return userRepository.save(user);
	};
	
	/** 删除 */
	public Integer deleteById(Integer id) {
		return userRepository.deleteById(id);
	};
	
	/** 加密用户密码 */
	public String digestPassword(String password) {
		String passwordDigest = DigestUtils.md5DigestAsHex(password.getBytes());
		return passwordDigest;
	}
	
	/** 生成当前用户的token */
	public String createToken() {
		UUID uuid = UUID.randomUUID();
		return DigestUtils.md5DigestAsHex(uuid.toString().getBytes());
	}
	
}
