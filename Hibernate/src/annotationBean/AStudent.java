package annotationBean;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;			//JAVAX��ʾΪJPAע��
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;



/**	ͨ��ע�����õ�ѧ����
 * 	����ָ��ע��*/
@Entity									//Ĭ�ϸ�ʵ��ӳ��ı���Ϊ����
@Table(name="astudent",schema="hibernate")					//ָ������
public class AStudent implements Serializable{				//�����������ʵ�����л��ӿ�
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)			//�������ɲ���,Ĭ�ϸ��ݵײ����ݿ��Զ�ѡ��
	private int sid;
	
	@OneToOne(cascade=CascadeType.ALL)						//���ü�����ϵ
	@JoinColumn(name="cid",unique=true)						//��������������,�Ƿ�Ψһ
	private AStudentCard studentCard;						//���ط��������ط�����
	
//	@Id														//@ID���������������
	@Column(length=10,name="sname")							//�ֶ�ע��
	private String sname;
	
//	@EmbeddedId												//����Ϊ��������
//	private AStudentPK aStudentPK;							//����������
	
	private String gender;
	
	@OneToMany(cascade={CascadeType.ALL},fetch=FetchType.LAZY)		//������ϵ,���ز���
	@JoinColumn(name="gid")											//������ϵΪһ��������
	private Set<APupil> pupils = new HashSet<APupil>();				//һ�Զ�
	
	private Date birthday;
	
	@Transient												//������ӳ��
	private String addree;

	private Blob picture;
	
	@Embedded
	private AAddress address;								//��ʾ��������Զ���

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