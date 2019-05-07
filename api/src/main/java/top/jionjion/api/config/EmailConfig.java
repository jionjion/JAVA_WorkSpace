package top.jionjion.api.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *  @author Jion
 *  配置信息
 *  其他参考官网 https://mailhelp.aliyun.com/freemail/detail.vm?knoId=5869705
 */
@Component
@PropertySource("classpath:config/api.properties")
@Data
public class EmailConfig {

    @Value("${api.aliyun.email.mail.host}")
    private String mailHost;

    @Value("${api.aliyun.email.mail.transport.protocol}")
    private String mailTransportProtocol;

    @Value("${api.aliyun.email.mail.service.charset}")
    private String charset;

    @Value("${api.aliyun.email.mail.service.address}")
    private String mailServiceAddress;


    @Value("${api.aliyun.email.mail.service.password}")
    private String mailServicePassword;
}


