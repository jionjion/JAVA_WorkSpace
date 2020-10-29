package top.jionjion.jms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;
import top.jionjion.jms.bean.Customer;
import top.jionjion.jms.service.CustomerService;

import java.util.List;

/**
 * 订单
 * @author Jion
 */
@RestController
@RequestMapping("/api/customer")
public class CustomerResource {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JmsTemplate jmsTemplate;


    /**
     * curl -X POST -d '{"id":"2", "username":"Arise", "deposit":100}' -H 'Content-Type: application/json' http://127.0.0.1:8080/api/customer/customer
     */
    @PostMapping("/customer")
    public Customer createOrder(@RequestBody Customer customer){
         return customerService.createCustomer(customer);
    }

    @GetMapping("/customer")
    public List<Customer> createOrder(){
        return customerService.findAll();
    }

    /**
     * 发送消息
     * curl -X POST -H 'Content-Type: application/json' http://127.0.0.1:8080/api/customer/msg?msg=Hello
     */
    @PostMapping("/msg")
    public void createOrder(@RequestParam String msg){
        jmsTemplate.convertAndSend("customer:msg:new", msg);
    }

    /**
     * 接收消息
     */
    @GetMapping("/msg")
    public String getMsg(){
        jmsTemplate.setReceiveTimeout(3000);
        Object result = jmsTemplate.receiveAndConvert("customer:msg:new");
        return String.valueOf(result);
    }
}
