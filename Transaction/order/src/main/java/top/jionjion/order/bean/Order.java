package top.jionjion.order.bean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Jion
 */
@Data
@Entity(name = "customer_order")
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String detail;

    private Integer amount;

}
