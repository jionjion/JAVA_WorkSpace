package annotationBean;

import static org.hamcrest.CoreMatchers.startsWith;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

/***��Զ�,�෽�Ĳ��� */
public class ATeacherTest {
	
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
	 * 	GET��ѯ����
	 */
	@Test
	public void testGetAStudent(){
		APupil student = (APupil)session.get(APupil.class, 1);
		System.out.println("��ʵ����:"+student.getClass().getName());
		System.out.println("��ѯ��:"+student.toString());
	}
	
	/**
	 * 	Load��ѯ����
	 */
	@Test
	public void testLoadAStudent(){
		APupil student = (APupil)session.load(APupil.class, 1);
		System.out.println("��������:"+student.getClass().getName());
		System.out.println("��ѯ��:"+student.toString());
	}
	
	/**
	 * 	GET��ѯ�����
	 */
	@Test
	public void testGetUpdateAStudent(){
		APupil student = (APupil)session.get(APupil.class, 1);
		student.setPname("JionJion");
		System.out.println("����:"+student.toString());
	}
	
	/**
	 * 	ɾ������
	 */
	@Test
	public void testDeleteAStudent(){
		APupil student = (APupil)session.get(APupil.class, 1);
		session.delete(student);
		System.out.println("ɾ��:"+student.toString());
	}
	
	/***
	 * 	���Զ�Զౣ��*/
	@Test
	public void testOneTowOneAStudent() {
		//����ѧ������
		APupil pupi1 = new APupil(1, "��ǫ", "��");
		APupil pupi2 = new APupil(2, "Jion", "��");
		
		//������ʦ����
		ATeacher teacher1 = new ATeacher("1", "����ʦ");
		ATeacher teacher2 = new ATeacher("2", "Jion��ʦ");

		Set<APupil> pupils = new HashSet<APupil>();
		pupils.add(pupi1);
		pupils.add(pupi2);
		
		Set<ATeacher> teachers = new HashSet<ATeacher>();
		teachers.add(teacher1);
		teachers.add(teacher2);
		
		//�����ϵ
		pupi1.setTeachers(teachers);
		pupi2.setTeachers(teachers);
		//�������
		session.save(pupi1);
		session.save(pupi2);
		session.save(teacher1);
		session.save(teacher2);
		
	}
}