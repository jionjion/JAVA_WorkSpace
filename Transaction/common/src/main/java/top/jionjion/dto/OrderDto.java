package top.jionjion.dto;

import lombok.Data;

/**
 *  订单
 * @author Jion
 */
@Data
public class OrderDto {
    private Long id;

    private String title;

    private String detail;

    private Integer amount;
}
