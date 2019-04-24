package top.jionjion.api.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * @author Jion
 *  全局的API异常
 */
public class ApiException extends RuntimeException{

    //@TODO 统一的异常处理模块


    /** 获得异常的全部堆栈信息 */
    public String getFullStackTrace(Exception e){
        final Writer writer = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(writer);
        e.fillInStackTrace().printStackTrace(printWriter);
        return writer.toString();
    }
}
