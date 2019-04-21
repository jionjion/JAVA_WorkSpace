package top.jionjion.api.config;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *  @author Jion
 *  配置信息
 */
@Component
@PropertySource("classpath:config/description.properties")
@ConfigurationProperties(prefix="info", ignoreUnknownFields=true)
@Data
public class Info {

    private String version;

    private String author;
}


