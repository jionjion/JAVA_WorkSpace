package top.jionjion.api.dao.repository.auth.user;

import org.springframework.data.repository.Repository;
import top.jionjion.api.bean.auth.user.User;

/**
 *  @author Jion
 *  用户保存
 */
public interface UserRepository extends Repository<User,String> {

    /** 通过ID查询 */
    public User findById(Integer id);

    /** 通过UUID查询 */
    public User findByUuid(String uuid);

    /** 通过用户名,加密密码查询 */
    public User findByUsernameAndPassword(String username, String password);

    /** 保存用户信息 */
    public User save(User user);
}
