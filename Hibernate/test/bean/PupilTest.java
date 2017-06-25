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
 *	学生类的测试
 */
public class PupilTest {

	private  SessionFactory sessionFactory;
	private  Session session;
	private  Transaction transaction;
	/*
	 * 	测试方法前
	 */
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
	/*
	 * 	测试方法后执行
	 */
	@After
	public void destory() {
		//事物提交
		transaction.commit();
		//关闭会话 
		session.close();
		//关闭工厂
		sessionFactory.close();
	}
	
	/*测试多对一的配置*/
	@Test
	public void addPupil(){
		//创建班级
		Grade grade = new Grade();
		grade.setGid(3);
		grade.setGname("大三");
		grade.setGdescribe("老腊肉");
		//创建学生
		Pupil pupil1 = new Pupil(3, "张三", "男", 3);
		Pupil pupil2 = new Pupil(4, "李四", "男", 3);
		//设置关联关系
		pupil1.setGrade(grade);
		pupil2.setGrade(grade);
		//保存
		session.save(grade);
		session.save(pupil1);
		session.save(pupil2);
	}
	
	/*测试一/多相互关系.*/
	@Test
	public void addPupilByReciprocal(){
		//创建班级
		Grade grade = new Grade();
		grade.setGid(4);
		grade.setGname("大四");
		grade.setGdescribe("该滚蛋了");
		//创建学生
		Pupil pupil1 = new Pupil(5, "张三", "男", 3);
		Pupil pupil2 = new Pupil(6, "李四", "男", 3);
		//设置关联关系,相互关系
		grade.getPupils().add(pupil1);
		grade.getPupils().add(pupil2);
		pupil1.setGrade(grade);
		pupil2.setGrade(grade);
		//保存
		session.save(grade);
//		session.save(pupil1);
//		session.save(pupil2);
	}	
	
	/*查询学生所在班级的信息*/
	@Test
	public void findGradeByPupil(){
		Pupil pupil = (Pupil) session.get(Pupil.class, 5);
		Grade grade = pupil.getGrade();
		System.out.println("学生信息:"+pupil.toString());
		System.out.println("班级信息"+grade.toString());
	}
}
