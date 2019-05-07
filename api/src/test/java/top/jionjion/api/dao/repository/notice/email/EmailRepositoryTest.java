package top.jionjion.api.dao.repository.notice.email;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import top.jionjion.api.BaseTest;
import top.jionjion.api.bean.notice.email.Email;

/**
 * @author Jion
 *  邮件发送后,实体类的测试
 */
@Slf4j
public class EmailRepositoryTest extends BaseTest {


    @Autowired
    private EmailRepository emailRepository;

    /** 测试查询方法 */
    @Test
    public void testFindByMailId(){
        Email email = emailRepository.findByMailId(1);
        log.info(email.toString());
        Assert.assertNotNull(email);
    }

    /** 测试保存邮件信息 */
    @Test
    @Rollback
    public void testSave(){
        Email email = new Email();
        email.setMailCc("1434501783@qq.com");
        email.setSubject("来自阿里云的测试邮件");
        email.setBody("当你收到这封邮件的时候,证明案阿里云邮箱已经成功的发送了一封测试邮件,测试时间戳" + System.currentTimeMillis());
        email.setCreateUserId(-1);
        emailRepository.save(email);
    }
}