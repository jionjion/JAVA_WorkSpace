package bean;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;



/**
 *	学生类的测试类
 */
public class StudentTest{
	
	//会话工厂
	private SessionFactory sessionFactory;
	//会话对象
	private Session session;
	//事物对象
	private Transaction transaction;
	
	/*
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
	
	/*
	 * 	测试方法后执行
	 */
	@After
	public void destory() {
		//事物提交
		transaction.commit();			//当改为自动提交时,需要将其注释掉
		//关闭会话 
		session.close();
		//关闭工厂
		sessionFactory.close();
	}
	
	/*
	 * 	测试保存学生
	 */
	@Test
	public void testStudent(){
		//创建学生对象
		Student student = new Student(1,"张三","男",new Date(),"河南");
		
		//改为自动提交事务
		session.doWork(new Work() {
			
			@Override
			public void execute(Connection connection) throws SQLException {
				connection.setAutoCommit(true);
			}
		});
		//保存对象
		session.save(student);
		//强制输出SQL语句
		session.flush();
		//注意:需要将transaction.commit()自动提交注释
	}
	
	/*
	 * 	测试保存对象类型数据
	 * 	向数据库存入的照片最大为1M
	 */
	@Test
	public void testWriteBlob() throws Exception{
		//创建学生对象
		Student student = new Student(1,"小简","女",new Date(),"上海");
		//获得文件
		File file = new File("F:"+File.separator+"JAVA_WorkSpace"+File.separator+"Hibernate"+File.separator+"picture"+File.separator+"jym.jpg");
		//获取输入流
		InputStream inputStream = new FileInputStream(file);
		//创建Blob对象,传入session创建对象,进而创建Blob对象
		Blob picture = Hibernate.getLobCreator(session).createBlob(inputStream, inputStream.available());
		student.setPicture(picture);
		//保存对象	
		session.save(student);
	}
	
	/**
	 * 	读取数据库中二进制文件
	 */
	@Test
	public void testReadBlod()throws Exception{
		Student student = (Student) session.get(Student.class, 1);
		//获取Blob对象
		Blob picture = student.getPicture();
		//获取输入流
		InputStream inputStream = picture.getBinaryStream();
		//创建输出文件,获得输出流
		File file = new File("F:"+File.separator+"JAVA_WorkSpace"+File.separator+"Hibernate"+File.separator+"picture"+File.separator+"jym2.jpg");
		OutputStream outputStream = new FileOutputStream(file);
		//读取,誊写
		byte[] buff = new byte[inputStream.available()];
		inputStream.read(buff);
		outputStream.write(buff);
		inputStream.close();
		outputStream.close();
	}
	
	/*
	 * 	测试保存学生及其组件属性
	 */
	@Test
	public void testStudentAndAddress(){
		//创建学生对象
		Student student = new Student(2,"张谦","男",new Date(),"河南");
		student.setAddress(new Address("451300", "15516559772", "河南科技学院"));
		//保存对象
		session.save(student);
	}
	
	/*
	 * 	GET查询方法
	 */
	@Test
	public void testGetStudent(){
		Student student = (Student)session.get(Student.class, 1);
		System.out.println("真实类名:"+student.getClass().getName());
		System.out.println("查询出:"+student.toString());
	}
	
	/*
	 * 	Load查询方法
	 */
	@Test
	public void testLoadStudent(){
		Student student = (Student)session.load(Student.class, 1);
		System.out.println("代理类名:"+student.getClass().getName());
		System.out.println("查询出:"+student.toString());
	}
	
	/*
	 * 	GET查询后更新
	 */
	@Test
	public void testGetUpdateStudent(){
		Student student = (Student)session.get(Student.class, 1);
		student.setAddree("浙江");
		System.out.println("更新:"+student.toString());
	}
	
	/*
	 * 	删除方法
	 */
	@Test
	public void testDeleteStudent(){
		Student student = (Student)session.get(Student.class, 1);
		session.delete(student);
		System.out.println("删除:"+student.toString());
	}
}

