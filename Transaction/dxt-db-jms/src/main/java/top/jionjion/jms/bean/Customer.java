package top.jionjion.jms.bean;

import lombok.Data;

import javax.persistence.*;

/**
 *  数据源A-用户表
 *
 * @author Jion
 */
@Data
@Entity(name = "customer")
public class Customer {

    /** 主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 用户 */
    @Column
    private String username;

    /** 余额 */
    @Column
    private Integer deposit;
}
