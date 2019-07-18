package top.jionjion.api.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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

    @Value("#{'${info.cn_numbers}'.split(',')}")
    private String[] cnNumbers;

    private List<String> addressList;

    private Map<String, String> map;

}


