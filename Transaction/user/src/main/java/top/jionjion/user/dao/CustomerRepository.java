package top.jionjion.user.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.jionjion.user.bean.Customer;

/**
 * @author Jion
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /** 通过用户名查询 */
    Customer findOneByUsername(String username);
}
