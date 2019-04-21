package top.jionjion.api.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *  @author Jion
 *  配置信息
 */
@Component
@PropertySource("classpath:config/description.properties")
@ConfigurationProperties(prefix="prompt")
@Data
public class Prompt {

    private String userNameNotNull;
}


