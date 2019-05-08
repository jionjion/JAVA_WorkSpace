package top.jionjion.api.service.notice.email;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jionjion.api.bean.notice.email.Email;
import top.jionjion.api.config.EmailConfig;
import top.jionjion.api.service.notice.SendEmail;

import java.util.Date;

/**
 * @author Jion
 *  发送邮件的实现类
 */
@Slf4j
@Service
public class SendEmailByAliCould implements SendEmail {


    @Autowired
    EmailConfig emailConfig;

    @Override
    public Email SendEmail(Email email) {

        return sendSimpleEmail(email);
    }

    /** 发送简单的邮件信息 */
    private Email sendSimpleEmail(Email email) {
        SimpleEmail simpleEmail = new SimpleEmail();

        // 字符集
        simpleEmail.setCharset(emailConfig.getCharset());
        // 邮箱服务器地址
        simpleEmail.setHostName(emailConfig.getMailHost());
        // 发件端口,非SSL加密方式
        simpleEmail.setSmtpPort(emailConfig.getPort());
        // 服务器登录用户密码
        simpleEmail.setAuthentication(emailConfig.getMailServiceAddress() ,emailConfig.getMailServicePassword());

        // 发件人,来自服务器地址
        String mailFrom = emailConfig.getMailServiceAddress();
        // 发件人名称,来自服务器登录用户名
        String mailSender = emailConfig.getMailServiceUsername();
        // 收件人
        String mailTo = email.getMailTo();
        // 主题
        String subject = email.getSubject();
        // 内容
        String msg = email.getBody();
        // 发件日期
        Date now = new Date();

        try {
            simpleEmail.setFrom(mailFrom,mailSender);
            simpleEmail.addTo(mailTo);
            simpleEmail.setSubject(subject);
            simpleEmail.setMsg(msg);
            simpleEmail.setSentDate(now);
            simpleEmail.send();
        }catch (EmailException e){
            log.error(e.getMessage(), e);
            email.setNote(e.getMessage());
        }
        return email;
    }

}
