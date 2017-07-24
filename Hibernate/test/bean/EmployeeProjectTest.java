package bean;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *	多对多的测试类
 */
public class EmployeeProjectTest {
	private  SessionFactory sessionFactory;
	private  Session session;
	private  Transaction transaction;
	
	/* 测试方法前 */
	@Before
	public void init(){
		//创建Configuration对象,读取Hibernate的哦配置文件
		Configuration configuration = new Configuration().configure();
		
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		//创建会话工厂
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		//创建会话对象
		session = sessionFactory.openSession();
		//开启事物
		transaction = session.beginTransaction();
		
	}
	
	/*测试方法后执行*/
	@After
	public void destory() {
		//事物提交
		transaction.commit();
		//关闭会话 
		session.close();
		//关闭工厂
		sessionFactory.close();
	}
	
	/*关联关系由Project进行维护,同时保存Project时会进行级联保存*/
	@Test
	public void add(){
		Project project1 = new Project(1, "项目一");
		Project project2 = new Project(2, "项目二");
		Employee employee1 = new Employee(1, "张三");
		Employee employee2 = new Employee(2, "李四");
		
		//参加项目一的有张三和李四
		project1.getEmployees().add(employee1);
		project1.getEmployees().add(employee2);
		//参加项目二的只有李四
		project2.getEmployees().add(employee2);
		//保存项目,级联保存员工
		session.save(project1);	
		session.save(project2);
	}
	
}
