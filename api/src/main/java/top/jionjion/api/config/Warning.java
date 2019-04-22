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
@ConfigurationProperties(prefix="warning")
@Data
public class Warning {

    private String passwordNoEnoughComplicated;
}


