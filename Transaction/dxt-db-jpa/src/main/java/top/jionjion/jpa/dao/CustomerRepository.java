package top.jionjion.jpa.dao;

/**
 * @author Jion
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.jionjion.jpa.bean.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {


}
