package top.jionjion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.jionjion.bean.OrderDetail;

import java.util.List;

/**
 * @author Jion
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail,String>{

    /** 通过订单主表主键查询订单 */
    List<OrderDetail> findAllByOrderId(String orderId);

    /** 通过主键查询 */
    OrderDetail findByDetailId(String detailId);
}
