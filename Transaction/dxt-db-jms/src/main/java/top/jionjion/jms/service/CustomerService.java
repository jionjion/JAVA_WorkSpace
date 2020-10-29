package top.jionjion.jms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.jionjion.jms.bean.Customer;
import top.jionjion.jms.dao.CustomerRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jion
 */
@Service
public class CustomerService {


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JmsTemplate jmsTemplate;

    /** 消息触发,收到队列消息后执行 */
    @Transactional(rollbackFor = Exception.class)
    @JmsListener(destination = "customer:msg:new")
    public void handleCreateCustomer(String msg){
        System.out.println("收到消息:" + msg);
        Customer customer = new Customer();
        customer.setUsername("Jion");
        customer.setDeposit(-2000);
        customerRepository.save(customer);
        if (customer.getDeposit() <= 0){
            throw new RuntimeException("余额不足");
        }
    }

    /** 创建用户 */
    @Transactional(rollbackFor = Exception.class)
    public Customer createCustomer(Customer customer) {
        Customer result = customerRepository.save(customer);
        if(result.getDeposit() <= 0){
            throw new RuntimeException("余额不足");
        }
        // 发送消息
        jmsTemplate.convertAndSend("customer:msg:reply", "创建成功.");

        return result;
    }


    /** 查询全部用户 */
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

}