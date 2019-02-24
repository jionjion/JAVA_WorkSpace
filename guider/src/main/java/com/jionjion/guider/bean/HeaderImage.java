package com.jionjion.guider.bean;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 14345 头像图片对象
 */
@Component
@Entity
@Table(name = "header_image")
@Scope("prototype")
public class HeaderImage extends FileObject {

	
	
	/** 文件UUID */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Getter
	private String uuid;

	/** 文件名 */
	@Column(name = "file_name")
	@Getter
	@Setter	
	private String fileName;
	
	
	/** 文件大小 */
	@Column(name = "file_length")
	@Getter
	@Setter
	private Long fileLength;

	/** 文件类型 */
	@Column(name = "file_type")
	@Getter
	@Setter
	private String fileType;
	
	/** 相对于文件根路径,图片文件存放路径 */
	@Column(name = "file_path")
	@Getter
	@Setter	
	private String filePath;
	
	/** 实际文件 */
	@Getter
	@Setter
	@Transient
	private File file;


}
