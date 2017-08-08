package MicroServices.domain;
/**	后台返回到*/
public class ResultMessage<T> {

	private Integer code;
	
	private String message;
	
	private T date;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getDate() {
		return date;
	}

	public void setDate(T date) {
		this.date = date;
	}
	
	
}
