package top.jionjion.order.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.jionjion.order.bean.Order;
import top.jionjion.order.repository.OrderRepository;
import top.jionjion.dto.*;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 *
 * @author Jion
 */
@Slf4j
@RestController
@RequestMapping("/api/order")
public class OrderController implements IOrderService{

    @Autowired
    private OrderRepository orderRepository;

    /** 初始化数据 */
    @PostConstruct
    public void init(){
        log.info("初始化数据....");
        Order order = new Order();
        order.setTitle("鞋子");
        order.setDetail("我的鞋子...");
        order.setAmount(100);
        orderRepository.save(order);
    }


    /**
     *  查询全部
     */
    @GetMapping("/all")
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    /**
     *  保存
     */
    @PostMapping("/save")
    public Order save(@RequestBody Order order) {
        return orderRepository.save(order);
    }

    /**
     *  查询我的
     */
    @GetMapping("/{id}")
    @Override
    public OrderDto getMyOrder(@PathVariable Long id) {
        Order order = orderRepository.findById(id).get();
        OrderDto result = new OrderDto();
        result.setId(order.getId());
        result.setTitle(order.getTitle());
        result.setDetail(order.getDetail());
        result.setAmount(order.getAmount());
        return result;
    }
}
