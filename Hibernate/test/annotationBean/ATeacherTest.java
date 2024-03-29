package annotationBean;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/***多对多,多方的测试 */
public class ATeacherTest {
	
	//会话工厂
	private SessionFactory sessionFactory;
	//会话对象
	private Session session;
	//事物对象
	private Transaction transaction;
	
	/***
	 * 	测试方法前执行
	 */
	@Before
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
	
	/***
	 * 	测试方法后执行
	 */
	@After
	public void destory() {
		//事物提交
		transaction.commit();	//当改为自动提交时,需要将其注释掉
		//关闭会话 
		session.close();
		//关闭工厂
		sessionFactory.close();
	}
	
	
	/**
	 * 	GET查询方法
	 */
	@Test
	public void testGetAStudent(){
		APupil student = (APupil)session.get(APupil.class, 1);
		System.out.println("真实类名:"+student.getClass().getName());
		System.out.println("查询出:"+student.toString());
	}
	
	/**
	 * 	Load查询方法
	 */
	@Test
	public void testLoadAStudent(){
		APupil student = (APupil)session.load(APupil.class, 1);
		System.out.println("代理类名:"+student.getClass().getName());
		System.out.println("查询出:"+student.toString());
	}
	
	/**
	 * 	GET查询后更新
	 */
	@Test
	public void testGetUpdateAStudent(){
		APupil student = (APupil)session.get(APupil.class, 1);
		student.setPname("JionJion");
		System.out.println("更新:"+student.toString());
	}
	
	/**
	 * 	删除方法
	 */
	@Test
	public void testDeleteAStudent(){
		APupil student = (APupil)session.get(APupil.class, 1);
		session.delete(student);
		System.out.println("删除:"+student.toString());
	}
	
	/***
	 * 	测试多对多保存*/
	@Test
	public void testOneTowOneAStudent() {
		//创建学生对象
		APupil pupi1 = new APupil(1, "张谦", "男");
		APupil pupi2 = new APupil(2, "Jion", "男");
		
		//创建老师对象
		ATeacher teacher1 = new ATeacher("1", "张老师");
		ATeacher teacher2 = new ATeacher("2", "Jion老师");

		Set<APupil> pupils = new HashSet<APupil>();
		pupils.add(pupi1);
		pupils.add(pupi2);
		
		Set<ATeacher> teachers = new HashSet<ATeacher>();
		teachers.add(teacher1);
		teachers.add(teacher2);
		
		//保存关系
		pupi1.setTeachers(teachers);
		pupi2.setTeachers(teachers);
		//保存对象
		session.save(pupi1);
		session.save(pupi2);
		session.save(teacher1);
		session.save(teacher2);
		
	}
}
