package top.jionjion.ticket.bean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Jion
 */
@Data
@Entity(name = "customer_ticket")
public class Ticket {

    /** 主键 */
    @Id
    @GeneratedValue
    private Long id;

    /** 票 */
    private String name;

    /** 所有者 */
    private Integer owner;

    /** 锁票的人 */
    private Integer lockUser;

    /** 票号 */
    private Integer tickNum;

}
