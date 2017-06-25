package mvcdemo.bean;

public class Teacher {

	private String username;
	
	private User user;

	public String getusername() {
		return username;
	}

	public void setusername(String username) {
		this.username = username;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Teacher [username=" + username + ", user=" + user + "]";
	}
	
	
}
