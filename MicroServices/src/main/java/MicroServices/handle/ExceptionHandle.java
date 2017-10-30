package MicroServices.handle;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import MicroServices.domain.ResultMessage;
import MicroServices.enums.ResultEnum;
import MicroServices.exception.UserException;
import MicroServices.tool.ResultUtil;

/**	异常捕获的类,通过将异常通知捕获,并向外包装返回,方便前台接收*/
@ControllerAdvice 		//控制层的通知
public class ExceptionHandle {

	@ExceptionHandler(value=Exception.class)			//处理异常的类,这里将异常统一捕获,完成分类处理
	@ResponseBody
	public ResultMessage<?> handle(Exception exception) {
		if (exception instanceof UserException){				//如果属于自定义的异常
			UserException e = (UserException) exception;		//强制类型转换
			return ResultUtil.error(e.getCode(), e.getMessage());	//将抛出的异常捕获后包装
		}
		
		return ResultUtil.error(ResultEnum.ERROR_UNKNOWN.getCode(),ResultEnum.ERROR_UNKNOWN.getMessage());				//如果不是自动返回系统的异常
	}
}
