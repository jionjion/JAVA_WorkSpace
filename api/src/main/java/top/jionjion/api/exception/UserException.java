package top.jionjion.api.exception;

/**
 * @author Jion
 *  用户信息异常
 */
public class UserException extends ApiException {


    /** 私有构造方法.必须填写异常信息或者异常来源 */
    private UserException(){}

    /** 父类的构造方法 */
    public UserException(String message){
        super(message);
    }

    public UserException(String message, Exception exception){
        super(message, exception);
    }
}
