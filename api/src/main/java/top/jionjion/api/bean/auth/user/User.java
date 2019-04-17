package top.jionjion.api.bean.auth.user;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.naming.Name;
import javax.persistence.*;


/**
 *  @author Jion
 *  用户认证信息信息
 */
@ToString
@Data
@Entity
@Table(name="USER")
public class User {

    /** 主键自增 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** 系统生成UUID */
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name="UUID")
    private String uudi;

    /** 用户名 */
    @Column(name="USERNAME")
    private String username;

    /** 密码 */
    @Column(name="PASSWORD")
    private String password;

    /** 令牌 */
    @Column(name="TOKEN")
    private String token;
}
