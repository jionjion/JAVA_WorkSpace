package com.jionjion.guider.dao.japRepository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.Repository;

import com.jionjion.guider.bean.User;


/**
 * @author 14345
 * 	用户类
 */
@Transactional	// 不添加事务不会保存,注意事务的级别
public interface UserRepository extends Repository<User,String>{

	/** 通过UUID查询 */
	public User findByUuid(String uuid);
	
	/** 通过用户ID查询 */
	public User findById(Integer id);
	
	/** 通过用户名查询 */
	public User findByUsername(String username);
	
	/** 查询全部 */
	public List<User> findAll();
	
	/** 保存 */
	public User save(User user);
	
	/** 删除 */
	public Integer deleteById(Integer id);
	
}
