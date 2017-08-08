package MicroServices.service.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import MicroServices.bean.User;
import MicroServices.dao.UserRepository;
import MicroServices.service.UserService;
@Service
public class UserServiceImp implements UserService{

	@Autowired
	private UserRepository userRepository;
 	
	@Override
	public User findOne(Integer id) {
		return userRepository.findOne(id);
	}

	
}
