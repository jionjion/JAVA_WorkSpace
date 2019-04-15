package top.jionjion.api.bean.auth.user;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

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

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String uudi;

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="USERNAME")
    private String username;

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="PASSWORD")
    private String password;

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="TOKEN")
    private String token;
}
