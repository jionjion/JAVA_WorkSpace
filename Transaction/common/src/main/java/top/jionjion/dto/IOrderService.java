package top.jionjion.dto;

/**
 *  共有方法,定义服务与调用
 * @author Jion
 */
public interface IOrderService {

    OrderDto getMyOrder(Long id);
}
