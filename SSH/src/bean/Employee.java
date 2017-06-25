package bean;

import java.io.Serializable;
import java.util.Date;

public class Employee implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer eid;
	
	private String ename;
	
	private String esex;
	
	private Date ebirthday;
	
	private Date ejoinDate;
	
	private Integer eno;
	
	private String epassword;
	
	private Department department;

	public Integer getEid() {
		return eid;
	}

	public void setEid(Integer eid) {
		this.eid = eid;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getEsex() {
		return esex;
	}

	public void setEsex(String esex) {
		this.esex = esex;
	}

	public Date getEbirthday() {
		return ebirthday;
	}

	public void setEbirthday(Date ebirthday) {
		this.ebirthday = ebirthday;
	}

	public Date getEjoinDate() {
		return ejoinDate;
	}

	public void setEjoinDate(Date ejoinDate) {
		this.ejoinDate = ejoinDate;
	}

	public Integer getEno() {
		return eno;
	}

	public void setEno(Integer eno) {
		this.eno = eno;
	}

	public String getEpassword() {
		return epassword;
	}

	public void setEpassword(String epassword) {
		this.epassword = epassword;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Employee() {
		super();
	}

	public Employee(String ename, String esex, String epassword, Department department) {
		super();
		this.ename = ename;
		this.esex = esex;
		this.epassword = epassword;
		this.department = department;
	}

	@Override
	public String toString() {
		return "Employee [eid=" + eid + ", ename=" + ename + ", esex=" + esex + ", ebirthday=" + ebirthday
				+ ", ejoinDate=" + ejoinDate + ", eno=" + eno + ", epassword=" + epassword + ", department="
				+ department + "]";
	}
	
	
}
