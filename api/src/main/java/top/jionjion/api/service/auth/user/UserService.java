package top.jionjion.api.service.auth.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import top.jionjion.api.bean.auth.user.User;
import top.jionjion.api.dao.repository.auth.user.UserRepository;

import static org.springframework.util.DigestUtils.md5DigestAsHex;

/**
 *  @author Jion
 *  用户权限
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /** 保存用户信息 */
    public User save(User user){

        // 生成MD5作为Token
        String token = getToken();
        user.setToken(token);
        // 加密用户名
        String encodePassword = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(encodePassword);
        return userRepository.save(user);
    }

    public User findByUsernameAndPassword (String username, String decodePassword){
        // 加密穿入的明文密码
        String encodePassword = DigestUtils.md5DigestAsHex(decodePassword.getBytes());
        User user = userRepository.findByUsernameAndPassword(username, encodePassword);
        return user;
    }

    /** 生成用户Token  MD5随机字符串 */
    private String getToken(){
        // @TODO 暂定md5加解密,后期需要双向加解密,并判断是否过期
        String time = Long.toHexString(System.currentTimeMillis());
        return md5DigestAsHex(time.getBytes());
    }
}
