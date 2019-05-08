package top.jionjion.api.service.notice.email;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.jionjion.api.BaseTest;
import top.jionjion.api.bean.notice.email.Email;
import top.jionjion.api.service.notice.SendEmail;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * @author Jion
 */
@Slf4j
public class SendEmailByAliCouldTest extends BaseTest {

    @Autowired
    SendEmailByAliCould sendEmail;

    @Test
    public void testSendEmail() {

    }

    @Test
    public void testSendSimpleEmail() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Email email = new Email();
        email.setMailTo("1434501783@qq.com");
        email.setSubject("测试邮件");
        email.setBody("这是邮件内容.....");
        email.setCreateUserId(-1);

        // 反射,测试私有方法
        Class clazz =sendEmail.getClass();
        // 私有方法名,参数列表
        Method method= clazz.getDeclaredMethod("sendSimpleEmail", new Class[]{Email.class});
        method.setAccessible(true);
        // 调用
        email = (Email) method.invoke(sendEmail, new Object[]{email});
        // email = sendEmail.sendSimpleEmail(email);

        log.info(email.toString());
        assertNotNull(email);
    }
}