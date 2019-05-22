package springData.jpa.bean;


import javax.persistence.*;
import java.util.Date;

/**
 *	数据库表对应的实体类
 *	注意使用包装类型替换基本类型
 *	数据库表在创建时随项目启动而创建
 *	JPA定义规范,Hibernate各种实现,ORM框架映射
 */
@Entity
@Table(name="STUDENT")
public class Student {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int id;


	@Column(name = "NAME")
	private String name;

	@Column(name = "AGE")
	private int age;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "BIRTHDAY")
	private Date birthday;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", age=" + age + ", address=" + address + ", birthday="
				+ birthday + "]";
	}

}
