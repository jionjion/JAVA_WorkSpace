package annotationBean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**	ѧ��������
 * 	��Ϊһ��һ��Э����*/
@Entity
@Table
public class AStudentCard {

	@Id
	@GeneratedValue(generator="cid")						//����������
	@GenericGenerator(name="cid",strategy="assigned")		//�Զ�������������
	@Column(length=12)										//����
	private String cid;
	
	private String description;
	
	@OneToOne(mappedBy="studentCard")						//���ط�������
	private AStudent student;								//˫�������ϵ

	public AStudent getStudent() {
		return student;
	}

	public void setStudent(AStudent student) {
		this.student = student;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public AStudentCard() {
		super();
	}	
	
	
}