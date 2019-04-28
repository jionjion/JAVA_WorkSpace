package top.jionjion.api.exception;

import lombok.Getter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * @author Jion
 *  全局的API异常
 *      其他自定义的异常通过继承该类,抛出自定义异常.
 *      异常在controller层或者handler拦截器中处理
 */
public class ApiException extends RuntimeException{

    /** 全部的异常堆栈信息 */
    @Getter
    private String fullStackTraceMessage;

    /** 用于捕获系统异常时,包装成为自定义的异常 */
    public ApiException(String message, Exception exception){
        super(message);
        this.fullStackTraceMessage = getFullStackTraceMessage(exception);
    }


    /** 用于抛出自定义异常时,指定异常信息 */
    public ApiException(String message){
        super(message);
        this.fullStackTraceMessage = message;
    }

    /** 构造方法 */
    public ApiException(){}

    /** 获得异常的全部堆栈信息 */
    private String getFullStackTraceMessage(Exception e){
        final Writer writer = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(writer);
        e.fillInStackTrace().printStackTrace(printWriter);
        this.fullStackTraceMessage = writer.toString();
        return this.fullStackTraceMessage;
    }
}
