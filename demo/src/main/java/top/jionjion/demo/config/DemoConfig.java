package top.jionjion.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Jion
 *      测试配置类
 *      使用Yml的文档块语法进行环境区分
 */
@Data
@Component
@ConfigurationProperties(prefix = "demo", ignoreUnknownFields = false)
public class DemoConfig {

    private Boolean enable;

    private String author;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private Map<String,String> info;

    private List<String> helpers;
}
