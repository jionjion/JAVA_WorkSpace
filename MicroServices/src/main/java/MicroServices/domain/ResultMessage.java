package MicroServices.domain;
/**	后台返回到*/
public class ResultMessage<T> {
	//代表消息状态
	private Integer code;
	//详细内容
	private String message;
	//存放数据
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
