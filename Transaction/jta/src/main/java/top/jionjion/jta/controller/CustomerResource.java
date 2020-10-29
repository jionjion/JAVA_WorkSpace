package top.jionjion.jta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.jionjion.jta.bean.Customer;
import top.jionjion.jta.dao.CustomerRepository;
import top.jionjion.jta.service.CustomerServiceInAnnotation;
import top.jionjion.jta.service.CustomerServiceInCode;

import java.util.List;

/**
 * @author Jion
 */
@RestController
@RequestMapping("/api/customer")
public class CustomerResource {

    @Autowired
    private CustomerServiceInAnnotation customerServiceInAnnotation;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerServiceInCode customerServiceInCode;

    /**
     *  curl http://127.0.0.1:8080/api/customer/all
     */
    @GetMapping("/all")
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    /**
     *  curl -X POST -d '{"username":"JionJion", "password":"123456", "role":"Admin"}' -H 'Content-Type: application/json' http://127.0.0.1:8080/api/customer/code
     */
    @PostMapping("/code")
    public Customer saveByCode(@RequestBody Customer customer) {
        return customerServiceInCode.save(customer);
    }

    /**
     *  curl -X POST -d '{"username":"Arise", "password":"123456", "role":"Admin"}' -H 'Content-Type: application/json' http://127.0.0.1:8080/api/customer/code
     */
    @PostMapping("/annotation")
    public Customer saveByAnnotation(@RequestBody Customer customer) {
        return customerServiceInAnnotation.save(customer);
    }
}
