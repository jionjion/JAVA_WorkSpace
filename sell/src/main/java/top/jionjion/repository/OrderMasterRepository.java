package top.jionjion.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import top.jionjion.bean.OrderMaster;

/**
 *  @author Jion
 *      订单主表的Repository
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster,String>{

    /** 根据用户的openid查询购买的商品 */
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);

    /** 根据主键查询 */
    OrderMaster findByOrderId(String orderId);
}
