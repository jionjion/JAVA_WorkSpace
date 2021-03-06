package annotationBean;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;			//JAVAX表示为JPA注解
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;



/**	通过注解配置的学生类
 * 	必须指定注解*/
@Entity									//默认该实体映射的表名为类名
@Table(name="astudent",schema="hibernate")					//指定表名
public class AStudent implements Serializable{				//多个主键必须实现序列化接口
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)			//主键生成策略,默认根据底层数据库自动选择
	private int sid;
	
	@OneToOne(cascade=CascadeType.ALL)						//设置级联关系
	@JoinColumn(name="cid",unique=true)						//关联被控类主键,是否唯一
	private AStudentCard studentCard;						//主控方保留被控方引用
	
//	@Id														//@ID多个构成联合主键
	@Column(length=10,name="sname")							//字段注解
	private String sname;
	
//	@EmbeddedId												//声明为联合主键
//	private AStudentPK aStudentPK;							//联合主键类
	
	private String gender;
	
	@OneToMany(cascade={CascadeType.ALL},fetch=FetchType.LAZY)		//级联关系,加载策略
	@JoinColumn(name="gid")											//关联关系为一方的主键
	private Set<APupil> pupils = new HashSet<APupil>();				//一对多
	
	private Date birthday;
	
	@Transient												//不进行映射
	private String addree;

	private Blob picture;
	
	@Embedded
	private AAddress address;								//表示引入的属性对象

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getAddree() {
		return addree;
	}

	public void setAddree(String addree) {
		this.addree = addree;
	}

	public Blob getPicture() {
		return picture;
	}

	public void setPicture(Blob picture) {
		this.picture = picture;
	}

	public AAddress getAAddress() {
		return address;
	}

	public void setAAddress(AAddress address) {
		this.address = address;
	}

	public AStudent() {
		super();
	}

	
	public AStudentCard getStudentCard() {
		return studentCard;
	}

	public void setStudentCard(AStudentCard studentCard) {
		this.studentCard = studentCard;
	}

	public AAddress getAddress() {
		return address;
	}

	public void setAddress(AAddress address) {
		this.address = address;
	}

	
	public AStudent(int sid, AStudentCard studentCard, String sname, String gender, Date birthday, String addree,
			Blob picture, AAddress address) {
		super();
		this.sid = sid;
		this.studentCard = studentCard;
		this.sname = sname;
		this.gender = gender;
		this.birthday = birthday;
		this.addree = addree;
		this.picture = picture;
		this.address = address;
	}

	public AStudent(int sid, String sname, String gender, Date birthday, String addree, Blob picture, AAddress address) {
		super();
		this.sid = sid;
		this.sname = sname;
		this.gender = gender;
		this.birthday = birthday;
		this.addree = addree;
		this.picture = picture;
		this.address = address;
	}
	
	public AStudent(int sid, String sname,String gender , Date birthday, String addree) {
		super();
		this.sid = sid;
		this.sname = sname;
		this.gender = gender;
		this.birthday = birthday;
		this.addree = addree;
	}


	@Override
	public String toString() {
		return "AStudent [sid=" + sid + ", sname=" + sname + ", gender=" + gender + ", birthday=" + birthday
				+ ", addree=" + addree + ", picture=" + picture + ", address=" + address + "]";
	}
	
	
}
