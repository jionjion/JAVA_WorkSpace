package exportExcel.core;
/**
 * 	模型类
 * @author JionJion
 */

import java.util.Date;

public class Student {

	/** 主键编号 */
	private Integer studentID;
	
	/** 姓名 */
	private String studentName;
	
	/** 年龄 */
	private Integer studentAge;
	
	/** 性别 */
	private String studentSix;
	
	/** 生日 */
	private Date birthday;
	
	/** 爱好 */
	private String hobby;
	
	/** 地址,为空,不做导出 */
	private String address;
	
	/** 介绍,不做导出 */
	public String introduce;

	public Integer getStudentID() {
		return studentID;
	}

	public void setStudentID(Integer studentID) {
		this.studentID = studentID;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public Integer getStudentAge() {
		return studentAge;
	}

	public void setStudentAge(Integer studentAge) {
		this.studentAge = studentAge;
	}

	public String getStudentSix() {
		return studentSix;
	}

	public void setStudentSix(String studentSix) {
		this.studentSix = studentSix;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getHobby() {
		return hobby;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}	
	
	public Student() {
		super();
	}

	public Student(Integer studentID, String studentName, Integer studentAge, String studentSix, Date birthday,
			String hobby, String introduce) {
		super();
		this.studentID = studentID;
		this.studentName = studentName;
		this.studentAge = studentAge;
		this.studentSix = studentSix;
		this.birthday = birthday;
		this.hobby = hobby;
		this.introduce = introduce;
	}

	@Override
	public String toString() {
		return "Student [studentID=" + studentID + ", studentName=" + studentName + ", studentAge=" + studentAge
				+ ", studentSix=" + studentSix + ", birthday=" + birthday + ", hobby=" + hobby + "]";
	}
}
