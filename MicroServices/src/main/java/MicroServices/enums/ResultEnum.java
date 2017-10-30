package MicroServices.enums;
/**
 * 	定义一个枚举类,作为返回结果的key-value对应
 * 	枚举的创建方法:
 * 		1.追加分号
 * 		2.编写枚举成员
 * 		3.定义构造方法,使用全部成员变量,完成赋初值
 * 		4.提供get方法,返回初值.set方法一般不使用*/
public enum ResultEnum {
	ERROR_UNKNOWN(-1,"未知的错误"),
	ONE_ERROR(1,"第一种异常"),
	TWO_ERROR(2,"第二种异常"),
	OTHER_ERROR(0,"其他异常");									//第四步:根据构造器,预先创建各种生成好的枚举对象					
	private Integer code;									//第一步:枚举的成员
	
	private String message;
	
	private ResultEnum(Integer code, String message) {		//第二步:定义一个使用全部成员变量的构造方法
		this.code = code;
		this.message = message;
	}
	
	public Integer getCode() {								//第三步:定义get方法.
		return code;
	}

	public String getMessage() {
		return message;
	}
}
