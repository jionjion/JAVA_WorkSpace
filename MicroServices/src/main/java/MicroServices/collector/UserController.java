package MicroServices.collector;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import MicroServices.bean.User;
import MicroServices.dao.UserRepository;
import MicroServices.domain.ResultMessage;
import MicroServices.enums.ResultEnum;
import MicroServices.exception.UserException;
import MicroServices.tool.ResultUtil;


/**	用户的控制层类*/
@RestController
@RequestMapping("/user")
@Transactional			//事务接口,表示该类同时为事务
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	
	/**	查询全部
	 * 	URL: http://localhost:8080/MicroServices/user/users
	 * */
	@GetMapping(value="/users")			//get方式,获得全部信息
	public List<User> userList() {
	
		return userRepository.findAll();
	}
	
	/**	新增一个
	 * 	URL: http://localhost:8080/MicroServices/user/user
	 */
	@PostMapping(value="/user")		//post方式,添加信息
	@Transactional						//事务接口,表示该方法为事务
	public ResultMessage<User>	userSave(@Valid User user ,BindingResult result) {	//@Valid表示进行对象参数验证	BindingResult:为参数的验证结果
		ResultMessage<User> resultMessage = new ResultMessage<User>();//封装的结果对象
		if (result.hasErrors()) {		//如果有错误,则返回bean中的定义
			String message = result.getFieldError().getDefaultMessage();
			return ResultUtil.error(400, message);
		}
		user.setUsername(user.getUsername());	//传入User对象,前台只需要传递对象的属性名即可
		user.setPassword(user.getPassword());
		return ResultUtil.success(userRepository.save(user));
	}
	
	/**	查询一个
	 * 	URL: http://localhost:8080/MicroServices/user/users/1  
	 * */
	@GetMapping(value="/users/{id}")			//get方式,获得全部信息
	public User userGet(@PathVariable("id") Integer id) {
		
		return userRepository.findOne(id);
	}
	
	/**	更新一个
	 * 	URL: http://localhost:8080/MicroServices/user/users/3 
	 */
	@PutMapping(value="/users/{id}")			//put方式,获得全部信息
	public User userUpdate(	@PathVariable("id") Integer id,
							@RequestParam("username") String username,
							@RequestParam("password") String password) {
		
		User user = new User();
		user.setId(id); 						//保存主键
		user.setUsername(username);
		user.setPassword(password);
		return userRepository.save(user);		//当保存主键一致时,为更新操作
	}
	
	/**	删除一个
	 * 	URL: http://localhost:8080/MicroServices/user/users/3  
	 * */
	@DeleteMapping(value="/users/{id}")			//delete方式,获得全部信息
	public void userDelete(@PathVariable("id") Integer id) {
		userRepository.delete(id);
	}
	
	
	/**	自定义查询,通过姓名查询用户
	 * 	URL: http://localhost:8080/MicroServices/user/users/username/Jion  
	 * */
	@GetMapping(value="/users/username/{username}")			//get方式,获得全部信息,注意路径不要冲突
	public List<User> userGetByUsername(@PathVariable("username") String username) {
		
		return  userRepository.findByUsername(username);
	}
	
	/**
	 * 	统一异常捕获.
	 * 	将service层抛出的异常,dao层抛出的异常进行统一向外抛出,交由ExceptionHandle类进行包装向前台传递
	 * 	这里模拟包含的类进行的异常抛出
	 * 	URL:http://localhost:8080/MicroServices/user/error/2			调用第二种异常,并返回
	 * */
	@GetMapping(value="/error/{code}")			//get方式,获得全部信息,注意路径不要冲突
	public void errorCodeHandler(@PathVariable("code") String code) throws Exception{
		if ("1".equals(code)) {
			throw new UserException(ResultEnum.ONE_ERROR);				//抛出自定义的异常.完成统一异常处理
		}
		if ("2".equals(code)) {
			throw new UserException(ResultEnum.TWO_ERROR);
		}
			throw new UserException(ResultEnum.OTHER_ERROR);
	}
}
