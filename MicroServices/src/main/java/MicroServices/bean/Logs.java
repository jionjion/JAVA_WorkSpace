package MicroServices.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 	日志记录对象,保存用户的日志信息.*/
@Entity
public class Logs{

	@Id
	@GeneratedValue
	private Integer id;
	
	private String ip;
	
	private String url;
	
	private String method;

	private String className;
	
	private String methodName;
	
	private Object[] params;

	private String returns;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

	public String getRetruns() {
		return returns;
	}

	public void setRetruns(String returns) {
		this.returns = returns;
	}
	
}
