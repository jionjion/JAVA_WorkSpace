package top.jionjion.api.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *  @author Jion
 *  提示信息
 */
@Component
@PropertySource("classpath:config/description.properties")
@ConfigurationProperties(prefix="wrong")
@Data
public class Wrong {

    /** 用户密码错误! */
    private String passwordError;

    /** 该用户暂未注册! */
    private String thisUserNoRegister;
}


