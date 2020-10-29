package top.jionjion.jpa.bean;

import lombok.Data;

/**
 *  数据源A-用户表
 *
 * @author Jion
 */
@Data
public class Customer {

    /** 主键 */
    private Long id;

    /** 用户 */
    private String username;

    /** 余额 */
    private Integer deposit;
}
