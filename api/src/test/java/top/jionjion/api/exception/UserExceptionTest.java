package top.jionjion.api.exception;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author Jion
 *  用户异常的测试类
 */
@Slf4j
public class UserExceptionTest {

    /** 抛出自定义异常 */
    private void throwUserException(){
        throw new UserException("异常原因 bi li bi li...");
    }

    /** 测试:抛出自定义异常 */
    @Test
    public void testThrowUserException(){
        try {
            throwUserException();
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    /** 捕获其他异常,并抛出自定义异常 */
    private void throwUserExceptionWithTryCatch(){
        try {
            int i = 1/0;
        }catch (Exception e) {
            throw new UserException("捕获其他异常",e);
        }
    }

    /** 测试:捕获其他异常,并抛出自定义异常 */
    @Test
    public void testThrowUserExceptionWithTryCatch(){
        try {
            throwUserExceptionWithTryCatch();
        } catch (UserException e){
            // 异常信息
            log.error(e.getMessage());
            // 异常堆栈
            log.error(e.getFullStackTraceMessage());
        }
    }
}