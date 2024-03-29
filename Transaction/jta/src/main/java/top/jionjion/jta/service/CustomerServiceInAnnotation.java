package top.jionjion.jta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.jionjion.jta.bean.Customer;
import top.jionjion.jta.dao.CustomerRepository;

/**
 * 通过标签形式,管理事务
 *
 * @author Jion
 */
@Service
public class CustomerServiceInAnnotation {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * 保存
     */
    @Transactional
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }
}
