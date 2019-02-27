package com.jionjion.guider.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;


/**
 * @author 14345
 *	站点类
 */
@Entity
@Table(name="USER")
public class User implements Serializable {

	/** 版本 */
	private static final long serialVersionUID = 1L;

	/** UUID作为用户主键  */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Getter
	private String uuid;
	
	/** 自增用户id */
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	@Getter
	private Integer id;
	
	/** 用户角色 */
	@Column(name="RULE_CODE")
	@Getter
	@Setter
	private String ruleCode;
	
	/** 用户名 */
	@Column(name="USERNAME")
	@Getter
	@Setter	
	private String username;

	/** 用户密码 */
	@Column(name="PASSWORD")
	@Getter
	@Setter	
	private String password;
	
	/** 邮箱 */
	@Column(name="EMAIL")
	@Getter
	@Setter	
	private String email;
	
	/** 手机 */
	@Column(name="PHONE")
	@Getter
	@Setter	
	private String phone;
	
	/** 地址 */
	@Column(name="ADDRESS")
	@Getter
	@Setter	
	private String address;
	
	@Getter
	@Setter	
	@Transient
	private String token;
}
