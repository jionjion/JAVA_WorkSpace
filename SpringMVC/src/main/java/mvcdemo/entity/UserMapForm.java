package mvcdemo.entity;

import java.util.Map;

import mvcdemo.bean.User;

public class UserMapForm {

	private Map<String, User> users;

	public Map<String, User> getUsers() {
		return users;
	}

	public void setUsers(Map<String, User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "UserMapForm [users=" + users + "]";
	}
	
	
}
