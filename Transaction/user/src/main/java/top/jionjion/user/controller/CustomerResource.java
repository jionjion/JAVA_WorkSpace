package top.jionjion.user.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.jionjion.dto.OrderDto;
import top.jionjion.user.bean.Customer;
import top.jionjion.user.dao.CustomerRepository;
import top.jionjion.user.feign.OrderClient;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户信息
 *
 * @author Jion
 */
@RestController
@RequestMapping("/api/customer")
public class CustomerResource {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderClient orderClient;

    @PostConstruct
    public void init(){
        log.info("执行初始化脚本 .........");
        Customer customer = new Customer();
        customer.setUsername("Jion");
        customer.setPassword("1234456");
        customer.setRole("Admin");
        customerRepository.save(customer);
    }

    /**
     *  全部用户
     */
    @GetMapping("/all")
    @HystrixCommand
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    /**
     *  保存用户
     */
    @PostMapping("/save")
    public Customer save(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    /**
     *  用户信息
     */
    @GetMapping("/my")
    public Map getMyInfo(){
        Customer customer = customerRepository.findOneByUsername("Jion");
        OrderDto order = orderClient.getMyOrder(1L);
        Map result = new HashMap(1);
        result.put("customer", customer);
        result.put("order", order);
        return result;
    }
}
