package top.jionjion.web.service;

import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import top.jionjion.web.bean.Customer;
import top.jionjion.web.dao.CustomerRepository;

/**
 *  通过代码方式管理事务
 * @author Jion
 */
@Service
public class CustomerServiceInCode {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    /** 保存 */
    public Customer save(Customer customer){
        Customer result = null;
        // 事务定义
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        // 隔离级别
        transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
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
