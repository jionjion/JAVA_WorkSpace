package com.jionjion.guider.dto;

import com.jionjion.guider.enums.ResultEnum;

/**
 * @author 14345
 *	将结果作为JSON字符串返回
 * @param <T> 数据对象
 */
public class ResultJsonDto<T> {

	private Integer code;
	
	private String message;
	
	private T date;

	/** 私有构造属性 */
	private ResultJsonDto() {
		
	}
	
	/** 私有构造属性 */
	private ResultJsonDto(Integer code, String message, T date) {
		super();
		this.code = code;
		this.message = message;
		this.date = date;
	}
	
	/** 返回成功对象 */
	public static ResultJsonDto<?> returnSuccessDto(Object date){
		return new ResultJsonDto<Object>(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMessage(),date);
	}
	
	/** 返回失败对象 */
	public static ResultJsonDto<?> returnErrorDto(Object date){
		return new ResultJsonDto<Object>(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMessage(),date);
	}

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
