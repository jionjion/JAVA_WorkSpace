package top.jionjion.api.bean.auth.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;


/**
 *  @author Jion
 *  用户认证信息信息
 */

@Entity
@Table(name="USER")
@ToString
public class User implements Serializable{

    private static final long serialVersionUID = 1L;

    /** 主键自增 */
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    @JsonIgnore
    private Integer id;

    /** 系统时间生成UUID */
    @Getter
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Id
    @JsonIgnore
    private String uuid;

    /** 用户名 */
    @Setter
    @Getter
    @Column(name="USERNAME")
    @JsonIgnore
    private String username;

    /** 密码 */
    @Setter
    @Getter
    @Column(name="PASSWORD")
    private String password;

    /** 令牌 */
    @Setter
    @Getter
    @Column(name="TOKEN")
    private String token;
}
