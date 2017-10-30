package MicroServices.exception;

import MicroServices.enums.ResultEnum;

/**
 *	自定义运行时异常,完成对SpringBoot框架的事务回滚触发
 *	同时触发同意异常处理
 */
public class UserException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/**自定义异常代码*/
	private Integer code;
	
	public UserException(ResultEnum resultEnum) {				//将枚举类传入,作为构造方法的参数
		super(resultEnum.getMessage());			//调用父类的构造方法
		this.code = resultEnum.getCode();		//追加两个参数的构造方法
	}

	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
}
