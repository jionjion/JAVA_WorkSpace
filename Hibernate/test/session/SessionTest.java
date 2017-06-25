package session;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.Test;

import bean.Student;

/**
 *	对session对象的获取进行测试
 */
public class SessionTest {
	
	
	@Test
	public void testOpenSession(){
	
	//创建配置对象
	Configuration configuration = new Configuration().configure();
	//创建服务注册对象
	ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
	//创建会话工厂
	SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	//创建会话对象，第一种方式。
	Session session = sessionFactory.openSession();
	if(session != null){
		System.out.println("Session创建成功");
		Session session2 = sessionFactory.openSession();
		System.out.println("session是否相同"+(session==session2));
	}else{
		System.err.println("Session创建失败");
	}
	
	
	//开启事物
 	Transaction transaction = session.beginTransaction();
	
	}
	
	@Test
	public void testGetCurrentSession(){
		//创建配置对象
		Configuration configuration = new Configuration().configure();
		//创建服务注册对象
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		//创建会话工厂
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		//创建会话对象，第二种方式。
		Session session = sessionFactory.getCurrentSession();
		if(session != null){
			System.out.println("Session创建成功");
			Session session2 = sessionFactory.getCurrentSession();
			System.out.println("session是否相同"+(session==session2));
		}else{
			System.err.println("Session创建失败");
		}
	}
	
	@Test
	public void testSaveStudentWithOpenSession(){
		//创建配置对象
		Configuration configuration = new Configuration().configure();
		//创建服务注册对象
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		//创建会话工厂
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		//创建会话对象，第一种方式。
		Session session1 = sessionFactory.openSession();
		//开启事物
		Transaction transaction1 = session1.beginTransaction();
		//创建对象
		Student student = new Student(1, "张三", "女", new Date(), "上海");
		session1.doWork(new Work() {
			
			@Override
			public void execute(Connection connection) throws SQLException {
				System.out.println("数据库连接的Hash码:"+connection.hashCode());
			}
		});
		//保存,但是不关闭事务
		session1.save(student);
		//session1.close();
		//提交事务
		transaction1.commit();
		
		
		//创建会话对象，第一种方式。
		Session session2 = sessionFactory.openSession();
		//开启事物
		Transaction transaction2 = session2.beginTransaction();
		//创建对象
		Student student2 = new Student(2, "李四", "男", new Date(), "上海");
		session2.doWork(new Work() {
			
			@Override
			public void execute(Connection connection) throws SQLException {
				System.out.println("数据库连接的Hash码:"+connection.hashCode());
			}
		});
		//保存,但是不关闭事务
		session2.save(student2);
		//session2.close();
		//提交事务
		transaction2.commit();
		
	}
	
	
	@Test
	public void testSaveStudentWithGetCurrentSession(){
		//创建配置对象
		Configuration configuration = new Configuration().configure();
		//创建服务注册对象
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		//创建会话工厂
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		//创建会话对象，第一种方式。
		Session session1 = sessionFactory.getCurrentSession();
		//开启事物
		Transaction transaction1 = session1.beginTransaction();
		//创建对象
		Student student = new Student(1, "张三", "女", new Date(), "上海");
		session1.doWork(new Work() {
			
			@Override
			public void execute(Connection connection) throws SQLException {
				System.out.println("数据库连接的Hash码:"+connection.hashCode());
			}
		});
		//保存,但是不关闭事务
		session1.save(student);
		//session1.close();
		//提交事务
		transaction1.commit();
		
		//创建会话对象，第一种方式。
		Session session2 = sessionFactory.getCurrentSession();
		//开启事物
		Transaction transaction2 = session2.beginTransaction();
		//创建对象
		Student student2 = new Student(2, "李四", "男", new Date(), "上海");
		session2.doWork(new Work() {
			
			@Override
			public void execute(Connection connection) throws SQLException {
				System.out.println("数据库连接的Hash码:"+connection.hashCode());
			}
		});
		//保存,但是不关闭事务
		session2.save(student2);
		//session2.close();
		//提交事务
		transaction2.commit();
	}
}

