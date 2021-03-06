package annotationBean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**	学生卡对象
 * 	作为一对一的协助方*/
@Entity
@Table
public class AStudentCard {

	@Id
	@GeneratedValue(generator="cid")						//主键生成器
	@GenericGenerator(name="cid",strategy="assigned")		//自定义主键生成器
	@Column(length=12)										//长度
	private String cid;
	
	private String description;
	
	@OneToOne(mappedBy="studentCard")						//主控方的引用
	private AStudent student;								//双向关联关系

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
