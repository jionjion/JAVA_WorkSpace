package cache;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bean.Pupil;
import bean.Student;

public class CacheTest {

	//会话工厂
	private SessionFactory sessionFactory;
	//会话对象
	private Session session;
	//事物对象
	private Transaction transaction;
	
	/*测试方法前执行*/
	public void init() {
		//创建配置对象
		Configuration configuration = new Configuration().configure();
		//创建服务注册对象
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		//创建会话工厂
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		//创建会话对象
		session = sessionFactory.openSession();
		//开启事物
		transaction = session.beginTransaction();
	}
	
	/*测试方法后执行*/
	public void destory() {
		//事物提交
		transaction.commit();
		//关闭会话 
		session.close();
		//关闭工厂
		sessionFactory.close();
	}
	
	/*第二次同一个session查询同一个对象时,并不会再次执行数据库的查询,而是使用缓存;在缓存中每一个对象唯一*/
	@Test
	public void testDoubleQueryBySame(){
		init();		//初始化
		Student student1 =(Student) session.get(Student.class,1);
		Student student2 =(Student) session.get(Student.class,1);
		System.out.println("查询到学生:"+student1.toString());
		System.out.println("查询到学生:"+student2.toString());
		destory();	//销毁
	}
	
	/*第二次查询同一对象,使用不同的session,并没有使用缓存*/
	@Test
	public void testDoubleQueryByDifferent(){
		init();		//初始化
		Student student1 =(Student) session.get(Student.class,1);
		System.out.println("查询到学生:"+student1.toString());
		destory();	//销毁
		init();		//初始化
		Student student2 =(Student) session.get(Student.class,1);
		System.out.println("查询到学生:"+student2.toString());
		destory();	//销毁
	}
	
	/*第二次查询对象,使用相同的session,是否使用缓存取决于查询方式*/
	@Test
	public void testDoubleQueryListBySame(){
		init();		//初始化
		Query query = session.createQuery("from Student");
		//第一次查询
		List<Student> students = query.list();
		for (Student student : students) {
			System.out.println("查询到学生:"+student.toString());
		}
		//第二次查询,并没有实现缓存,再次查询数据库
		students = query.list();
		for (Student student : students) {
			System.out.println("查询到学生:"+student.toString());
		}		
		
		//使用迭代器,对缓存进行查询匹配,如果命中则调用缓存,否则查询数据库
		Iterator<Student> iterator = query.iterate();
		while (iterator.hasNext()) {
			Student student = (Student) iterator.next();
			System.out.println("查询到学生:"+student.toString());
		}		
		destory();	//销毁
	}
	
	/*针对一级缓存的操作*/
	@Test
	public void testFirstLevelCache(){
		init();		//初始化
		Student student1 =(Student) session.get(Student.class,1);
		System.out.println("查询到学生:"+student1.toString());
		//清除一级缓存区的指定内容
		session.evict(student1);
		//清除一级缓存区的所有内容
		session.clear();
		Student student2 =(Student) session.get(Student.class,1);
		System.out.println("查询到学生:"+student2.toString());
		destory();	//销毁
	}
	
	/*针对二级缓存的操作,使用不同的sessionFactory,发现不能使用二级缓存*/
	@Test
	public void testTwoLevelCacheByDifferentFactory(){
		init();		//初始化
		Pupil pupil1 =(Pupil) session.get(Pupil.class,5);
		System.out.println("查询到学生:"+pupil1.toString());
		destory();	//销毁
		init();		//初始化
		Pupil pupil2 =(Pupil) session.get(Pupil.class,5);
		System.out.println("查询到学生:"+pupil2.toString());
		destory();	//销毁
	}
	
	/*针对二级缓存的操作,使用相同的sessionFactory,可以使用二级缓存*/
	@Test
	public void testTwoLevelCacheBySameFactory(){

		//创建配置对象
		Configuration configuration = new Configuration().configure();
		//创建服务注册对象
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		//创建会话工厂
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		//创建会话对象
		Session session = sessionFactory.openSession();
		//开启事物
		Transaction transaction = session.beginTransaction();
		//查询
		Pupil pupil1 =(Pupil) session.get(Pupil.class,5);
		System.out.println("查询到学生:"+pupil1.toString());
		//提交事务
		transaction.commit();
		//关闭session,但是不关闭工厂
		session.close();
		
		//再次查询
		//创建会话对象
		session = sessionFactory.openSession();
		//开启事物
		transaction = session.beginTransaction();
		//查询
		pupil1 =(Pupil) session.get(Pupil.class,5);
		System.out.println("查询到学生:"+pupil1.toString());
		//提交事务
		transaction.commit();
		//关闭session,sessionFactory
		session.close();
		sessionFactory.close();
	}
}
