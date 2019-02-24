package com.jionjion.guider.enums;

/**
 * @author 14345
 *	请求返回结果枚举
 */
public enum ResultEnum {
	
	SUCCESS(200,"Success"),
	ERROR(400,"Error");

	private Integer code;
	
	private String message;
	
	private ResultEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
