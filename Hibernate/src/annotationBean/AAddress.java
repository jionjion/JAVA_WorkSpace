package annotationBean;

import javax.persistence.Embeddable;

/**
 *	组件对象,使用注解配置
 */
@Embeddable			//表示当前类为一个嵌入,作为组件属性
public class AAddress {

	private String postCode;	//邮编
	private String phone;		//电话
	private String address;		//地址
	
	public AAddress() {}

	public AAddress(String postCode, String phone, String address) {
		super();
		this.postCode = postCode;
		this.phone = phone;
		this.address = address;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
