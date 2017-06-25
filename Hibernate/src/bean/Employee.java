package bean;

import java.util.HashSet;
import java.util.Set;

/**
 *	多对多的实体类
 *	员工信息
 */
public class Employee {

	private int empId;
	
	private String empName;

	//增加一个项目的结合
	private Set<Project> projects = new HashSet<Project>(); 
	
	public Employee() {
		super();
	}
	
	public Employee(int empId, String empName) {
		super();
		this.empId = empId;
		this.empName = empName;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", empName=" + empName + ", projects=" + projects + "]";
	}
	
	
	
	
}
