package top.jionjion.demo.controller;

import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.jionjion.demo.exception.UserNotExitsException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 *  @author Jion
 *      异常处理器
 */
@ControllerAdvice
public class DemoExceptionHandler{

    /** 处理用户不存在异常 */
    @ExceptionHandler(UserNotExitsException.class)
    @ResponseBody
    public Map<String, Object> handleUserNotExitsException(UserNotExitsException e){
        Map<String, Object> model = new HashMap<>();
        model.put("code","user not found");
        model.put("message", e.getMessage());

        return model;
    }

    /** 自适应的异常处理. 浏览器响应页面,应用响应JSON */
    @ExceptionHandler(RuntimeException.class)
    public String handleException(Exception e, HttpServletRequest request){

        // 扩展异常处理信息,交由DemoDefaultErrorAttributes类,解析后响应
        Map<String, Object> model = new HashMap<>();
        model.put("code","user not found");
        model.put("message", e.getMessage());
        request.setAttribute("ext", model);

        // 设置默认的错误响应状态码
        request.setAttribute("javax.servlet.error.status_code",500);

        // 交由自定义异常解析器完成
        return "forward:/error";
    }

}
