package top.jionjion.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.jionjion.jpa.bean.Customer;
import top.jionjion.jpa.bean.Order;
import top.jionjion.jpa.dao.CustomerRepository;

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
    private JdbcTemplate orderJdbcTemplate;


    /** 创建一个订单 org.springframework.jdbc.datasource.DataSourceUtils#doGetConnection(javax.sql.DataSource) 分别不同事务 */
    @Transactional(rollbackFor = Exception.class)
    public void createOrder(Order order) {
        // 用户
        Customer customer = customerRepository.getOne(order.getCustomerId());
        customer.setDeposit(customer.getDeposit() - order.getAmount());
        customerRepository.save(customer);
        // 模拟错误,在事物后执行
        if(order.getCustomerId()<= 0){
            throw new RuntimeException("用户错误");
        }

        // 订单
        String sqlOrder = "insert into customer_order(customer_id, title, amount) values(?, ?, ?)";
        orderJdbcTemplate.update(sqlOrder, order.getCustomerId(), order.getTitle(), order.getAmount());
        // 模拟错误,在事物后执行
        if(order.getAmount()<= 0){
            throw new RuntimeException("金额错误");
        }
    }

    public Map userInfo(Long id) {
        Customer customer = customerRepository.findById(id).get();
        List orders = orderJdbcTemplate.queryForList("select * from customer_order");
        Map result = new HashMap();
        result.put("customer", customer);
        result.put("orders", orders);
        return result;
    }
}
