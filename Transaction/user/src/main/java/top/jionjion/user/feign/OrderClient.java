package top.jionjion.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.jionjion.dto.IOrderService;
import top.jionjion.dto.OrderDto;

/**
 * @author Jion
 */
@FeignClient(value = "order", path = "/api/order")
public interface OrderClient extends IOrderService {

    /** 获得我的订单 */
    @GetMapping("/{id}")
    @Override
    OrderDto getMyOrder(@PathVariable(name = "id") Long id);
}
