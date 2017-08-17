package annotationBean;

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

/****ʹ�õ�Ԫ����,��ɶ�ע��浥���Ĳ���*/
public class AStudentTest {
	
	//�Ự����
	private SessionFactory sessionFactory;
	//�Ự����
	private Session session;
	//�������
	private Transaction transaction;
	
	/***
	 * 	���Է���ǰִ��
	 */
	@Before
	public void init() {
		//�������ö���
		Configuration configuration = new Configuration().configure();
		//��������ע�����
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		//�����Ự����
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		//�����Ự����
		session = sessionFactory.openSession();
		//��������
		transaction = session.beginTransaction();
		
	}
	
	/***
	 * 	���Է�����ִ��
	 */
	@After
	public void destory() {
		//�����ύ
		transaction.commit();	//����Ϊ�Զ��ύʱ,��Ҫ����ע�͵�
		//�رջỰ 
		session.close();
		//�رչ���
		sessionFactory.close();
	}
	
	/**
	 * 	���Ա���ѧ��
	 */
	@Test
	public void testAStudent(){
		//����ѧ������
		AStudent student = new AStudent(1,"����","��",new Date(),"����");
		
		//��Ϊ�Զ��ύ����
		session.doWork(new Work() {
			
			@Override
			public void execute(Connection connection) throws SQLException {
				connection.setAutoCommit(true);
			}
		});
		//�������
		session.save(student);
		//ǿ�����SQL���
		session.flush();
		//ע��:��Ҫ��transaction.commit()�Զ��ύע��
	}
	
	/**
	 * 	���Ա��������������
	 * 	�����ݿ�������Ƭ���Ϊ1M
	 */
	@Test
	public void testWriteBlob() throws Exception{
		//����ѧ������
		AStudent student = new AStudent(1,"����÷","Ů",new Date(),"�Ϻ�");
		//����ļ�
		File file = new File("F:"+File.separator+"JAVA_WorkSpace"+File.separator+"Hibernate"+File.separator+"picture"+File.separator+"jym.jpg");
		//��ȡ������
		InputStream inputStream = new FileInputStream(file);
		//����Blob����,����session��������,��������Blob����
		Blob picture = Hibernate.getLobCreator(session).createBlob(inputStream, inputStream.available());
		student.setPicture(picture);
		//�������	
		session.save(student);
	}
	
	@Test
	public void testReadBlod()throws Exception{
		AStudent student = (AStudent) session.get(AStudent.class, 1);
		//��ȡBlob����
		Blob picture = student.getPicture();
		//��ȡ������
		InputStream inputStream = picture.getBinaryStream();
		//��������ļ�,��������
		File file = new File("F:"+File.separator+"JAVA_WorkSpace"+File.separator+"Hibernate"+File.separator+"picture"+File.separator+"jym2.jpg");
		OutputStream outputStream = new FileOutputStream(file);
		//��ȡ,��д
		byte[] buff = new byte[inputStream.available()];
		inputStream.read(buff);
		outputStream.write(buff);
		inputStream.close();
		outputStream.close();
	}
	
	/**
	 * 	���Ա���ѧ�������������
	 */
	@Test
	public void testAStudentAndAddress(){
		//����ѧ������
		AStudent student = new AStudent(0,"��ǫ","��",new Date(),"����");
		student.setAAddress(new AAddress("451300", "15516559772", "���ϿƼ�ѧԺ"));
		//�������
		session.save(student);
		
	}
	
	/**
	 * 	GET��ѯ����
	 */
	@Test
	public void testGetAStudent(){
		AStudent student = (AStudent)session.get(AStudent.class, 1);
		System.out.println("��ʵ����:"+student.getClass().getName());
		System.out.println("��ѯ��:"+student.toString());
	}
	
	/**
	 * 	Load��ѯ����
	 */
	@Test
	public void testLoadAStudent(){
		AStudent student = (AStudent)session.load(AStudent.class, 1);
		System.out.println("��������:"+student.getClass().getName());
		System.out.println("��ѯ��:"+student.toString());
	}
	
	/**
	 * 	GET��ѯ�����
	 */
	@Test
	public void testGetUpdateAStudent(){
		AStudent student = (AStudent)session.get(AStudent.class, 1);
		student.setAddree("�㽭");
		System.out.println("����:"+student.toString());
	}
	
	/**
	 * 	ɾ������
	 */
	@Test
	public void testDeleteAStudent(){
		AStudent student = (AStudent)session.get(AStudent.class, 1);
		session.delete(student);
		System.out.println("ɾ��:"+student.toString());
	}
	
	/***
	 * 	����һ��һ����*/
	@Test
	public void testOneTowOneAStudent() {
		//����ѧ������
		AStudent student = new AStudent(1,"��ǫ","��",new Date(),"����");
		AStudentCard studentCard = new AStudentCard();
		studentCard.setCid("1");
		studentCard.setDescription("ѧ�� ��");
		student.setStudentCard(studentCard);
		//�������,�ȱ���Э����,�ٱ������ط�
		session.save(studentCard);
		session.save(student);
		
	}
}