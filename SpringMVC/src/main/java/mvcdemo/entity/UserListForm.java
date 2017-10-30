package mvcdemo.entity;

import java.util.List;

import mvcdemo.bean.User;

/**对实体类进行包装*/
public class UserListForm {

	private List<User> users;

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "UserListForm [users=" + users + "]";
	}
}
