package top.jionjion.jta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import top.jionjion.jta.bean.Customer;
import top.jionjion.jta.dao.CustomerRepository;

/**
 *  通过代码方式管理事务
 * @author Jion
 */
@Service
public class CustomerServiceInCode {

    /** JPA操作 */
    @Autowired
    private CustomerRepository customerRepository;

    /** 平台事务管理器 */
    @Autowired
    private PlatformTransactionManager transactionManager;

    /** 保存 */
    public Customer save(Customer customer){
        Customer result = null;
        // 事务定义
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        // 隔离级别
        transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
        // 传播行为
        transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);
        // 平台事务管理器,获取事务
        TransactionStatus transaction = transactionManager.getTransaction(transactionDefinition);

        try {
            // 业务方法
            result = customerRepository.save(customer);
            // 提交
            transactionManager.commit(transaction);
        } catch (Exception e){
            // 回滚
            transactionManager.rollback(transaction);
        }
        return result;
    }

}
