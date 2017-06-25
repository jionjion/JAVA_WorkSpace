package bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Department implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer did;
	
	private String dname;
	
	private String ddesc;
	//员工集合,用于双向维护
	private Set<Employee> employees = new HashSet<Employee>();
	
	public Integer getDid() {
		return did;
	}

	public void setDid(Integer did) {
		this.did = did;
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public String getDdesc() {
		return ddesc;
	}

	public void setDdesc(String ddesc) {
		this.ddesc = ddesc;
	}

	public Department() {
		super();
	}

	public Department(Integer did, String dname, String ddesc) {
		super();
		this.did = did;
		this.dname = dname;
		this.ddesc = ddesc;
	}

	@Override
	public String toString() {
		return "Department [did=" + did + ", dname=" + dname + ", ddesc=" + ddesc + "]";
	}
	
	
}
