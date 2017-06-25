package bean;

import java.util.HashSet;
import java.util.Set;

/**
 *	多对多的实体类
 *	项目信息
 */
public class Project {

	private int proId;
	
	private String proName;

	private Set<Employee> employees = new HashSet<Employee>();
	
	public Project() {
		super();
	}


	public Project(int proId, String proName) {
		super();
		this.proId = proId;
		this.proName = proName;
	}

	public int getProId() {
		return proId;
	}

	public void setProId(int proId) {
		this.proId = proId;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}

	@Override
	public String toString() {
		return "Project [proId=" + proId + ", proName=" + proName + ", employees=" + employees + "]";
	}

	
	
}
