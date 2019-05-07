package top.jionjion.api.service.notice.email;

import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import top.jionjion.api.bean.notice.email.Email;
import top.jionjion.api.config.EmailConfig;
import top.jionjion.api.service.notice.SendEmail;

/**
 * @author Jion
 *  发送邮件的实现类
 */
public class SendEmailByAliCould implements SendEmail {


    @Autowired
    EmailConfig emailConfig;

    @Override
    public Email SendEmail(Email email) {

        return null;
    }

    /** 发送简单的邮件信息 */
    private void sendSimpleEmail(Email email){
        SimpleEmail simpleEmail = new SimpleEmail();

        // 字符集
        simpleEmail.setCharset(emailConfig.getCharset());
        // 邮箱服务器地址
        simpleEmail.setHostName(emailConfig.getMailHost());

        simpleEmail.set



            String sendFrom = "authenUser@163.com";//发送者邮件地址
            String sender = "long"; //发送者的名称
            String authenUser = "authenUser@163.com"; //发送者的帐户
            String authenPwd = "authenPwd";  //发送都 的密码
            String smtpPort ="25";  //发送邮件的服务端口
            String isSSL = "false";//是否为SSL链接的邮箱
            String isTSL = "true";//是否为TSL加密方式的邮箱，true=是，false=否

            SimpleEmail email = new SimpleEmail();
            email.setHostName(hostName);
            email.setAuthentication(authenUser,authenPwd);
            email.addTo(sendTo, receiver);
            email.setFrom(sendFrom,sender);
            if("true".equals(isSSL)){
                email.setSslSmtpPort(smtpPort);
                email.setSSLOnConnect(true);
            }else{
                email.setSmtpPort(25);
                if("true".equals(isTSL)) {
                    email.setStartTLSEnabled(true);
                }
            }
            email.setSubject(subject);
            email.setMsg(msg);
            email.setSentDate(new Date());
            email.send();
    }

}
