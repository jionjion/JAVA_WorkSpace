package top.jionjion.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.jionjion.order.bean.Order;

/**
 * @author Jion
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    /** 通过标签查询 */
    Order findOneByTitle(String title);
}
