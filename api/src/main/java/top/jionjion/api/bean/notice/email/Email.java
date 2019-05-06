package top.jionjion.api.bean.notice.email;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author Jion
 *  邮件通讯类,将邮件发送信息存入数据库
 */
@Entity
@Table(name="EMAIL_LIST")
@ToString
public class Email {

    /** 主键 */
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="MAIL_ID")
    @Id
    @JsonIgnore
    private Integer mailId;

    /** 发送 */
    @Getter
    @Setter
    @Column(name="MAIL_TO")
    private String mailTo;

    /** 抄送 */
    @Getter
    @Setter
    @Column(name="MAIL_CC")
    private String mailCc;

    /** 主题 */
    @Getter
    @Setter
    @Column(name="SUBJECT")
    private String subject;

    /** 正文 */
    @Getter
    @Setter
    @Column(name="BODY")
    private String body;

    /** 发送信息 */
    @Getter
    @Setter
    @Column(name="NOTE")
    private String note;

    /** 发件人 */
    @Getter
    @Setter
    @Column(name="CREATE_USER_ID")
    private Integer createUserId;

}
