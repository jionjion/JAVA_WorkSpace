package annotationBean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 *	班级,为一方
 */
@Entity
public class AGrade implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int gid;												//班级类主键
	
	private String gname;
	
	private String gdescribe;
	
	@OneToMany(cascade={CascadeType.ALL},fetch=FetchType.LAZY)		//级联关系,加载策略
	@JoinColumn(name="gid")											//关联关系为一方的主键
	private Set<APupil> pupils = new HashSet<APupil>();				//一对多

	public AGrade(){}

	public AGrade(int gid, String gname, String gdescribe, Set<APupil> pupils) {
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

	public Set<APupil> getPupils() {
		return pupils;
	}

	public void setPupils(Set<APupil> pupils) {
		this.pupils = pupils;
	}

	@Override
	public String toString() {
		return "Grade [gid=" + gid + ", gname=" + gname + ", gdescribe=" + gdescribe + ", pupils=" + pupils + "]";
	}
	
	
}
