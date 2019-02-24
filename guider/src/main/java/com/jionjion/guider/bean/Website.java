package com.jionjion.guider.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author 14345
 *	站点类
 */
@Entity
@Table(name="WEBSITE")
public class Website {
	
	/** 主键  */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Integer siteId;
	
	/** 站点名称 */
	@Column(name="NAME")
	private String siteName;
	
	/** 站点URI */
	@Column(name="URI")
	private String siteUri;

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSiteUri() {
		return siteUri;
	}

	public void setSiteUri(String siteUri) {
		this.siteUri = siteUri;
	}

	@Override
	public String toString() {
		return "Website [siteId=" + siteId + ", siteName=" + siteName + ", siteUri=" + siteUri + "]";
	}
	
}
