package top.jionjion.api.service.notice.email;

import org.springframework.stereotype.Service;
import top.jionjion.api.bean.notice.email.Email;
import top.jionjion.api.service.notice.Notice;

/**
 * @author Jion
 *  发送邮件
 */
@Service
public abstract class SendEmail implements Notice {

    /** 发送邮件 */
    public abstract Email SendEmail(Email email);


}
