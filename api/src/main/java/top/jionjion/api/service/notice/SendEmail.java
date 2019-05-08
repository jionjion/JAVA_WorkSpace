package top.jionjion.api.service.notice;

import org.springframework.stereotype.Service;
import top.jionjion.api.bean.notice.email.Email;

/**
 * @author Jion
 *  发送邮件接口
 */
public interface SendEmail {

    /** 发送邮件方法* */
    Email SendEmail(Email email);

}
