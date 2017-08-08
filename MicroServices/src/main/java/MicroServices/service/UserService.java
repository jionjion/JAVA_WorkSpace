package MicroServices.service;

import org.springframework.stereotype.Service;

import MicroServices.bean.User;
public interface UserService {

	/**查询一个用户信息*/
	public User findOne(Integer id);
}
