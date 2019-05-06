package top.jionjion.api.dao.repository.notice.email;

import org.springframework.data.repository.Repository;
import top.jionjion.api.bean.auth.user.User;
import top.jionjion.api.bean.notice.email.Email;

/**
 *  @author Jion
 *  用户保存
 */
public interface EmailRepository extends Repository<Email,String> {

    /** 通过ID查询 */
    public Email findByMailId(Integer mailId);

    /** 保存邮件信息 */
    public Email save(Email user);
}
