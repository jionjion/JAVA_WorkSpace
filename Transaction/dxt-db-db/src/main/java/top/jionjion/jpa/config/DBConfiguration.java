package top.jionjion.jpa.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


/**
 *  数据库配置,在中使用
 *  org.springframework.jdbc.datasource.DataSourceUtils#doGetConnection(javax.sql.DataSource)
 *
 * @author Jion
 */
@Configuration
public class DBConfiguration {

    /** 定义用户数据源数据库连接属性 */
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.ds-user")
    public DataSourceProperties userDataSourceProperties() {
        return new DataSourceProperties();
    }

    /** 定义用户数据源数据库连接源 */
    @Bean
    @Primary
    public DataSource userDataSource() {
        return userDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    /** 定义用户数据源JDBC模板 */
    @Bean
    public JdbcTemplate userJdbcTemplate(@Qualifier("userDataSource") DataSource userDataSource){
        return new JdbcTemplate(userDataSource);
    }

    /** 定义数据库事务管理器 */
    @Bean
    public PlatformTransactionManager transactionManager(){
        PlatformTransactionManager userTransactionManager = new DataSourceTransactionManager(userDataSource());
        PlatformTransactionManager orderDataSourceTransactionManager = new DataSourceTransactionManager(orderDataSource());
        // 链式事务,这样可以将两个事务依次打开,依次执行,依次提交,或者依次回滚.
        return new ChainedTransactionManager(userTransactionManager, orderDataSourceTransactionManager);
    }

    /** 定义订单数据源数据库连接属性 */
    @Bean
    @ConfigurationProperties(prefix = "spring.ds-order")
    public DataSourceProperties orderDataSourceProperties() {
        return new DataSourceProperties();
    }

    /** 定义订单数据源数据库连接源 */
    @Bean
    public DataSource orderDataSource() {
        return orderDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    /** 定义订单数据源JDBC模板 */
    @Bean
    public JdbcTemplate orderJdbcTemplate(@Qualifier("orderDataSource") DataSource orderDataSource){
        return new JdbcTemplate(orderDataSource);
    }
}
