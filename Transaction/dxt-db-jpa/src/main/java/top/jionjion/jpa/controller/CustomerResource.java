package top.jionjion.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.jionjion.jpa.bean.Order;
import top.jionjion.jpa.service.CustomerService;

/**
 * 订单
 * @author Jion
 */
@RestController
@RequestMapping("/api/customer")
public class CustomerResource {

    @Autowired
    private CustomerService customerService;


    /**
     * curl -X POST -d '{"id":"1", "customerId":"1", "title":"iphone12X", "amount":-100}' -H 'Content-Type: application/json' http://127.0.0.1:8080/api/customer/order
     */
    @PostMapping("/order")
    public void createOrder(@RequestBody Order order){
        customerService.createOrder(order);
    }

    @GetMapping("/{id}")
    public void createOrder(@PathVariable Long id){
        customerService.userInfo(id);
    }
}
