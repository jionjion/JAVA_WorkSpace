package top.jionjion.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jionjion.order.repository.OrderRepository;

/**
 * @author Jion
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;


}
