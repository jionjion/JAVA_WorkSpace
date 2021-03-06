package annotationBean;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *	学生类,为多方.
 */
@Entity
public class APupil implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int pid;																//学生类主键
	
	private String pname;
	
	private String psex;
	
	@ManyToOne(cascade={CascadeType.ALL},fetch=FetchType.EAGER)						//级联关系和抓取策略
	@JoinColumn(name="gid",referencedColumnName="gid")								//表中字段和映射属性										
	private AGrade grade;															//多方持有一方的引用
	
	@ManyToMany()																	//多对多配置
	@JoinTable(name="teacher_students",												//表名
				joinColumns={@JoinColumn(name="sid")},								//关联的外键
				inverseJoinColumns={@JoinColumn(name="tid")})
	private Set<ATeacher> teachers;													//多对多配置,引入多方的集合
	
	public APupil(){}
	
	public APupil(int pid, String pname, String psex) {
		super();
		this.pid = pid;
		this.pname = pname;
		this.psex = psex;
	}


	public Set<ATeacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(Set<ATeacher> teachers) {
		this.teachers = teachers;
	}

	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getPsex() {
		return psex;
	}
	public void setPsex(String psex) {
		this.psex = psex;
	}

	
	public AGrade getGrade() {
		return grade;
	}

	public void setGrade(AGrade grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "Pupil [pid=" + pid + ", pname=" + pname + ", psex=" + psex +  "]";
	}
	
	
}
