package annotationBean;

import javax.persistence.Embeddable;

/**
 *	�������,ʹ��ע������
 */
@Embeddable			//��ʾ��ǰ��Ϊһ��Ƕ��,��Ϊ�������
public class AAddress {

	private String postCode;	//�ʱ�
	private String phone;		//�绰
	private String address;		//��ַ
	
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