package bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *	班级,为一方
 */
public class Grade implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int gid;
	private String gname;
	private String gdescribe;
	//在一方设置多方的集合
	private Set<Pupil> pupils = new HashSet<Pupil>();

	public Grade(){}

	public Grade(int gid, String gname, String gdescribe, Set<Pupil> pupils) {
		super();
		this.gid = gid;
		this.gname = gname;
		this.gdescribe = gdescribe;
		this.pupils = pupils;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}

	public String getGdescribe() {
		return gdescribe;
	}

	public void setGdescribe(String gdescribe) {
		this.gdescribe = gdescribe;
	}

	public Set<Pupil> getPupils() {
		return pupils;
	}

	public void setPupils(Set<Pupil> pupils) {
		this.pupils = pupils;
	}

	@Override
	public String toString() {
		return "Grade [gid=" + gid + ", gname=" + gname + ", gdescribe=" + gdescribe + ", pupils=" + pupils + "]";
	}
	
	
}
