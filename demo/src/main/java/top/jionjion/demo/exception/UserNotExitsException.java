package top.jionjion.demo.exception;

/**
 * @author Jion
 *  自定义异常
 */
public class UserNotExitsException extends RuntimeException{

    public UserNotExitsException(){
        super("用户不存在!");
    }
}
