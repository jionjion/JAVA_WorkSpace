package top.jionjion.jms.dao;

/**
 * @author Jion
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.jionjion.jms.bean.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {


}
