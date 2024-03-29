package MicroServices;
/**	该类的位置为创建时pom文件的根目录下,默认被IOC扫描加载后启动SpringBoot.
 * 	该类为SpringBoot的启动类,完成IOC容器的创建*/
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})	//无数据库时运行
@SpringBootApplication
public class MicroServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroServicesApplication.class, args);
	}
}
