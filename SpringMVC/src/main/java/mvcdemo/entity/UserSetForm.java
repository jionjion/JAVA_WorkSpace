package mvcdemo.entity;
import java.util.LinkedHashSet;
/**使用set集合,进行数据的传递*/
import java.util.Set;

import mvcdemo.bean.User;

public class UserSetForm {

	private Set<User> users;

	private UserSetForm(){
		users = new LinkedHashSet<>();
		users.add(new User());			//初始化set
		users.add(new User());
	}
	
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "UserSetForm [users=" + users + "]";
	}
}
