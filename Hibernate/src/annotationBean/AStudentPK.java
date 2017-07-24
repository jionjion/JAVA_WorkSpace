package annotationBean;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**	学生类的联合主键*/
@Embeddable
public class AStudentPK implements Serializable{

	private static final long serialVersionUID = 1L;

	private int sid ;	//学号
	
	private int id ;	//身份证号

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AStudentPK() {
		super();
	}
	
	
}
