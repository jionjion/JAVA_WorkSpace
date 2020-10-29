package top.jionjion.web.bean;

import lombok.Data;

import javax.persistence.*;

/**
 * 顾客类
 *
 * @author Jion
 */
@Data
@Entity(name ="customer")
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name="user_name", unique = true)
    private String username;

    private String password;

    private String role;
}
