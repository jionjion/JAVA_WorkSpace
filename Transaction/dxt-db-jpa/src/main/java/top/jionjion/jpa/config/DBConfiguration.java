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
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


/**
 *  数据库配置,在中使用
 *  org.springframework.jdbc.datasource.DataSourceUtils#doGetConnection(javax.sql.DataSource)
 *
 *  JDBC和JPA共同使用
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

    /** Bean实体管理工厂 */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        // 不生成表结构
        adapter.setGenerateDdl(false);
        // 事务管理器工厂类
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(adapter);
        factory.setDataSource(userDataSource());
        // 包扫描路径
        factory.setPackagesToScan("top.jionjion.jpa.bean");
        return factory;
    }

    /** 事务管理器 */
    @Bean
    public PlatformTransactionManager transactionManager(){
        // JPA 事务管理器
        JpaTransactionManager userTransactionManager = new JpaTransactionManager();
        // 实体管理工厂
        userTransactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        // JDBC事务管理器
        DataSourceTransactionManager orderTransactionManager = new DataSourceTransactionManager(orderDataSource());
        // 链式事务管理器. 先放入,后提交
        return new ChainedTransactionManager(userTransactionManager, orderTransactionManager);
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
