package MicroServices.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import MicroServices.bean.User;

/**	接口,调用jpa
 * 	在该接口中,实现了父接口的各种方法
 * 	也可以自己自定义实现特定的接口*/

public interface UserRepository extends JpaRepository<User, Integer> {


	/**根据名字查询*/
	public List<User> findByUsername(String usename);		//注意命名规范
}
