package springData.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *	数据库表对应的实体类
 *	注意使用包装类型替换基本类型
 *	数据库表在创建时随项目启动而创建
 *	JPA定义规范,Hibernate各种实现,ORM框架映射
 */
@Entity
public class Teacher {

	@Id
	@GeneratedValue
	private Integer id;
	
	private String name;
	
	private Date workday;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getWorkday() {
		return workday;
	}

	public void setWorkday(Date workday) {
		this.workday = workday;
	}

	@Override
	public String toString() {
		return "Teacher [id=" + id + ", name=" + name + ", workday=" + workday + "]";
	}
}
